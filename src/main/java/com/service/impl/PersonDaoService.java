package com.service.impl;

import com.domain.PersonDao;
import com.mapper.PersonDaoMapper;
import com.service.IPersonDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ownlove on 2019/1/27.
 */
@Component
public class PersonDaoService implements IPersonDaoService{
    @Autowired
    private PersonDaoMapper personDaoMapper;
    @Override
    public PersonDao get(Long userId) {
        return personDaoMapper.get(userId);
    }
}
