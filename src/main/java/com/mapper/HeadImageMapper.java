package com.mapper;

import com.domain.HeadImage;
import org.springframework.stereotype.Repository;

/**
 * Created by ownlove on 2019/2/20.
 */
@Repository
public interface HeadImageMapper {
    int insert(HeadImage dao);
    HeadImage getHeadImageByID(String id);
    int update(HeadImage headImage);
}
