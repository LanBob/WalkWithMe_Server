package com.service;

import com.domain.IsGoodMan;

import java.util.List;

/**
 * Created by ownlove on 2019/2/28.
 */
public interface IIsGoodManService {
    IsGoodMan get(String userId);
    List<IsGoodMan> listAllNotScore();
    List<IsGoodMan> listAllAlreadyScore();
    int insert(IsGoodMan isGoodMan);
    int delete(String userId);
    int update(IsGoodMan isGoodMan);
}
