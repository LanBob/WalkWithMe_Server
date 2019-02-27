package com.service;

import com.domain.FindViewDao;

/**
 * Created by ownlove on 2019/1/27.
 */
public interface IFindViewService {

    /**
     * 通过ViewShowID获取FindViewDao
     * @param viewShowId
     * @return
     */
    FindViewDao get_by_view_showID(Long viewShowId);

    int insert(FindViewDao findViewDao);

    int addStar(Long id);

    int subStar(Long id);
}
