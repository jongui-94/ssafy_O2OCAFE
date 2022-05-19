package com.ssafy.cafe.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ssafy.cafe.model.dto.Product;


class ProductTest extends AbstractDaoTest{

	@Test
	@org.junit.jupiter.api.Order(1)
	public void selectTest() {
		Product selected = pDao.select(1);
		assertEquals(selected.getName(), "아메리카노");

	}

	@Test
	@org.junit.jupiter.api.Order(2)
	public void insertTest() {
		Product data = new  Product("좋은상품", "쿠키", 100, "abc.png");
		int result = pDao.insert(data);
		assertEquals(result, 1);
	}

	static Product last;

	@Test
	@org.junit.jupiter.api.Order(3)
	public void selectAll() {
		List<Product> result = pDao.selectAll();
		assertEquals(result.size(), 11);
		last = result.get(0);

		assertEquals(last.getType(), "쿠키");
	}

	@Test
	@org.junit.jupiter.api.Order(4)
	public void updateTest() {
		last.setType("coffee");
		int result = pDao.update(last);
		assertEquals(result, 1);

		Product selected = pDao.select(last.getId());
		assertEquals(last.getType(), selected.getType());
	}

	@Test
	@org.junit.jupiter.api.Order(5)
	public void deleteTest() {
		int result = pDao.delete(last.getId());
		assertEquals(result, 1);

		Product selected = pDao.select(last.getId());
		assertNull(selected);
	}


}
