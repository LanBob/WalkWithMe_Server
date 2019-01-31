package com.service.impl;

import com.domain.UserFollowDao;
import com.mapper.UserFollowMapper;
import com.service.IUserFollowService;
import com.servlet.UserFollow;
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
public class UserFollowService implements IUserFollowService {

    @Autowired
    private UserFollowMapper userFollowMapper;

    @Override
    public List<Long> get_followed_id(Long userID) {
        return userFollowMapper.get_followed_id(userID);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int insert(UserFollowDao userFollowDao) {
        return userFollowMapper.insert(userFollowDao);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int delete(Map map) {
        return userFollowMapper.delete(map);
    }

    @Override
    public UserFollowDao get_user_follow(Map map) {
        return userFollowMapper.get_user_follow(map);
    }
}
