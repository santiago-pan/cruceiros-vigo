package com.nadisam.cruceirosvigo.domain.repository;

import com.nadisam.cruceirosvigo.domain.Cruise;

import java.util.List;

import rx.Observable;

/**
 * Created by santiagopan on 21/02/16.
 */
public interface DataRepositoryInterface
{
    /**
     * Gets an {@link Observable} which will emit a {@link Cruise}.
     */
    Observable<List<Cruise>> getCruises();
}
