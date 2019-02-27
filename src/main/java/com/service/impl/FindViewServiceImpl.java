package com.service.impl;

import com.domain.FindViewDao;
import com.mapper.FindViewMapper;
import com.service.IFindViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ownlove on 2019/1/27.
 */
@Component
public class FindViewServiceImpl implements IFindViewService {

    @Autowired
    FindViewMapper findViewMapper;

    @Override
    public FindViewDao get_by_view_showID(Long viewShowId) {
        return findViewMapper.get_by_view_showID(viewShowId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int insert(FindViewDao findViewDao) {
        return findViewMapper.insert(findViewDao);
    }

    @Override
    public int addStar(Long id) {
        return findViewMapper.addStar(id);
    }

    @Override
    public int subStar(Long id) {
        return findViewMapper.subStar(id);
    }


}
