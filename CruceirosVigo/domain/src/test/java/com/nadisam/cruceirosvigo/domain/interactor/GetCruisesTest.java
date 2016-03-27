package com.nadisam.cruceirosvigo.domain.interactor;

import com.nadisam.cruceirosvigo.domain.Cruise;
import com.nadisam.cruceirosvigo.domain.executor.PostExecutionThread;
import com.nadisam.cruceirosvigo.domain.executor.ThreadExecutor;
import com.nadisam.cruceirosvigo.domain.repository.DataRepositoryInterface;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import rx.Observable;
import rx.Scheduler;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class GetCruisesTest
{
    private static final String TAG = "GetCruisesTest";
    private GetCruises getCruises;

    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private DataRepositoryInterface mockDataRepository;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        mockPostExecutionThread = new MyPostExecutionThread();
        mockThreadExecutor = new MyThreadExecutor();
        mockDataRepository = new DataRepositoryTest();
        getCruises = new GetCruises(mockThreadExecutor, mockPostExecutionThread, mockDataRepository);
    }

    @Test
    public void getCruisesTest() throws Exception
    {
        TestSubscriber<List<Cruise>> testSubscriber = new TestSubscriber<>();
        getCruises.getCruises(testSubscriber);
        Thread.sleep(2000);
        testSubscriber.assertNoErrors();
        List<List<Cruise>> res = testSubscriber.getOnNextEvents();
        assertThat(res.get(0), notNullValue());

        List<Cruise> cruises = res.get(0);
        assertThat(cruises.size(), is(10));
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

    protected class MyPostExecutionThread implements PostExecutionThread {

        @Override
        public Scheduler getScheduler()
        {
            return Schedulers.newThread();
        }
    }

    protected class MyThreadExecutor implements ThreadExecutor {

        @Override
        public void execute(Runnable command)
        {
            command.run();
        }
    }

}