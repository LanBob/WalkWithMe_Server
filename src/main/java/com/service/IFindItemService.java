package com.service;

import com.domain.FindViewDao;

import java.util.List;

/**
 * Created by ownlove on 2019/1/26.
 */
public interface IFindItemService {
    List<FindViewDao> getFindViewByType(int type);
}
