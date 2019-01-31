package com.service;

import com.domain.UserFollowDao;
import com.servlet.UserFollow;

import java.util.List;
import java.util.Map;

/**
 * Created by ownlove on 2019/1/27.
 */
public interface IUserFollowService {
    /**
     *
     * @param userID
     * @return
     */
    List<Long> get_followed_id(Long userID);

    /**
     *
     * @param userFollowDao
     * @return
     */
    int insert(UserFollowDao userFollowDao);

    /**
     *
     * @param map
     * @return
     */
    int delete(Map map);

    UserFollowDao get_user_follow(Map map);
}
