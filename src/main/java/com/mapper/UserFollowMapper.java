package com.mapper;

import java.util.List;
import java.util.Map;

import com.domain.UserFollowDao;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFollowMapper {
	/**
	 * 通过id获取他所关注的guide
	 */
	List<UserFollowDao> get(Long id);
	
	List<Long> get_followed_id(Long id);
	
	UserFollowDao get_user_follow(Map map);
	
	/**
	 * 通过一个User_follow_dao进行插入操作
	 * @param dao
	 */
	int insert(UserFollowDao dao);
	
	/**
	 * 通过tour_id和guide_id进行取消关注操作
	 */
	int delete(Map map);
	
}	
