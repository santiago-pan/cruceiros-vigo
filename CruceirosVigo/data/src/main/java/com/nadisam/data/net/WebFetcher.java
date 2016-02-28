package com.nadisam.data.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by santiagopan on 16/02/16.
 *
 * Retrieves the html content from a URL.
 */
@Singleton
public class WebFetcher implements WebFetcherInterface
{
    private String url;
    private OkHttpClient client = new OkHttpClient();

    @Inject
    public WebFetcher(){
        this.url = "http://www.apvigo.com/control.php?sph=a_iap=1110&p_rpp=1";
    }

    public WebFetcher(String url)
    {
        this.url = url;
    }

    public Observable<List<String>> fetch(int n)
    {
        try
        {
            String response = this.run(this.url);
            List<String> list = new ArrayList<>();
            list.add(response);
            return Observable.just(list);
        }
        catch (IOException ex) {
            return Observable.just(null);
        }
    }

    private String run(String url) throws IOException
    {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
