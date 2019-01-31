package com.service.impl;

import com.domain.Star;
import com.mapper.StarMapper;
import com.service.IStarService;
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
public class StarServiceImpl implements IStarService{

    @Autowired
    private StarMapper starMapper;

    @Override
    public List<Long> listStarViewShowIdByUserId(Long userId) {
        return starMapper.list_star(userId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int insert(Star star) {
        return starMapper.insert(star);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int delete(Map map) {
        return starMapper.delete(map);
    }

    @Override
    public Star get(Map map) {
        return starMapper.get(map);
    }


}
