package com.nadisam.data.net;

import java.util.List;

import rx.Observable;

/**
 * Created by santiagopan on 23/02/16.
 */
public interface WebFetcherInterface
{
    /**
     * Retrieves n pages from a base URL, using n as index to go through the pages.
     *
     * @param n Number of pages to retrieve.
     * @return Observable with a list of strings. Each string is the body of the page.
     */
    Observable<List<String>> fetch(int n);
}
