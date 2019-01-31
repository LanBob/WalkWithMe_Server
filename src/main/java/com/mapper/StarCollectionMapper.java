package com.mapper;

import com.domain.StarCollectionDao;
import org.springframework.stereotype.Repository;

@Repository
public interface StarCollectionMapper {
	
	StarCollectionDao get(Long view_show_id);

	int insert(StarCollectionDao dao);

	int update_add_star(Long view_show_id);

	int update_sub_star(Long view_show_id);

	int update_add_collection(Long view_show_id);

	int update_sub_collection(Long view_show_id);

	int delete(Long view_show_id);
}
