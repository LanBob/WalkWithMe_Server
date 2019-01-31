package com.service;

import com.domain.StarCollectionDao;

/**
 * Created by ownlove on 2019/1/27.
 */
public interface IStarCollectionService {
    StarCollectionDao get(Long viewShowId);
    int update_add_star(Long viewShowId);
    int update_sub_star(Long viewShowId);
    int update_add_collection(Long viewShowId);
    int update_sub_collection(Long viewShowId);
    int insert(StarCollectionDao starCollectionDao);
}
