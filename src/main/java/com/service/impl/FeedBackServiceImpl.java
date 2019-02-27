package com.service.impl;

import com.domain.FeedBackDao;
import com.mapper.FeedBackMapper;
import com.service.IFeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ownlove on 2019/2/27.
 */
@Component
public class FeedBackServiceImpl implements IFeedBackService{

    @Autowired
    private FeedBackMapper feedBackMapper;

    @Override
    public int insert(FeedBackDao feedBackDao) {
        return feedBackMapper.insert(feedBackDao);
    }

    @Override
    public List<FeedBackDao> getFeedBackList() {
        return feedBackMapper.getFeedBackList();
    }

    @Override
    public List<FeedBackDao> getFeedBackListByUserId(String userId) {
        return feedBackMapper.getFeedBackListByUserId(userId);
    }
}
