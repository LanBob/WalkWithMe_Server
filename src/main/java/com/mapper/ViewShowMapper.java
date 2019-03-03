package com.mapper;

import com.domain.ViewShowDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewShowMapper {
	
	/**
	 * 通过View_show_dao进行插入操作
	 * @param dao
	 */
	Long insert(ViewShowDao dao);
	
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
	
	int delete(String id);

	List<ViewShowDao> searchByKeyWord(@Param("title") String keyWord);


//	增加的查询

	/**
	 * 查询已经相互验证的ViewShow
	 * @return
     */
	List<ViewShowDao> getViewShowAlreadyScoreByOthers();

//	List<ViewShowDao> getViewShowNotComplete(String userId);

	List<ViewShowDao> getViewShowByUserId(String userId);
}	
