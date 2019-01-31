package com.mapper;

import java.util.List;
import java.util.Map;

import com.domain.CollectionDao;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionMapper {
	int insert(CollectionDao dao);
	
	CollectionDao get(Map map);

	int delete(Map map);

	List<Long> list_collection(Long who_collection);

}
