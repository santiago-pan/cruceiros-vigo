package com.nadisam.cruceirosvigo.internal.di.modules;

import android.provider.ContactsContract;

import com.nadisam.cruceirosvigo.domain.interactor.GetCruises;
import com.nadisam.cruceirosvigo.internal.di.PerActivity;
import com.nadisam.data.repository.DataRepository;

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

    @Provides @PerActivity @Named("getCruises") GetCruises provideGetCruises(GetCruises getCruises){
        return getCruises;
    }
}
