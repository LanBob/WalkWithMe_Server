package com.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Created by ownlove on 2019/2/28.
 */
@Data
@Component
public class IsGoodMan {

    private String userId;
    private String userName;
    private String sex;
    private String age;
    private String introduce;
    private Integer score;
}
