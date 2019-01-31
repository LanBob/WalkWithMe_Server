package com.mapper;

import com.domain.PersonSettingDao;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonSettingMapper {
	
	int insert(PersonSettingDao person);
	
	int update(PersonSettingDao person);
	
	
	/**
	 * 通过id获取一个Person_setting_dao
	 * @param id
	 * @return
	 */
	PersonSettingDao get(Long id);
	
	
	/**
	 * 通过id删除这个设置
	 * @param id
	 */
	int delete(Long id);
	
}
