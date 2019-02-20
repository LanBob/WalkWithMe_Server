package com.service;

import com.domain.HeadImage;

/**
 * Created by ownlove on 2019/2/20.
 */
public interface IHeadImageService {
    int insert(HeadImage dao);
    HeadImage get(String id);
    int update(HeadImage dao);
}
