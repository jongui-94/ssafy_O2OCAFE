package com.ssafy.cafe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cafe.model.dao.CardDao;
import com.ssafy.cafe.model.dao.CommentDao;
import com.ssafy.cafe.model.dao.UserDao;
import com.ssafy.cafe.model.dto.Card;
import com.ssafy.cafe.model.dto.Comment;
import com.ssafy.cafe.model.dto.User;

@Service
public class CardServiceImpl implements CardService {

	@Autowired
	CardDao cDao;
	
	@Autowired
	UserDao uDao;

	@Override
	@Transactional
	public List<Card> selectUserCard(String userId) {
		return cDao.selectUserCard(userId);
	}

	@Override
	@Transactional
	public int insertCard(Card card) {
		User user = uDao.select(card.getUserId()); 		
		user.setCash(user.getCash() + card.getPayment());
		uDao.update(user);
		
		return cDao.insertCard(card);
	}

}
