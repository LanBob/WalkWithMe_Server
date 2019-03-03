package com.service.impl;

import com.domain.InterScore;
import com.domain.ViewShowDao;
import com.mapper.InterScoreMapper;
import com.mapper.ViewShowMapper;
import com.service.IInterScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ownlove on 2019/3/3.
 */
@Component
public class InterScoreServiceImpl implements IInterScoreService{

    @Autowired
    private InterScoreMapper interScoreMapper;

    @Autowired
    private ViewShowMapper viewShowMapper;

    @Override
    public List<InterScore> listCanBeScore(String userid) {
        return interScoreMapper.listCanBeScore(userid);
    }

    /**
     * 1、将这个ViewShowId进行打分，把ViewShowId的TobeScore设置为Y，并判断这个ViewShowId是否可以删除
     * 2、选择userId的其中一个ViewShow的OwnToScore标记为Y，两个原子操作（查询List - > 改OwnToScore）,并判断这个ViewShowId是否可以删除
     * 3、对这个ViewShowId进行分数相加
     * @param viewShowId
     * @return
     */
    @Transactional
    @Override
    public void toScoreToViewId(String viewShowId, String userId,int score) {
//        第一个操作
        InterScore interScore = interScoreMapper.get(viewShowId);
        interScore.setToBeScored("Y");
        interScoreMapper.update(interScore);

        InterScore interScore1 = interScoreMapper.getCompleteJudge(viewShowId);
        if(interScore1 != null && "Y".equals(interScore1.getOwnToScore()) && "Y".equals(interScore1.getToBeScored())){
            interScoreMapper.delete(interScore1);
        }
//        第一个操作完成

        //        第二个操作
        List<InterScore> listCanBeSetOwnScore = interScoreMapper.listCanBeSetOwnScore(userId);
        if(listCanBeSetOwnScore != null && listCanBeSetOwnScore.size() != 0){
            InterScore interScore2 = listCanBeSetOwnScore.get(0);
            interScore2.setOwnToScore("Y");
            interScoreMapper.update(interScore2);
        }
        //        第二个操作完成

        //        第三个操作
        ViewShowDao viewShowDao = viewShowMapper.get(Long.valueOf(viewShowId));
        int newScore = viewShowDao.getScore() + score;
        viewShowDao.setScore(newScore);
        viewShowMapper.update(viewShowDao);
        //        第三个操作完成

//        第三个任务，进行保存在find_view之中
    }

    @Override
    public List<InterScore> listCanBeDelete() {
        return interScoreMapper.listCanBeDelete();
    }

    @Override
    public InterScore get(String viewShowId) {
        return null;
    }

    @Override
    public int insert(InterScore interScore) {
        return interScoreMapper.insert(interScore);
    }

    @Override
    public int update(InterScore interScore) {
        return interScoreMapper.update(interScore);
    }


    @Override
    public int delete(InterScore interScore) {
        return interScoreMapper.delete(interScore);
    }

    @Override
    public int deleteByViewShowId(String viewShowId) {
        return interScoreMapper.deleteByViewShowId(viewShowId);
    }

    @Override
    public List<InterScore> listUserHavaAnyViewShow(String userId) {
        return interScoreMapper.listUserHavaAnyViewShow(userId);
    }
}
