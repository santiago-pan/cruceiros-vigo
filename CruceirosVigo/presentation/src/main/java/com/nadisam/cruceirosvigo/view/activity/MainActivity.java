package com.nadisam.cruceirosvigo.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.nadisam.cruceirosvigo.R;
import com.nadisam.cruceirosvigo.internal.di.HasComponent;
import com.nadisam.cruceirosvigo.internal.di.components.CruiseComponent;
import com.nadisam.cruceirosvigo.internal.di.components.DaggerCruiseComponent;

public class MainActivity extends BaseActivity implements HasComponent
{
    private CruiseComponent cruiseComponent;
    private SortMenuOptionListener sortMenuOptionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cruise_list);

        this.initializeInjector();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (null != sortMenuOptionListener)
        {
            switch (item.getItemId())
            {
                // Respond to the action bar's Up/Home button
                case R.id.action_sort_date:
                {
                    sortMenuOptionListener.sortListBy(SortMenuOptionListener.SORT_BY_DATE);
                    return true;
                }
                case R.id.action_sort_length:
                {
                    sortMenuOptionListener.sortListBy(SortMenuOptionListener.SORT_BY_LENGTH);
                    return true;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void setSortMenuOptionListener(SortMenuOptionListener listener) {
        this.sortMenuOptionListener = listener;
    }

    private void initializeInjector()
    {
        this.cruiseComponent = DaggerCruiseComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public Object getComponent()
    {
        return cruiseComponent;
    }
}
