package com.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class FindViewDao {
	private String title;
	private String city;
	private Long user_id;
	private int type;
	private BigDecimal money;
	private Long id;
	private int star;
	private String defaultpath;
}
