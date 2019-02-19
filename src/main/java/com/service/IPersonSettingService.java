package com.service;

import com.domain.PersonSettingDao;
import com.servlet.PersonSetting;

/**
 * Created by ownlove on 2019/1/27.
 */
public interface IPersonSettingService {
    void insert(PersonSettingDao personSettingDao);
    PersonSettingDao getById(String id);
}
