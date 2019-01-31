package com.mapper;

import java.util.List;
import java.util.Map;

import com.domain.Star;
import org.springframework.stereotype.Repository;

@Repository
public interface StarMapper {
	
	int insert(Star dao);
	
	Star get(Map map);
	
	int delete(Map map);

	List<Long> list_star(Long who_star);
}	
