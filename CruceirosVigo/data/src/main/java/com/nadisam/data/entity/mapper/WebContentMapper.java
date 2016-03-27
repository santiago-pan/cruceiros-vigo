package com.nadisam.data.entity.mapper;

/**
 * Created by santiagopan on 23/02/16.
 */

import com.nadisam.data.cache.CruisesCache;
import com.nadisam.data.entity.CruiseEntity;
import com.nadisam.data.net.MultipleWebFetcher;
import com.nadisam.data.net.WebFetcher;
import com.nadisam.data.net.WebFetcherInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by santiagopan on 16/02/16.
 *
 * Process web content to retrieve all cruises.
 */
@Singleton
public class WebContentMapper
{
    private static String TAG = WebContentMapper.class.getName();

    private CruisesCache cruisesCache;
    private WebFetcherInterface webFetcher;
    private WebFetcherInterface multipleWebFetcher;

    @Inject
    public WebContentMapper(CruisesCache cruisesCache, WebFetcher webFetcher, MultipleWebFetcher multipleWebFetcher)
    {
        this.cruisesCache = cruisesCache;
        this.webFetcher = webFetcher;
        this.multipleWebFetcher = multipleWebFetcher;
    }

    /**
     * Processes the web content to retrieve the list of {@link CruiseEntity} and emits them in an
     * Observable with them.
     *
     * @return Observable with the list of cruises.
     */
    public Observable<List<CruiseEntity>> getCruises()
    {
        if (!cruisesCache.isCached()||(cruisesCache.isExpired()))
        {
            // Return observable with a list of cruises
            return Observable.create(new Observable.OnSubscribe<List<CruiseEntity>>()
            {
                @Override
                public void call(Subscriber<? super List<CruiseEntity>> subscriber)
                {
                    String URL = "http://www.apvigo.com/control.php?sph=a_iap=1110&p_rpp=1";
                    webFetcher.fetch(0).subscribe(web -> {

                        // Get number of cruises
                        int numCruises = getNumCruises(web.get(0));

                        // Fetch all pages containing all cruises
                        multipleWebFetcher.fetch(numCruises).subscribe(pages -> {
                            // Get cruises from each page
                            List<CruiseEntity> cruises = new ArrayList<>();
                            for (int i = 0; i < pages.size(); i++)
                            {
                                cruises.addAll(WebContentMapper.this.process(pages.get(i)));
                            }
                            subscriber.onNext(cruises);
                            subscriber.onCompleted();
                        });
                    });
                }
            });
        }
        else
        {
            return cruisesCache.getCruises();
        }
    }

    public int getNumCruises(String web)
    {
        // Get how many cruises there are
        Pattern mPattern = Pattern.compile("Localizados [0-9]{2,3} registros");

        Matcher matcher = mPattern.matcher(web);
        if (matcher.find())
        {
            try
            {
                String line = matcher.group();
                line = line.replace("Localizados ", "");
                line = line.replace(" registros", "");
                return Integer.valueOf(line);
            }
            catch (Exception e)
            {
                return 0;
            }
        }
        return 0;
    }

    public List<CruiseEntity> process(String web)
    {
        int NUM_ROWS_BY_LINE = 8;
        List<CruiseEntity> cruises = new ArrayList<>();

        // Get beginning of boats information
        String table = "";
        String initDelimiter = "<tr class='fila1'";
        table = web.substring(web.indexOf(initDelimiter));

        // Split by row
        String columnSplit = "<tr class='fila";
        String[] columns = table.split(columnSplit);
        int numColumns = columns.length;

        // Split each row by column
        String rowSplit = "class=\"negro\">";
        for (int col = 0; col < numColumns; col++)
        {
            String[] rows = columns[col].split(rowSplit);

            // Get ship info
            if (rows.length == NUM_ROWS_BY_LINE)
            {
                // Create CruiseEntity
                int cnt = 1;
                CruiseEntity c = new CruiseEntity();
                c.setArrive(rows[cnt].substring(0, rows[cnt].indexOf("</span>")));
                cnt++;
                c.setCruise(rows[cnt].substring(0, rows[cnt].indexOf("</span>")));
                cnt++;
                c.setLength(Integer.valueOf(rows[cnt].substring(0, rows[cnt].indexOf("</span>"))));
                cnt++;
                c.setArriveTime(rows[cnt].substring(0, rows[cnt].indexOf("</span>")-3));
                cnt++;
                c.setDepartureTime(rows[cnt].substring(0, rows[cnt].indexOf("</span>")-3));
                cnt++;
                c.setFrom(rows[cnt].substring(0, rows[cnt].indexOf("</span>")));
                cnt++;
                c.setTo(rows[cnt].substring(0, rows[cnt].indexOf("</span>")));

                cruises.add(c);
            }
        }

        return cruises;
    }
}

