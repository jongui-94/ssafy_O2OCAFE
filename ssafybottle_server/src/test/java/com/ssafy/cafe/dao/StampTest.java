package com.ssafy.cafe.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ssafy.cafe.model.dto.Stamp;

class StampTest extends AbstractDaoTest{

	@Test
	@org.junit.jupiter.api.Order(1)
	public void selectTest() {
		Stamp selected = sDao.select(1);
		assertEquals(selected.getUserId(), "ssafy01");

	}

	@Test
	@org.junit.jupiter.api.Order(2)
	public void insertTest() {
		Stamp data = new Stamp("ssafy02", 1,10);
		int result = sDao.insert(data);
		assertEquals(result, 1);
	}
	static Stamp last;

	@Test
	@org.junit.jupiter.api.Order(3)
	public void selectAll() {
		List<Stamp> result = sDao.selectAll();

		last = result.get(0);
		assertEquals(last.getQuantity(), 10);
	}

	@Test
	public void selectByUser() {
		List<Stamp> result = sDao.selectByUserId("ssafy01");

		Stamp selected = result.get(0);
		assertEquals(selected.getUserId(), "ssafy01");
	}
}
