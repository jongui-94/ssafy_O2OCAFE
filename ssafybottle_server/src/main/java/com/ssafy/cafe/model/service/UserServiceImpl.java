package com.ssafy.cafe.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cafe.model.dao.UserDao;
import com.ssafy.cafe.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	private static UserServiceImpl instance = new UserServiceImpl();

	private UserServiceImpl() {}

	public static UserServiceImpl getInstance() {
		return instance;
	}

	@Autowired
	private UserDao userDao;

	@Override
	public void join(User user) {
		userDao.insert(user);

	}

	@Override
	public User login(String id, String pass) {
		User user = userDao.select(id);
		
		if (user != null && user.getPass().equals(pass)) {
			return user;
		}
		else {
			return null;
		}
	}
	
	@Override
	public User login2(String id, String pass, Integer admin) {
		User user = userDao.select(id);
		
		if (user != null && user.getPass().equals(pass) && admin == 1) {
			return user;
		}
		else {
			return null;
		}
	}

	@Override
	public User info(String id) {
		User user = userDao.select(id);
		
		if (user != null) {
			return user;
		}
		else {
			return null;
		}
	}

	@Override
	public void leave(String id) {
		userDao.delete(id);
	}

	@Override
	public boolean isUsedId(String id) {
		return userDao.select(id) != null;
	}
	
	@Override
	public int checkCash(String id) {
		return userDao.checkCash(id);
	}
	
	@Override
	public int insertToken(String id, String token) {
		User user = userDao.select(id);
		user.setFtoken(token);		
		
		return userDao.update(user);
	}
	
}
