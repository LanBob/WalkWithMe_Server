package com.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Created by ownlove on 2019/2/25.
 */
@Data
@Component
public class CommentDao {
    private String userId;
    private String comment;
    private String viewShowId;
    private String defaultImage;
    private String userName;
    private String mytime;
}
