package com.nadisam.cruceirosvigo.mapper;

import com.nadisam.cruceirosvigo.domain.Cruise;
import com.nadisam.cruceirosvigo.internal.di.PerActivity;
import com.nadisam.cruceirosvigo.model.CruiseModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by santiagopan on 25/02/16.
 * <p>
 * Mapper between {@link Cruise} and {@link CruiseModel}
 */
@PerActivity
public class CruiseModelMapper
{
    @Inject
    public CruiseModelMapper()
    {
    }

    public CruiseModel transform(Cruise cruise)
    {
        if (cruise == null)
        {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        CruiseModel cruiseModel = new CruiseModel();
        cruiseModel.setArrive(cruise.getArrive());
        cruiseModel.setCruise(cruise.getCruise());
        cruiseModel.setLength(cruise.getLength());
        cruiseModel.setDepartureTime(cruise.getDepartureTime());
        cruiseModel.setArriveTime(cruise.getArriveTime());
        cruiseModel.setConsignee(cruise.getConsignee());
        cruiseModel.setFrom(cruise.getFrom());
        cruiseModel.setPicture(cruise.getPicture());
        cruiseModel.setTo(cruise.getTo());

        return cruiseModel;
    }

    public List<CruiseModel> transform(List<Cruise> cruisesCollection)
    {
        List<CruiseModel> cruiseModelCollection;

        if (cruisesCollection != null && !cruisesCollection.isEmpty())
        {
            cruiseModelCollection = new ArrayList<>();
            for (Cruise c : cruisesCollection)
            {
                cruiseModelCollection.add(transform(c));
            }
        }
        else
        {
            cruiseModelCollection = Collections.emptyList();
        }

        return cruiseModelCollection;
    }
}
