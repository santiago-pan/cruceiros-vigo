package com.nadisam.data.cache;

import com.nadisam.data.entity.CruiseEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by santiagopan on 22/02/16.
 */
public interface CruisesCacheInterface
{
    /**
     * Gets an {@link rx.Observable} which will emit a {@link CruiseEntity}.
     */
    Observable<List<CruiseEntity>> getCruises();

    /**
     * Puts and element into the cache.
     *
     * @param cruisesEntity Element to insert in the cache.
     */
    void put(List<CruiseEntity> cruisesEntity);

    /**
     * Checks if cruises are in the cache.
     *
     * @return true if cruises are in cached, otherwise false.
     */
    boolean isCached();

    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    boolean isExpired();

    /**
     * Evict all elements of the cache.
     */
    void evictAll();
}
