package com.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CollectionDao {
	public CollectionDao(){
	}
	private int id;
	private Long view_show_id;
	private Long who_collection;
}
