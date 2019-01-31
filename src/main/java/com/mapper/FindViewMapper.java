package com.mapper;

import java.util.List;

import com.domain.FindViewDao;
import org.springframework.stereotype.Repository;

/**
 * 发现页面
 */
@Repository
public interface FindViewMapper {
	int delete(Long id);
	
	int insert(FindViewDao dao);
	
	//通过UserId进行查询
	List<FindViewDao> get_list_by_userId(Long userId);
	
	//通过type进行查询
	List<FindViewDao> get_list_by_type(int type);
	
	FindViewDao get_by_view_showID(Long id);
}
