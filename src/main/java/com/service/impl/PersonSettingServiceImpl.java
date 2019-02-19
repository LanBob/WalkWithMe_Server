package com.service.impl;

import com.domain.PersonSettingDao;
import com.mapper.PersonSettingMapper;
import com.service.IPersonSettingService;
import com.servlet.PersonSetting;
import com.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ownlove on 2019/1/27.
 */
@Component
public class PersonSettingServiceImpl implements IPersonSettingService {

    @Autowired
    private PersonSettingMapper personSettingMapper;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void insert(PersonSettingDao personSettingDao) {
        personSettingMapper.insert(personSettingDao);
    }

    @Override
    public PersonSettingDao getById(String id) {
        Long userId = null;
        if(id != null && id != ""){
            userId = Long.valueOf(id);
        }
        return personSettingMapper.get(userId);
    }
}
