package com.domain;

import java.io.File;
import java.nio.ByteBuffer;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MessageDao {
	private Long  to_user;
	private Long from_user;
	private ByteBuffer bytebuffer;
	private String text;
	private Long time;
}
