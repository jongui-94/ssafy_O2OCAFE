package com.ssafy.cafe.controller.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cafe.model.dto.Card;
import com.ssafy.cafe.model.dto.Comment;
import com.ssafy.cafe.model.dto.Product;
import com.ssafy.cafe.model.service.CardService;
import com.ssafy.cafe.model.service.CommentService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/rest/card")
@CrossOrigin(allowCredentials = "true", originPatterns = { "*" })
public class CardRestController {

	@Autowired
	CardService cService;

	@PostMapping
	@Transactional
	@ApiOperation(value = "card 이용내역를 추가한다.", response = Boolean.class)
	public Boolean insert(@RequestBody Card card) {
		cService.insertCard(card);
		return true;
	}
	
	@GetMapping("/{userId}")
	@ApiOperation(value = "{userId}에 해당하는 카드 이용내역을 반환한다.", response = List.class)
	public List<Card> getProductWithComments(@PathVariable String userId) {
		return cService.selectUserCard(userId);
	}
}