package com.ssafy.cafe.model.service;

import java.util.List;

import com.ssafy.cafe.model.dto.Card;

public interface CardService {
	
	/**
	 * User의 카드 이용내역을 반환한다.
	 * @param userId
	 */
	List<Card> selectUserCard(String userId);
	
	/**
	 * Card 이용내역을 등록한다.
	 * @param card
	 */	
	int insertCard(Card card);	
}
