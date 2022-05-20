package com.ssafy.cafe.model.dao;

import java.util.List;
import com.ssafy.cafe.model.dto.Card;

public interface CardDao {

   List<Card> selectUserCard(String userId);

   int insertCard(Card card);

}
