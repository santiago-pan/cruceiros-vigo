package com.nadisam.data.repository;

import com.nadisam.cruceirosvigo.domain.Cruise;
import com.nadisam.cruceirosvigo.domain.repository.DataRepositoryInterface;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rx.Observable;
import rx.observers.TestSubscriber;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by santiagopan on 25/02/16.
 *
 */
public class CruiseRepositoryTest
{
    private static final String TAG = "CruiseRepositoryTest";

    private DataRepository cruiseRepository;
    private DataRepositoryInterface dataRepository;


    @Before
    public void setUp()
    {
        dataRepository = new DataRepositoryTest();
        //cruiseRepository = new DataRepository(dataRepository);
    }

    @Test
    public void getCruisesByLengthTest() throws Exception
    {
        TestSubscriber<List<Cruise>> testSubscriber = new TestSubscriber<>();
        //cruiseRepository.getCruisesByLength().subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        List<List<Cruise>> res = testSubscriber.getOnNextEvents();
        assertThat(res.get(0), notNullValue());

        List<Cruise> cruises = res.get(0);
        assertThat(cruises.size(), not(0));

        int length = Integer.MAX_VALUE;
        for (int c = 0; c < cruises.size(); c++) {
            int newLength = cruises.get(c).getLength();
            assertTrue(newLength <= length);
            System.out.println("Length out: " + newLength);
            length = newLength;
        }
    }

    @Test
    public void getCruisesByDateTest() throws Exception
    {
        TestSubscriber<List<Cruise>> testSubscriber = new TestSubscriber<>();
        //cruiseRepository.getCruisesByDate().subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        List<List<Cruise>> res = testSubscriber.getOnNextEvents();
        assertThat(res.get(0), notNullValue());

        List<Cruise> cruises = res.get(0);
        assertThat(cruises.size(), not(0));

        Date date = null;
        String myFormatString = "dd/MM/yyyy";
        SimpleDateFormat df = new SimpleDateFormat(myFormatString, Locale.getDefault());

        for (int c = 0; c < cruises.size(); c++) {
            Date newDate = df.parse(cruises.get(c).getArrive());
            if (date != null)
            {
                assertTrue(newDate.before(date));
            }
            date = newDate;

            System.out.println("Date out: " + df.format(date));
        }
    }

    protected class DataRepositoryTest implements DataRepositoryInterface {

        @Override
        public Observable<List<Cruise>> getCruises()
        {
            List<Cruise> cruises = new ArrayList<>();
            // Generate demo cruises
            for (int c = 0; c < 10; c++) {
                int length = 100 + (int)(Math.random()*200);
                String date = generateRandomDate();
                Cruise cruise = new Cruise();
                cruise.setLength(length);
                cruise.setArrive(date);
                cruises.add(cruise);
            }
            return Observable.just(cruises);
        }
    }

    private String generateRandomDate () {

        int randomYear = 2015 + (int)(Math.random()*2);
        int dayOfYear = (int)(Math.random()*365);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, randomYear);
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);

        String myFormatString = "dd/MM/yyyy";
        SimpleDateFormat df = new SimpleDateFormat(myFormatString, Locale.getDefault());

        return df.format(calendar.getTime());
    }
}
