package com.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PersonSettingDao {
	private Long id;
	private String alias;
	private String sex;
	private String love;
	private String introduce;
	private String city;
	private String phone_num;
	private int age;
}
