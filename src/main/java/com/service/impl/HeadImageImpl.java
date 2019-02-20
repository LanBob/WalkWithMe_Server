package com.service.impl;

import com.domain.HeadImage;
import com.mapper.HeadImageMapper;
import com.service.IHeadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ownlove on 2019/2/20.
 */
@Component
public class HeadImageImpl implements IHeadImageService{

    @Autowired
    private HeadImageMapper headImageMapper;

    @Override
    public int insert(HeadImage dao) {
        HeadImage headImage = headImageMapper.getHeadImageByID(dao.getId());
        if (headImage == null || headImage.getHead_image() == null){
            return headImageMapper.insert(dao);
        }else {
            return this.update(dao);
        }
    }

    @Override
    public HeadImage get(String id) {
        return headImageMapper.getHeadImageByID(id);
    }

    @Override
    public int update(HeadImage dao) {
        return headImageMapper.update(dao);
    }
}
