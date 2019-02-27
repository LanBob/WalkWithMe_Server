package com.service.impl;

import com.domain.CommentDao;
import com.mapper.CommentMapper;
import com.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ownlove on 2019/2/25.
 */
@Component
public class CommentServiceImpl implements ICommentService{

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public int insert(CommentDao commentDao) {
        return commentMapper.insert(commentDao);
    }

    @Override
    public List<CommentDao> getCommentByViewShowId(String viewShowId) {
        return commentMapper.getCommentByViewShowId(viewShowId);
    }
}
