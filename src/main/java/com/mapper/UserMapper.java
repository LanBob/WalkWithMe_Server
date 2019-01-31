package com.mapper;

import com.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
	/**
	 * 查询一个用户，通过id
	 * @param id
	 * @return
	 */
	User get(Long id);
	
	/**
	 * 通过id修改密码
	 */
	void change_password(User u);
	/**
	 * 插入一个User账号和密码
	 * @param u
	 */
	void insert(User u);
	/**
	 * 通过id删除一个用户
	 * @param id
	 */
	void delete(Long id);
	
}	
