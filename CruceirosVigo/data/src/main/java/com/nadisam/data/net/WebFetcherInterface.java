package com.nadisam.data.net;

import java.util.List;

import rx.Observable;

/**
 * Created by santiagopan on 23/02/16.
 */
public interface WebFetcherInterface
{
    Observable<List<String>> fetch(int n);
}
