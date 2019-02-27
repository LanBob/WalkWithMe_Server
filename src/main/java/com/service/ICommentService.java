package com.service;

import com.domain.CommentDao;

import java.util.List;

/**
 * Created by ownlove on 2019/2/25.
 */
public interface ICommentService {
    int insert(CommentDao commentDao);
    List<CommentDao> getCommentByViewShowId(String viewShowId);
}
