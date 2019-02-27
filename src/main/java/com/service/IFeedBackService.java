package com.service;

import com.domain.FeedBackDao;

import java.util.List;

/**
 * Created by ownlove on 2019/2/27.
 */
public interface IFeedBackService {
    int insert(FeedBackDao feedBackDao);
    List<FeedBackDao> getFeedBackList();
    List<FeedBackDao> getFeedBackListByUserId(String userId);
}
