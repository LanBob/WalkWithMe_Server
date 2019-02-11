package com.service.impl;

import com.domain.ViewShowDao;
import com.mapper.ViewShowMapper;
import com.service.IViewShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ownlove on 2019/1/27.
 */
@Component
public class ViewShowService implements IViewShowService {

    @Autowired
    private ViewShowMapper viewShowMapper;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int insert(ViewShowDao viewShowDao) {
        return viewShowMapper.insert(viewShowDao);
    }

    @Override
    public ViewShowDao get(Long id) {
        return viewShowMapper.get(id);
    }

    @Override
    public List<ViewShowDao> searchByKeyWord(String keyWord) {
        return viewShowMapper.searchByKeyWord(keyWord);
    }
}
