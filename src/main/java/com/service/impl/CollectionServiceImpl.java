package com.service.impl;

import com.domain.CollectionDao;
import com.mapper.CollectionMapper;
import com.service.ICollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by ownlove on 2019/1/27.
 */
@Component
public class CollectionServiceImpl implements ICollectionService {

    @Autowired
    CollectionMapper collectionMapper;

    @Override
    public List<Long> list_collection(Long userID) {
        return collectionMapper.list_collection(userID);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int insert(CollectionDao collectionDao) {
        return collectionMapper.insert(collectionDao);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int delete(Map map) {
        return collectionMapper.delete(map);
    }

    @Override
    public CollectionDao get(Map map) {
        return collectionMapper.get(map);
    }
}
