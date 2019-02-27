package com.mapper;

import com.domain.CommentDao;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by ownlove on 2019/2/25.
 */
@Repository
public interface CommentMapper {
    int insert(CommentDao commentDao);
    List<CommentDao> getCommentByViewShowId(String viewShowId);
}
