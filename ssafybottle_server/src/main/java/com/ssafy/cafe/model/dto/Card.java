package com.ssafy.cafe.model.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Card {
	
	private Integer id;
	private String userId;
	private Integer orderId;
	private Integer payment;
	private String content;
	private Date payTime;
	
	@Builder
	public Card(Integer id, String userId, Integer orderId, Integer payment, String content, Date payTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.orderId = orderId;
		this.payment = payment;
		this.content = content;
		this.payTime = payTime; 
	}

}
