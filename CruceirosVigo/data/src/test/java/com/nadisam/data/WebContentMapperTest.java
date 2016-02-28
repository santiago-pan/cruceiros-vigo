package com.nadisam.data;

import com.nadisam.cruceirosvigo.domain.Cruise;
import com.nadisam.data.cache.CruisesCache;
import com.nadisam.data.entity.CruiseEntity;
import com.nadisam.data.entity.mapper.WebContentMapper;
import com.nadisam.data.net.MultipleWebFetcher;
import com.nadisam.data.net.WebFetcher;
import com.nadisam.data.net.WebFetcherInterface;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class WebContentMapperTest
{
    private static final String TAG = "WebContentMapperTest";

    @Before
    public void setUp()
    {
    }

    @Test
    public void testWebContentMapper() throws Exception
    {

        TestSubscriber<List<CruiseEntity>> testSubscriber = new TestSubscriber<>();
        CruisesCache cruisesCache = new CruisesCache();
        WebFetcherInterface webFetcher = new MockWebFetcher();
        WebFetcherInterface multipleWebFetcher = new MockMultipleWebFetcher();

        WebContentMapper webContentMapper = new WebContentMapper(cruisesCache, (WebFetcher)webFetcher,
                                                                 (MultipleWebFetcher)multipleWebFetcher);
        webContentMapper.getCruises().subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        List<List<CruiseEntity>> res = testSubscriber.getOnNextEvents();
        assertThat(res.size(), is(1));

        List<CruiseEntity> cruises = res.get(0);

        assertThat(cruises.size(), is(9)); // 3 cruises per page and 3 pages

        for (int i = 0; i < cruises.size(); i++)
        {
            CruiseEntity c = cruises.get(i);
            assertThat(c.getCruise(), is(names[i]));
            assertThat(c.getLength(), is(length[i]));
            assertThat(c.getArrive(), is(dates[i]));
        }
    }

    // Validation data
    private String[] names = {"ARCADIA", "QUEEN MARY II", "BOUDICCA", "COSTA LUMINOSA", "COSTA " +
            "FAVOLOSA", "QUEEN ELIZABETH", "VENTURA", "BLACK WATCH", "WIND SURF"};
    private String[] dates = {"17/12/2015", "13/12/2015", "03/12/2015", "08/09/2015",
            "05/09/2015", "01/09/2015", "08/04/2015", "03/04/2015", "04/02/2015"};
    private int[] length = {290, 345, 207, 292, 290, 294, 290, 206, 187};

    private class MockWebFetcher implements WebFetcherInterface
    {

        @Override
        public Observable<List<String>> fetch(int n)
        {
            String result = MOCK_MAIN_WEB_PAGE;
            List<String> list = new ArrayList<>();
            list.add(result);
            return Observable.just(list);
        }
    }

    private class MockMultipleWebFetcher implements WebFetcherInterface
    {

        @Override
        public Observable<List<String>> fetch(int n)
        {
            String page1 = MOCK_WEB_PAGE_1;
            String page2 = MOCK_WEB_PAGE_2;
            String page3 = MOCK_WEB_PAGE_3;
            List<String> list = new ArrayList<>();
            list.add(page1);
            list.add(page2);
            list.add(page3);
            return Observable.just(list);
        }
    }

    // Demo main page
    private static final String MOCK_MAIN_WEB_PAGE = "<td class=\"columnai\" style=\"width:50%;" +
            "padding:3px;\"> <span id=\"regtotlst\" class=\"rojo\"><span class=\"lst\">»</span> " +
            "Localizados 345 registros</span></td>\n";

    // Demo cruises pages
    private static final String MOCK_WEB_PAGE_1 = "<tr class='fila1' " +
            "onMouseOver=\"javascript:this.className='fila3';\" onMouseOut=\"javascript:this" +
            ".className='fila1';\">\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo6_1119\" " +
            "class=\"negro\">17/12/2015</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo3_1119\" class=\"negro\">ARCADIA</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo8_1119\" class=\"negro\">290</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo9_1119\" class=\"negro\">08:00:00</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo10_1119\" class=\"negro\">15:30:00</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo1_1119\" class=\"negro\">Barbados</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo11_1119\" " +
            "class=\"negro\">Southampton</span></td>\n" +
            "  </tr>\n" +
            "  <tr class='fila2' onMouseOver=\"javascript:this.className='fila3';\" " +
            "onMouseOut=\"javascript:this.className='fila2';\">\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo6_1117\" " +
            "class=\"negro\">13/12/2015</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo3_1117\" class=\"negro\">QUEEN MARY " +
            "II</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo8_1117\" class=\"negro\">345</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo9_1117\" class=\"negro\">09:00:00</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo10_1117\" class=\"negro\">17:00:00</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo1_1117\" class=\"negro\">Lisboa</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo11_1117\" " +
            "class=\"negro\">Southampton</span></td>\n" +
            "  </tr>\n" +
            "  <tr class='fila1' onMouseOver=\"javascript:this.className='fila3';\" " +
            "onMouseOut=\"javascript:this.className='fila1';\">\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo6_1116\" " +
            "class=\"negro\">03/12/2015</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo3_1116\" class=\"negro\">BOUDICCA</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo8_1116\" class=\"negro\">207</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo9_1116\" class=\"negro\">08:00:00</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo10_1116\" class=\"negro\">18:00:00</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo1_1116\" class=\"negro\">Tilbury</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo11_1116\" " +
            "class=\"negro\">Casablanca</span></td>\n" +
            "  </tr>\n";

    private static final String MOCK_WEB_PAGE_2 = "<tr class='fila1' " +
            "onMouseOver=\"javascript:this.className='fila3';\" onMouseOut=\"javascript:this" +
            ".className='fila1';\">\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo6_1085\" " +
            "class=\"negro\">08/09/2015</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo3_1085\" class=\"negro\">COSTA " +
            "LUMINOSA</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo8_1085\" class=\"negro\">292</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo9_1085\" class=\"negro\">08:00:00</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo10_1085\" class=\"negro\">18:00:00</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo1_1085\" class=\"negro\">Portland</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo11_1085\" class=\"negro\">Lisboa</span></td>\n" +
            "  </tr>\n" +
            "  <tr class='fila2' onMouseOver=\"javascript:this.className='fila3';\" " +
            "onMouseOut=\"javascript:this.className='fila2';\">\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo6_1100\" " +
            "class=\"negro\">05/09/2015</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo3_1100\" class=\"negro\">COSTA " +
            "FAVOLOSA</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo8_1100\" class=\"negro\">290</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo9_1100\" class=\"negro\">08:00:00</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo10_1100\" class=\"negro\">18:00:00</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo1_1100\" class=\"negro\">Le Havre</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo11_1100\" class=\"negro\">Lisboa</span></td>\n" +
            "  </tr>\n" +
            "  <tr class='fila1' onMouseOver=\"javascript:this.className='fila3';\" " +
            "onMouseOut=\"javascript:this.className='fila1';\">\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo6_1083\" " +
            "class=\"negro\">01/09/2015</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo3_1083\" class=\"negro\">QUEEN " +
            "ELIZABETH</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo8_1083\" class=\"negro\">294</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo9_1083\" class=\"negro\">09:00:00</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo10_1083\" class=\"negro\">17:00:00</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo1_1083\" " +
            "class=\"negro\">Southampton</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo11_1083\" class=\"negro\">Lisboa</span></td>\n" +
            "  </tr>\n";

    private static final String MOCK_WEB_PAGE_3 = "  <tr class='fila1' " +
            "onMouseOver=\"javascript:this.className='fila3';\" onMouseOut=\"javascript:this" +
            ".className='fila1';\">\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo6_1030\" " +
            "class=\"negro\">08/04/2015</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo3_1030\" class=\"negro\">VENTURA</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo8_1030\" class=\"negro\">290</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo9_1030\" class=\"negro\">09:00:00</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo10_1030\" class=\"negro\">17:30:00</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo1_1030\" class=\"negro\">Lisboa</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo11_1030\" " +
            "class=\"negro\">Southampton</span></td>\n" +
            "  </tr>\n" +
            "  <tr class='fila2' onMouseOver=\"javascript:this.className='fila3';\" " +
            "onMouseOut=\"javascript:this.className='fila2';\">\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo6_1029\" " +
            "class=\"negro\">03/04/2015</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo3_1029\" class=\"negro\">BLACK " +
            "WATCH</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo8_1029\" class=\"negro\">206</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo9_1029\" class=\"negro\">08:00:00</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo10_1029\" class=\"negro\">18:00:00</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo1_1029\" class=\"negro\">Liverpool</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo11_1029\" class=\"negro\">Lisboa</span></td>\n" +
            "  </tr>\n" +
            "  <tr class='fila1' onMouseOver=\"javascript:this.className='fila3';\" " +
            "onMouseOut=\"javascript:this.className='fila1';\">\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo6_1055\" " +
            "class=\"negro\">04/02/2015</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo3_1055\" class=\"negro\">WIND SURF</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo8_1055\" class=\"negro\">187</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo9_1055\" class=\"negro\">09:54:00</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo10_1055\" class=\"negro\">09:54:00</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo1_1055\" class=\"negro\">St. Peter " +
            "Port</span></td>\n" +
            "    <td class=\"columna\" style=\"vertical-align:middle;padding:10px;" +
            "text-align:left;\"><span id=\"lstcpo11_1055\" class=\"negro\">St. Malo</span></td>\n" +
            "  </tr>\n";
}
