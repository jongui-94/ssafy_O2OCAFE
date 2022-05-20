package com.ssafy.cafe.model.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

	private String id;
	private String name;
	private String pass;
	private Integer cash;
	private Integer stamps;
	private String ftoken;
	private Integer admin;
	private List<Stamp> stampList = new ArrayList<>();


	@Builder
	public User(String id, String name, String pass, Integer cash, Integer stamps, String ftoken, Integer admin) {
		super();
		this.id = id;
		this.name = name;
		this.pass = pass;
		this.cash = cash;
		this.stamps = stamps;
		this.ftoken = ftoken;
		this.admin = admin;
	}

	public User(String id, String name, String pass, Integer admin) {
		super();
		this.id = id;
		this.name = name;
		this.pass = pass;
		this.admin = admin;
	}


}
