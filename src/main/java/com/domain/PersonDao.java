package com.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PersonDao {
	private Long id;
	private String name;
	private String introduce;
	private String sex;
}	
