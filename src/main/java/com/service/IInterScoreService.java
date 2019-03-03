package com.service;

import com.domain.InterScore;

import java.util.List;

/**
 * Created by ownlove on 2019/3/3.
 */
public interface IInterScoreService {
    /**
     * 查询那些可以被UserId进行评分的
     * @return
     */
    List<InterScore> listCanBeScore(String userid);

    /**
     * 1、将这个ViewShowId进行打分，把ViewShowId的TobeScore设置为Y，并判断这个ViewShowId是否可以删除
     * 2、选择userId的其中一个ViewShow的OwnToScore标记为Y，两个原子操作（查询List - > 改OwnToScore）,并判断这个ViewShowId是否可以删除
     * @param viewShowId
     * @return
     */
    void toScoreToViewId(String viewShowId,String userId,int score);


    //    查询那些可以被删除的
    List<InterScore> listCanBeDelete();
    /**
     * 查询此ViewShowId的情况
     * @param viewShowId
     * @return
     */
    InterScore get(String viewShowId);

    int insert(InterScore interScore);

    int update(InterScore interScore);

    int delete(InterScore interScore);

    int deleteByViewShowId(String viewShowId);

    List<InterScore> listUserHavaAnyViewShow(String userId);
}
