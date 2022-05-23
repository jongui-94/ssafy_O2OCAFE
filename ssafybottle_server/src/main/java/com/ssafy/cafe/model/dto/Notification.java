package com.ssafy.cafe.model.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Notification {
	private Integer id;
	private String userId;
	private String title;
	private String content;
	private Date time;
	
	@Builder
	public Notification(Integer id, String userId, String title, String content, Date time) {
		super();
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.time = time;
	}
	
	public Notification(String userId, String title, String content) {
		super();
		this.userId = userId;
		this.title = title;
		this.content = content;
	}

}
