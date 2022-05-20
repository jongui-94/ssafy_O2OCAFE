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
import com.ssafy.cafe.model.dto.User;
import com.ssafy.cafe.model.service.CardService;
import com.ssafy.cafe.model.service.CommentService;
import com.ssafy.cafe.model.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/rest/token")
@CrossOrigin(allowCredentials = "true", originPatterns = { "*" })
public class TokenRestController {
	
	@Autowired
	UserService uService;
	

	@PostMapping
	@Transactional
	@ApiOperation(value = "token을 추가한다", response = Boolean.class)
	public Boolean insert(@RequestBody Map<String, String> request) {
	    uService.insertToken(request.get("userId"), request.get("token"));
		return true;
	}
	
}