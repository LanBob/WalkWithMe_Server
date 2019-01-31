package com.domain;

import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class ChatMessageDao {
	public Long from_userID;
	public Long to_userID;
	public String path;
	public String message;
}
