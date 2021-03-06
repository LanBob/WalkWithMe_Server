package com.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 具体一点的旅游信息页面
 */
@Data
@Component
public class ViewShowDao {
	
	private String title;
	private String introduce;
	private String city;
	private Long user_id;
	private int type;
	private BigDecimal money;
	private Long id;
	private String defaultpath;
	private String myTime;

//	新增
	private String route;
	private String friendlyToEat;
	private String firendlyToLive;
	private String detailAddress;
	private int score;
}
