package com.service;

import com.domain.CollectionDao;

import java.util.List;
import java.util.Map;

/**
 * Created by ownlove on 2019/1/27.
 */
public interface ICollectionService {

    /**
     *
     * @param userID
     * @return
     */
    List<Long> list_collection(Long userID);

    /**
     *
     * @param collectionDao
     * @return
     */
    int insert(CollectionDao collectionDao);

    /**
     *
     * @param map
     * @return
     */
    int delete(Map map);

    CollectionDao get(Map map);
}
