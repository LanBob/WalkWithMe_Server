package com.service;

import com.domain.ViewShowDao;

/**
 * Created by ownlove on 2019/1/27.
 */
public interface IViewShowService {
    int insert(ViewShowDao viewShowDao);
    ViewShowDao get(Long id);
}
