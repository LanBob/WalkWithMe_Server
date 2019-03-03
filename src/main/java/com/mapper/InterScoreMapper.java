package com.mapper;

import com.domain.InterScore;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ownlove on 2019/3/3.
 */
@Repository
public interface InterScoreMapper {
//    查询那些ToBeScore为Y的ViewShow
    List<InterScore> listCanBeScore(String userId);
    List<InterScore> listCanBeDelete();
    List<InterScore> listCanBeSetOwnScore(String userId);

    List<InterScore> listUserHavaAnyViewShow(String userId);

    /**
     * 查询那些已经可以被删除了的
     * @param viewShowId
     * @return
     */
    InterScore getCompleteJudge(String viewShowId);

    int insert(InterScore interScore);

    int delete(InterScore interScore);

    int deleteByViewShowId(String viewShowId);

    int update(InterScore interScore);

    InterScore get(String viewShowId);
}
