package com.nadisam.cruceirosvigo;

import android.app.Application;

import com.nadisam.cruceirosvigo.internal.di.components.ApplicationComponent;
import com.nadisam.cruceirosvigo.internal.di.modules.ApplicationModule;
import com.nadisam.cruceirosvigo.internal.di.components.DaggerApplicationComponent;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application
{

    private ApplicationComponent applicationComponent;

    @Override public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
