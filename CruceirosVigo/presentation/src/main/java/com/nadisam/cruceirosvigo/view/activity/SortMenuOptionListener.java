package com.nadisam.cruceirosvigo.view.activity;

/**
 * Created by santiagopan on 27/02/16.
 *
 * Activity containing the cruises list must implements this interface.
 */
public interface SortMenuOptionListener
{
    public static final int SORT_BY_DATE = 0;
    public static final int SORT_BY_LENGTH = 1;

    void sortListBy(int type);
}
