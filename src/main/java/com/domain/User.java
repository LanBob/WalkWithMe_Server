package com.domain;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class User {
	private Long id;
	private String password;
}