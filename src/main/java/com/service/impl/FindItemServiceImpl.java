package com.service.impl;

import com.domain.FindViewDao;
import com.mapper.FindViewMapper;
import com.service.IFindItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ownlove on 2019/1/26.
 */
@Service
public class FindItemServiceImpl implements IFindItemService {

    @Autowired
    private FindViewMapper mapper;

    @Override
    public List<FindViewDao> getFindViewByType(int type) {
//        List<FindViewDao> list = mapper.get_list_by_type(type);
//        for (FindViewDao findViewDao : list) {
//            System.out.println(findViewDao.toString());
//        }
        return mapper.get_list_by_type(type);
    }

    @Override
    public List<FindViewDao> getFindViewByUserId(String userId) {
        return mapper.get_list_by_userId(userId);
    }
}
