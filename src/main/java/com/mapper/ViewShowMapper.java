package com.mapper;

import com.domain.ViewShowDao;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewShowMapper {
	
	/**
	 * 通过View_show_dao进行插入操作
	 * @param dao
	 */
	int insert(ViewShowDao dao);
	
	/**
	 * 通过type属性得出这个类别所有的数据
	 *
	 * @return
	 */
	ViewShowDao get(Long id);
	
	/**
	 * 通过View_show_dao重设这个旅游照
	 * @param dao
	 */
	int update(ViewShowDao dao);
	
	int delete(Long id);
	
}	
