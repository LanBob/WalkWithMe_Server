package com.service;

import com.domain.PersonDao;

/**
 * Created by ownlove on 2019/1/27.
 */
public interface IPersonDaoService {
    /**
     *
     * @param userId
     * @return
     */
    PersonDao get(Long userId);
}
