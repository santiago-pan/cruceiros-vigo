package com.nadisam.cruceirosvigo.internal.di.components;

import com.nadisam.cruceirosvigo.internal.di.PerActivity;
import com.nadisam.cruceirosvigo.internal.di.modules.ActivityModule;
import com.nadisam.cruceirosvigo.internal.di.modules.CruiseModule;
import com.nadisam.cruceirosvigo.view.fragment.CruisesListFragment;

import dagger.Component;

/**
 * Created by santiagopan on 25/02/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, CruiseModule.class})
public interface CruiseComponent
{
    void inject(CruisesListFragment cruisesListFragment);
}
