package com.ssafy.cafe.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ssafy.cafe.model.dto.Comment;

class CommentTest extends AbstractDaoTest {

	Integer productId = 1;

	@Test
	@org.junit.jupiter.api.Order(1)
	public void selectTest() {
		Comment comment = cDao.select(1);
		assertEquals(comment.getUserId(), "ssafy01");

	}

	@Test
	@org.junit.jupiter.api.Order(2)
	public void selectAll() {
		List<Comment> result = cDao.selectAll();
		assertEquals(result.size(), 10);
		System.out.println(result);

		Comment selected = result.get(0);
		assertEquals(selected.getUserId(), "ssafy10");
	}

	@Test
	@org.junit.jupiter.api.Order(3)
	public void insertTest() {
		Comment comment = new Comment("ssafy01", productId, 5.0, "좋음");
		int result = cDao.insert(comment);
		assertEquals(result, 1);
	}

	@Test
	@org.junit.jupiter.api.Order(4)
	public void selectAllByProduct() {
		List<Comment> result = cDao.selectByProduct(productId);
		assertEquals(result.size(), 4);
		last = result.get(0);
		System.out.println(last);
		assertEquals(result.get(0).getUserId(), "ssafy01");
	}

	static Comment last = null;

	@Test
	@org.junit.jupiter.api.Order(5)
	public void updateTest() {
		Comment selected = cDao.select(last.getId());
		selected.setComment("더 좋아짐");
		int result = cDao.update(selected);
		assertEquals(result, 1);

		Comment selected2 = cDao.select(selected.getId());
		assertEquals(selected2.getComment(), selected.getComment());
	}

	@Test
	@org.junit.jupiter.api.Order(6)
	public void deleteTest() {
		Comment selected = cDao.select(last.getId());
		int result = cDao.delete(selected.getId());
		assertEquals(result, 1);

		Comment selected2 = cDao.select(selected.getId());
		assertNull(selected2);
	}
}
