package com.nadisam.cruceirosvigo.internal.di.modules;

import android.content.Context;

import com.nadisam.cruceirosvigo.AndroidApplication;
import com.nadisam.cruceirosvigo.UIThread;
import com.nadisam.cruceirosvigo.domain.executor.PostExecutionThread;
import com.nadisam.cruceirosvigo.domain.executor.ThreadExecutor;
import com.nadisam.cruceirosvigo.domain.repository.DataRepositoryInterface;
import com.nadisam.data.cache.CruisesCache;
import com.nadisam.data.cache.CruisesCacheInterface;
import com.nadisam.data.executor.JobExecutor;
import com.nadisam.data.repository.DataRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule
{
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application)
    {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext()
    {
        return this.application;
    }

    @Provides @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread)
    {
        return uiThread;
    }


    @Provides
    @Singleton
    CruisesCacheInterface provideCruisesCache(CruisesCache cruisesCacheCache)
    {
        return cruisesCacheCache;
    }

    @Provides
    @Singleton
    DataRepositoryInterface provideCruiseRepository(DataRepository dataRepository)
    {
        return dataRepository;
    }
}

