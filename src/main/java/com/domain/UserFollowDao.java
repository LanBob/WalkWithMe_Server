package com.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserFollowDao {
	private Long id;
	private Long follower;
	private Long followed;
}
