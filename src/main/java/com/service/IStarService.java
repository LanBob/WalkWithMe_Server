package com.service;

import com.domain.Star;

import java.util.List;
import java.util.Map;

/**
 * Created by ownlove on 2019/1/27.
 */
public interface IStarService {

    /**
     * 通过userId获取此人所Star过的ViewShow的ID
     * @param userId
     * @return
     */
    List<Long> listStarViewShowIdByUserId(Long userId);

    /**
     * 插入一个Star记录
     * @param star
     * @return
     */
    int insert(Star star);

    int delete(Map map);

    Star get(Map map);

}
