package com.nadisam.cruceirosvigo.presenter;

import android.support.annotation.NonNull;

import com.nadisam.cruceirosvigo.domain.Cruise;
import com.nadisam.cruceirosvigo.domain.interactor.GetCruisesInterface;
import com.nadisam.cruceirosvigo.internal.di.PerActivity;
import com.nadisam.cruceirosvigo.mapper.CruiseModelMapper;
import com.nadisam.cruceirosvigo.model.CruiseModel;
import com.nadisam.cruceirosvigo.view.CruisesListView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Subscriber;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class CruisesListPresenter implements Presenter
{

    private CruisesListView viewListView;
    private GetCruisesInterface getCruisesUseCase;
    private CruiseModelMapper cruiseModelMapper;

    @Inject
    public CruisesListPresenter(@Named("getCruises") GetCruisesInterface getCruisesUseCase,
                                CruiseModelMapper cruiseModelMapper)
    {
        this.getCruisesUseCase = getCruisesUseCase;
        this.cruiseModelMapper = cruiseModelMapper;
    }

    public void setView(@NonNull CruisesListView view)
    {
        this.viewListView = view;
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void destroy()
    {
        this.getCruisesUseCase.unsubscribe();
    }

    /**
     * Initializes the presenter by start retrieving the user list.
     */
    public void initialize()
    {
        this.loadCruisesList();
    }

    /**
     * Loads all users.
     */
    private void loadCruisesList()
    {
        this.hideViewRetry();
        this.showViewLoading();
        this.getCruisesList();
    }

    private void showViewLoading()
    {
        this.viewListView.showLoading();
    }

    private void hideViewLoading()
    {
        this.viewListView.hideLoading();
    }

    private void showViewRetry()
    {
        this.viewListView.showRetry();
    }

    private void hideViewRetry()
    {
        this.viewListView.hideRetry();
    }

    private void showUsersCollectionInView(List<Cruise> cruisesCollection)
    {
        final List<CruiseModel> cruisesModelCollection =
                this.cruiseModelMapper.transform(cruisesCollection);
        this.viewListView.renderCruiseList(cruisesModelCollection);
    }

    private void getCruisesList()
    {
        this.getCruisesUseCase.getCruises(new CruisesListSubscriber());
    }

    // Subscriber to receive cruises list from observable
    private final class CruisesListSubscriber extends Subscriber<List<Cruise>>
    {

        @Override
        public void onCompleted()
        {
            CruisesListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e)
        {
            CruisesListPresenter.this.hideViewLoading();
            CruisesListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Cruise> cruises)
        {
            CruisesListPresenter.this.showUsersCollectionInView(cruises);
        }
    }
}
