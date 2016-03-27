package com.nadisam.cruceirosvigo.domain.interactor;

import com.nadisam.cruceirosvigo.domain.Cruise;

import java.util.List;

import rx.Subscriber;

/**
 * CruceirosVigo
 * <p>
 * Created by santiagopan on 27/03/16.
 */
public interface GetCruisesInterface
{
    /**
     * Returns a {@link rx.Subscriber} with a list of {@link Cruise}
     *
     * @param useCaseSubscriber Returned subscriber.
     */
    void getCruises(Subscriber<List<Cruise>> useCaseSubscriber);

    /**
     * Unsubscribes from current {@link rx.Subscription}.
     */
    void unsubscribe();
}
