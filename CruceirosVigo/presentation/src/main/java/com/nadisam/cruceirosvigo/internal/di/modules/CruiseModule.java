package com.nadisam.cruceirosvigo.internal.di.modules;

import com.nadisam.cruceirosvigo.domain.interactor.GetCruises;
import com.nadisam.cruceirosvigo.domain.interactor.GetCruisesInterface;
import com.nadisam.cruceirosvigo.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by santiagopan on 25/02/16.
 */
@Module
public class CruiseModule
{
    public CruiseModule()
    {
    }

    @Provides
    @PerActivity
    @Named("getCruises")
    GetCruisesInterface provideGetCruises(GetCruises getCruises)
    {
        return getCruises;
    }
}
