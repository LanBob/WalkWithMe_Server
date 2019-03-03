package com.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Created by ownlove on 2019/3/3.
 */
@Data
@Component
public class InterScore {
    private String userId;
    private String viewShowId;
    private String ownToScore;
    private String toBeScored;
}
