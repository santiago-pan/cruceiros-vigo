package com.nadisam.cruceirosvigo.internal.di.components;

import android.content.Context;

import com.nadisam.cruceirosvigo.domain.executor.PostExecutionThread;
import com.nadisam.cruceirosvigo.domain.executor.ThreadExecutor;
import com.nadisam.cruceirosvigo.domain.repository.DataRepositoryInterface;
import com.nadisam.cruceirosvigo.internal.di.modules.ApplicationModule;
import com.nadisam.cruceirosvigo.view.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent
{
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    DataRepositoryInterface dataRepository();
}

