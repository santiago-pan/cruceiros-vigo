package com.nadisam.data.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.exceptions.OnErrorThrowable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by santiagopan on 19/02/16.
 */
@Singleton
public class MultipleWebFetcher implements WebFetcherInterface
{
    private OkHttpClient client = new OkHttpClient();
    private static int CRUISES_PER_PAGE = 30;
    private static String BASE_URL = "http://www.apvigo.com/control" +
            ".php?sph=a_lst_nrt=NUM_CRUISES%%a_lst_npa=NUM_PAGE%%a_iap=1110%%s_idm=1";

    @Inject
    public MultipleWebFetcher()
    {
    }

    public Observable<List<String>> fetch(int numCruises)
    {

        // Create the list of URLs to retrieve all pages content
        int numPages = (int) (Math.ceil((double) numCruises / CRUISES_PER_PAGE));
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < numPages; i++)
        {
            String nc = String.valueOf(numCruises);
            String np = String.valueOf(i + 1);
            urls.add(BASE_URL.replace("NUM_CRUISES", nc).replace("NUM_PAGE", np));
        }
        return Observable.from(urls).flatMap(url -> {
            Observable<String> pageObservable = Observable.create(sub -> {
                if (sub.isUnsubscribed())
                {
                    return;
                }
                String response;
                try
                {
                    response = MultipleWebFetcher.this.run(url);
                }
                catch (Exception io)
                {
                    throw OnErrorThrowable.from(OnErrorThrowable.addValueAsLastCause(io,
                                                                                     url));
                }

                if (!sub.isUnsubscribed())
                {
                    sub.onNext(response);
                    sub.onCompleted();
                }
            });

            return pageObservable.subscribeOn(Schedulers.io());
        }, 12).toList();
    }

    private String run(String url) throws IOException
    {
        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}



