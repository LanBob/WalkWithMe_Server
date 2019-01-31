package com.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Star {
	private int id;
	private Long view_show_id;
	private Long who_star;
}
