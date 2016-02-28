package com.nadisam.data.repository;

import com.nadisam.cruceirosvigo.domain.Cruise;
import com.nadisam.cruceirosvigo.domain.repository.DataRepositoryInterface;
import com.nadisam.data.entity.mapper.CruiseEntityDataMapper;
import com.nadisam.data.entity.mapper.WebContentMapper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by santiagopan on 21/02/16.
 */
@Singleton
public class DataRepository implements DataRepositoryInterface
{
    private final WebContentMapper webContentMapper;
    private final CruiseEntityDataMapper cruiseEntityDataMapper;

    @Inject
    public DataRepository(WebContentMapper webContentMapper, CruiseEntityDataMapper cruiseEntityDataMapper)
    {
        this.webContentMapper = webContentMapper;
        this.cruiseEntityDataMapper = cruiseEntityDataMapper;
    }

    @Override
    public Observable<List<Cruise>> getCruises()
    {
        return webContentMapper.getCruises().map(this.cruiseEntityDataMapper::transform);
    }
}
