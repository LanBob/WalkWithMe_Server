package com.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class StarCollectionDao {
	private int id;
	private Long view_show_id;
	private int star;
	private int collection;
}
