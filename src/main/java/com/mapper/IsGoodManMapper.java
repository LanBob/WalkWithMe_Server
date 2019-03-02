package com.mapper;

import com.domain.IsGoodMan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ownlove on 2019/2/28.
 */
@Repository
public interface IsGoodManMapper {
    int insert(IsGoodMan isGoodMan);
    int delete(String userId);
    IsGoodMan get(String userId);
    int update(IsGoodMan isGoodMan);
    List<IsGoodMan> listAllNotScore();
    List<IsGoodMan> listAllAlreadyScore();
}
