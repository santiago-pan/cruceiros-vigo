package com.nadisam.data.entity.mapper;

import com.nadisam.cruceirosvigo.domain.Cruise;
import com.nadisam.data.entity.CruiseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by santiagopan on 21/02/16.
 */
@Singleton
public class CruiseEntityDataMapper
{
    @Inject
    public CruiseEntityDataMapper()
    {
    }

    /**
     * Transform a {@link CruiseEntity} into an {@link Cruise}.
     *
     * @param cruiseEntity Object to be transformed.
     * @return {@link Cruise} if valid {@link CruiseEntity} otherwise null.
     */
    public Cruise transform(CruiseEntity cruiseEntity) {
        Cruise user = null;
        if (cruiseEntity != null) {
            user = new Cruise();
            user.setArriveTime(cruiseEntity.getArriveTime());
            user.setArrive(cruiseEntity.getArrive());
            user.setTo(cruiseEntity.getTo());
            user.setFrom(cruiseEntity.getFrom());
            user.setDepartureTime(cruiseEntity.getDepartureTime());
            user.setCruise(cruiseEntity.getCruise());
            user.setLength(cruiseEntity.getLength());
        }

        return user;
    }

    /**
     * Transform a List of {@link CruiseEntity} into a Collection of {@link Cruise}.
     *
     * @param cruiseEntityCollection Object Collection to be transformed.
     * @return {@link Cruise} if valid {@link CruiseEntity} otherwise null.
     */
    public List<Cruise> transform(Collection<CruiseEntity> cruiseEntityCollection) {
        List<Cruise> cruiseList = new ArrayList<>();
        Cruise cruise;
        for (CruiseEntity cruiseEntity : cruiseEntityCollection) {

            cruise = transform(cruiseEntity);
            if (cruise != null) {
                cruiseList.add(cruise);
            }
        }

        return cruiseList;
    }
}
