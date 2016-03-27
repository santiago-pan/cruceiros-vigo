package com.nadisam.data.cache;

import com.nadisam.data.entity.CruiseEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by santiagopan on 22/02/16.
 *
 * {@link CruisesCacheInterface} implementation.
 */
@Singleton
public class CruisesCache implements CruisesCacheInterface
{
    private static List<CruiseEntity> cruisesCache = new ArrayList<>();
    private static final long EXPIRATION_TIME = 60 * 10 * 1000; // 10 minutes
    private long currentMillis = 0;

    @Inject
    public CruisesCache() {}

    @Override
    public Observable<List<CruiseEntity>> getCruises()
    {
        return Observable.just(cruisesCache);
    }

    @Override
    public void put(List<CruiseEntity> cruisesEntity)
    {
        if (cruisesEntity != null)
        {
            cruisesCache = cruisesEntity;
            setLastCacheUpdateTimeMillis();
        }
    }

    @Override
    public boolean isCached()
    {
        return (cruisesCache.size() > 0);
    }

    @Override
    public boolean isExpired()
    {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    @Override
    public void evictAll()
    {
        cruisesCache.clear();
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis() {
        this.currentMillis = System.currentTimeMillis();
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private long getLastCacheUpdateTimeMillis() {
        return this.currentMillis;
    }
}
