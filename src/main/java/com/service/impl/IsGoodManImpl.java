package com.service.impl;

import com.domain.IsGoodMan;
import com.mapper.IsGoodManMapper;
import com.service.IIsGoodManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ownlove on 2019/2/28.
 */
@Component
public class IsGoodManImpl implements IIsGoodManService{

    @Autowired
    private IsGoodManMapper isGoodManMapper;


    @Override
    public IsGoodMan get(String userId) {
        return isGoodManMapper.get(userId);
    }

    @Override
    public List<IsGoodMan> listAllNotScore() {
        return isGoodManMapper.listAllNotScore();
    }

    @Override
    public List<IsGoodMan> listAllAlreadyScore() {
        return isGoodManMapper.listAllAlreadyScore();
    }


    @Transactional
    @Override
    public int insert(IsGoodMan isGoodMan) {
        IsGoodMan isGoodMan1 = isGoodManMapper.get(isGoodMan.getUserId());
        if(isGoodMan1 != null && isGoodMan1.getIntroduce() != null){
            return isGoodManMapper.update(isGoodMan);
        }
        return isGoodManMapper.insert(isGoodMan);
    }

    @Override
    public int delete(String userId) {
        return isGoodManMapper.delete(userId);
    }

    @Override
    public int update(IsGoodMan isGoodMan) {
        return isGoodManMapper.update(isGoodMan);
    }
}
