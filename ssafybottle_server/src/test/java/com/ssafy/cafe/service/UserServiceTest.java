package com.ssafy.cafe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.ssafy.cafe.model.dto.User;

class UserServiceTest extends AbstractServiceTest {
	private User testUser = new User("test", "test", "test", 0);

	@Test
	@org.junit.jupiter.api.Order(1)
	void joinTest() {
		// 회원 가입은 예외만 없으면 진행
		userService.join(testUser);

		// 동일 사용자 회원 가입 시 예외 발생할것
		assertThrows(Exception.class, () -> {
			userService.join(testUser);
		});

	}

	@Test
	@org.junit.jupiter.api.Order(2)
	void loginTest() {
		User selected = userService.login(testUser.getId(), testUser.getPass());
		assertEquals(selected.getName(), testUser.getName());

		selected = userService.login("some", "some");
		assertNull(selected);
	}

	@Test
	@org.junit.jupiter.api.Order(3)
	void leaveTest() {
		userService.leave(testUser.getId());
		User selected = userService.login(testUser.getId(), testUser.getPass());
		assertNull(selected);
	}

	@Test
	@org.junit.jupiter.api.Order(4)
	void isUsedTest() {
		assertEquals(true, userService.isUsedId(user.getId()));
		assertEquals(false, userService.isUsedId(testUser.getId()));
	}

}
