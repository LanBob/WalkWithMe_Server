package com.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ResponseResult<T> {
	private int code;
    private String message;
    private T data;
}
