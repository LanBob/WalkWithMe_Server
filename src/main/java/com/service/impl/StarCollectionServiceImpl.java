package com.service.impl;

import com.domain.StarCollectionDao;
import com.mapper.StarCollectionMapper;
import com.service.IStarCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ownlove on 2019/1/27.
 */
@Component
public class StarCollectionServiceImpl implements IStarCollectionService {

    @Autowired
    private StarCollectionMapper starCollectionMapper;

    @Override
    public StarCollectionDao get(Long viewShowId) {
        return starCollectionMapper.get(viewShowId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int update_add_star(Long viewShowId) {
        return starCollectionMapper.update_add_star(viewShowId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int update_sub_star(Long viewShowId) {
        return starCollectionMapper.update_sub_star(viewShowId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int update_add_collection(Long viewShowId) {
        return starCollectionMapper.update_add_collection(viewShowId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int update_sub_collection(Long viewShowId) {
        return starCollectionMapper.update_sub_collection(viewShowId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int insert(StarCollectionDao starCollectionDao) {
        return starCollectionMapper.insert(starCollectionDao);
    }
}
