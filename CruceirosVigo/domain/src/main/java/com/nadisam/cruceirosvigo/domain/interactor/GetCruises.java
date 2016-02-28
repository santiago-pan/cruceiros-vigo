package com.nadisam.cruceirosvigo.domain.interactor;

import com.nadisam.cruceirosvigo.domain.Cruise;
import com.nadisam.cruceirosvigo.domain.executor.PostExecutionThread;
import com.nadisam.cruceirosvigo.domain.executor.ThreadExecutor;
import com.nadisam.cruceirosvigo.domain.repository.DataRepositoryInterface;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by santiagopan on 22/02/16.
 */
public class GetCruises
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

    public void getCruises(Subscriber UseCaseSubscriber)
    {
        this.subscription =  dataRepositoryInterface.getCruises()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(UseCaseSubscriber);
    }

    /**
     * Unsubscribes from current {@link rx.Subscription}.
     */
    public void unsubscribe()
    {
        if (!subscription.isUnsubscribed())
        {
            subscription.unsubscribe();
        }
    }
}
