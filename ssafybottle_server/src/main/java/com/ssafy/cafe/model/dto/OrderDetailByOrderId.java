package com.ssafy.cafe.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetailByOrderId {
	
	private Integer quantity;
	private String name;
	
	@Builder
	public OrderDetailByOrderId(Integer quantity, String name) {
		super();
		this.quantity = quantity;
		this.name = name;
	}
}
