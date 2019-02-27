package com.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Created by ownlove on 2019/2/27.
 */
@Data
@Component
public class FeedBackDao {
    private String userId;
    private String title;
    private String content;
}
