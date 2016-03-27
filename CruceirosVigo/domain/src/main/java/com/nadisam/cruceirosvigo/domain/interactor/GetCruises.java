package com.nadisam.cruceirosvigo.domain.interactor;

import com.nadisam.cruceirosvigo.domain.Cruise;
import com.nadisam.cruceirosvigo.domain.executor.PostExecutionThread;
import com.nadisam.cruceirosvigo.domain.executor.ThreadExecutor;
import com.nadisam.cruceirosvigo.domain.repository.DataRepositoryInterface;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by santiagopan on 22/02/16.
 */
public class GetCruises implements GetCruisesInterface
{
    private final DataRepositoryInterface dataRepositoryInterface;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private Subscription subscription = Subscriptions.empty();

    @Inject
    public GetCruises(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                      DataRepositoryInterface dataRepositoryInterface)
    {
        this.dataRepositoryInterface = dataRepositoryInterface;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void getCruises(Subscriber<List<Cruise>> useCaseSubscriber)
    {
        this.subscription =  dataRepositoryInterface.getCruises()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(useCaseSubscriber);
    }

    /**
     * Unsubscribes from current {@link rx.Subscription}.
     */
    @Override
    public void unsubscribe()
    {
        if (!subscription.isUnsubscribed())
        {
            subscription.unsubscribe();
        }
    }
}
