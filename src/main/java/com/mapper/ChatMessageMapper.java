package com.mapper;

import java.util.List;

import com.domain.ChatMessageDao;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageMapper {
	
	/**
	 * 当to_userID已经上线，读取了数据以后，就掉该信息
	 * @param to_userID
	 */
	void delete(Long to_userID);
	
	/**
	 * 当to_userID已经下线的时候，通过插入一个数据，缓存此消息
	 * @param dao
	 */
	void insert(ChatMessageDao dao);
	
	/**
	 * 通过to_userID，获取所有的这些信息
	 * @param to_userID
	 * @return
	 */
	List<ChatMessageDao> listAll(Long to_userID);
	
}
