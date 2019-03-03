package com.service;

import com.domain.ViewShowDao;
import com.sun.glass.ui.View;

import java.util.List;

/**
 * Created by ownlove on 2019/1/27.
 */
public interface IViewShowService {
    Long insert(ViewShowDao viewShowDao);
    ViewShowDao get(Long id);
    List<ViewShowDao> searchByKeyWord(String keyWord);
    List<ViewShowDao> getViewShowAlreadyScoreByOthers();
    List<ViewShowDao> getViewShowByUserId(String userId);
    int delete(String viewShowId);
    int update(ViewShowDao viewShowDao);
    int wantToUpdate(ViewShowDao viewShowDao);

}
