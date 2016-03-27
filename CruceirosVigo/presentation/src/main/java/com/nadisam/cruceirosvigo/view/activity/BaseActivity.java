package com.nadisam.cruceirosvigo.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nadisam.cruceirosvigo.AndroidApplication;
import com.nadisam.cruceirosvigo.internal.di.components.ApplicationComponent;
import com.nadisam.cruceirosvigo.internal.di.modules.ActivityModule;

/**
 * Created by santiagopan on 22/02/16.
 */
public class BaseActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent()
    {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link ActivityModule}
     */
    protected ActivityModule getActivityModule()
    {
        return new ActivityModule(this);
    }
}
