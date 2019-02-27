package com.mapper;

import com.domain.FeedBackDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ownlove on 2019/2/27.
 */
@Repository
public interface FeedBackMapper {
    int insert(FeedBackDao feedBackDao);
    List<FeedBackDao> getFeedBackList();
    List<FeedBackDao> getFeedBackListByUserId(String userId);
}
