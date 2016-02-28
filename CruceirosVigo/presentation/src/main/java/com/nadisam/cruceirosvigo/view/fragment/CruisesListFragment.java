package com.nadisam.cruceirosvigo.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.nadisam.cruceirosvigo.R;
import com.nadisam.cruceirosvigo.internal.di.components.CruiseComponent;
import com.nadisam.cruceirosvigo.model.CruiseModel;
import com.nadisam.cruceirosvigo.presenter.CruisesListPresenter;
import com.nadisam.cruceirosvigo.view.CruisesListView;
import com.nadisam.cruceirosvigo.view.activity.MainActivity;
import com.nadisam.cruceirosvigo.view.activity.SortMenuOptionListener;
import com.nadisam.cruceirosvigo.view.adapter.CruisesRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 * <p>
 *
 * interface.
 */
public class CruisesListFragment extends BaseFragment implements CruisesListView, SortMenuOptionListener
{
    @Inject
    CruisesListPresenter cruisesListPresenter;
    @Bind(R.id.cruises_list)
    RecyclerView rv_cruise;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    private CruisesRecyclerViewAdapter cruisesAdapter;

    public CruisesListFragment()
    {
        super();
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if (activity instanceof MainActivity)
        {
            ((MainActivity) activity).setSortMenuOptionListener(this);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (getActivity() instanceof MainActivity)
        {
            ((MainActivity) getActivity()).setSortMenuOptionListener(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View fragmentView = inflater.inflate(R.layout.fragment_cruises_list, container, true);
        ButterKnife.bind(this, fragmentView);
        setupUI();

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
        this.loadCruisesList();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.cruisesListPresenter.resume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        this.cruisesListPresenter.pause();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.cruisesListPresenter.destroy();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initialize()
    {
        this.getComponent(CruiseComponent.class).inject(this);
        this.cruisesListPresenter.setView(this);
    }

    private void setupUI()
    {
        this.rv_cruise.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        this.cruisesAdapter = new CruisesRecyclerViewAdapter(getActivity(), new ArrayList<CruiseModel>());
        this.rv_cruise.setAdapter(cruisesAdapter);
    }

    // NEW
    @Override
    public void renderCruiseList(List<CruiseModel> cruiseModelCollection)
    {
        if (cruiseModelCollection != null)
        {
            this.cruisesAdapter.setCruisesCollection(cruiseModelCollection);
        }
    }

    private void loadCruisesList()
    {
        this.cruisesListPresenter.initialize();
    }

    @Override
    public void showLoading()
    {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading()
    {
        this.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showRetry()
    {

    }

    @Override
    public void hideRetry()
    {

    }

    @Override
    public void showError(String message)
    {

    }

    @Override
    public void sortListBy(int type)
    {
        this.cruisesAdapter.sortListBy(type);
    }
}
