package com.nadisam.data;

import com.nadisam.cruceirosvigo.domain.Cruise;
import com.nadisam.data.cache.CruisesCache;
import com.nadisam.data.entity.CruiseEntity;
import com.nadisam.data.entity.mapper.WebContentMapper;
import com.nadisam.data.net.MultipleWebFetcher;
import com.nadisam.data.net.WebFetcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import rx.observers.TestSubscriber;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class WebFetcherUnitTest
{
    private static final String TAG = "WebFetcherUnitTest";

    @Before
    public void setUp()
    {
    }

    @Test
    public void webFetcherTest() throws Exception {

        TestSubscriber<List<String>> testSubscriber = new TestSubscriber<>();

        String URL = "http://www.apvigo.com/control.php?sph=a_iap=1110&p_rpp=1";
        WebFetcher webFetcher = new WebFetcher(URL);
        webFetcher.fetch(0)
        .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        List<List<String>> res = testSubscriber.getOnNextEvents();
        assertThat(res.get(0), notNullValue());
    }

    @Test
    public void multipleWebFetcherTest () throws Exception {

        TestSubscriber<List<String>> testSubscriber = new TestSubscriber<>();

        List<String> urls = new ArrayList<>();

        urls.add("http://www.apvigo.com/control.php?sph=a_lst_nrt=345%%a_lst_npa=1%%a_iap=1110%%s_idm=1");
        urls.add("http://www.apvigo.com/control.php?sph=a_lst_nrt=345%%a_lst_npa=2%%a_iap=1110%%s_idm=1");
        urls.add("http://www.apvigo.com/control.php?sph=a_lst_nrt=345%%a_lst_npa=3%%a_iap=1110%%s_idm=1");
        urls.add("http://www.apvigo.com/control.php?sph=a_lst_nrt=345%%a_lst_npa=4%%a_iap=1110%%s_idm=1");

        urls.add("http://www.apvigo.com/control.php?sph=a_lst_nrt=345%%a_lst_npa=5%%a_iap=1110%%s_idm=1");
        urls.add("http://www.apvigo.com/control.php?sph=a_lst_nrt=345%%a_lst_npa=6%%a_iap=1110%%s_idm=1");
        urls.add("http://www.apvigo.com/control.php?sph=a_lst_nrt=345%%a_lst_npa=7%%a_iap=1110%%s_idm=1");
        urls.add("http://www.apvigo.com/control.php?sph=a_lst_nrt=345%%a_lst_npa=8%%a_iap=1110%%s_idm=1");

        urls.add("http://www.apvigo.com/control.php?sph=a_lst_nrt=345%%a_lst_npa=9%%a_iap=1110%%s_idm=1");
        urls.add("http://www.apvigo.com/control.php?sph=a_lst_nrt=345%%a_lst_npa=10%%a_iap=1110%%s_idm=1");
        urls.add("http://www.apvigo.com/control.php?sph=a_lst_nrt=345%%a_lst_npa=11%%a_iap=1110%%s_idm=1");
        urls.add("http://www.apvigo.com/control.php?sph=a_lst_nrt=345%%a_lst_npa=12%%a_iap=1110%%s_idm=1");

        // Get content
        MultipleWebFetcher webFetcher = new MultipleWebFetcher();
        webFetcher.fetch(345).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertNoErrors();
        List<List<String>> res = testSubscriber.getOnNextEvents();
        List<String> pages = res.get(0);

        assertThat(pages.size(), is(12));

        List<CruiseEntity> cruises = new ArrayList<>();
        String URL = "http://www.apvigo.com/control.php?sph=a_iap=1110&p_rpp=1";
        WebContentMapper webContentMapper = new WebContentMapper(new CruisesCache(), new WebFetcher(URL), new MultipleWebFetcher());
        for (int i = 0; i < pages.size(); i++)
        {
            cruises.addAll(webContentMapper.process(pages.get(i)));
        }

        assertThat(cruises.size(), is(345));

    }

    @Test
    public void numPagesTest () throws Exception {
        TestSubscriber<List<String>> testSubscriber = new TestSubscriber<>();

        // Get content
        String URL = "http://www.apvigo.com/control.php?sph=a_iap=1110&p_rpp=1";
        WebFetcher webFetcher = new WebFetcher(URL);
        webFetcher.fetch(0).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        List<List<String>> res = testSubscriber.getOnNextEvents();
        String content = res.get(0).get(0);

        // Can not be null
        assertThat(content, notNullValue());

        // Parse content
        WebContentMapper webContentMapper = new WebContentMapper(new CruisesCache(), new WebFetcher(URL), new MultipleWebFetcher());
        int numPages = webContentMapper.getNumCruises(content);

        // There should be 345 cruises
        assertThat(numPages, is(345));
    }

    @Test
    public void integrationTest() throws Exception {

        TestSubscriber<List<CruiseEntity>> testSubscriber = new TestSubscriber<>();

        String URL = "http://www.apvigo.com/control.php?sph=a_iap=1110&p_rpp=1";
        WebContentMapper webContentMapper = new WebContentMapper(new CruisesCache(), new WebFetcher(URL), new MultipleWebFetcher());
        webContentMapper.getCruises().subscribe(testSubscriber);
        Thread.sleep(5000);
        testSubscriber.assertNoErrors();
        List<List<CruiseEntity>> res = testSubscriber.getOnNextEvents();
        List<CruiseEntity> cruises = res.get(0);

        assertThat(cruises, notNullValue());

        for (CruiseEntity c : cruises)
        {
            System.out.println("Cruise " + c.getCruise());
        }
    }
}