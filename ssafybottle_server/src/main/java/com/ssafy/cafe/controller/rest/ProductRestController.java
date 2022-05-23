package com.ssafy.cafe.controller.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cafe.model.dto.Order;
import com.ssafy.cafe.model.dto.Product;
import com.ssafy.cafe.model.service.ProductService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/rest/product")
@CrossOrigin(allowCredentials = "true", originPatterns = { "*" })
public class ProductRestController {

	@Autowired
	ProductService pService;

	@GetMapping()
	@ApiOperation(value = "전체 상품의 목록을 반환한다.", response = List.class)
	public List<Product> getProductList() {
		return pService.getProductList();
	}
	
	@GetMapping("/beverage")
	@ApiOperation(value = "음료 상품의 목록을 반환한다.", response = List.class)
	public List<Product> getBeverage() {
		return pService.getBeverage();
	}
	
	@GetMapping("/dessert")
	@ApiOperation(value = "디저트 상품의 목록을 반환한다.", response = List.class)
	public List<Product> getDessert() {
		return pService.getDessert();
	}
	
	@GetMapping("/searchProduct")
	@ApiOperation(value = "원하는 상품 검색", response = List.class)
	public List<Product> searchProduct(String productName) {
		return pService.searchProduct(productName);
	}

	@GetMapping("/{productId}")
	@ApiOperation(value = "{productId}에 해당하는 상품의 정보를 comment와 함께 반환한다."
			+ "이 기능은 상품의 comment를 조회할 때 사용된다.", response = List.class)
	public List<Map<String, Object>> getProductWithComments(@PathVariable Integer productId) {
		return pService.selectWithComment(productId);
	}
	
	@GetMapping("/recommendBySell")
	@ApiOperation(value = "판매량 순위로 추천한다.", response = List.class)
	public List<Map<String, Object>> recommendBySell() {
		return pService.recommendBySell();
	}
	
	@GetMapping("/recommendByRating")
	@ApiOperation(value = "별점 순위로 추천한다.", response = List.class)
	public List<Map<String, Object>> recommendByRating() {
		return pService.recommendByRating();
	}
	
	@GetMapping("/salesAll")
	@ApiOperation(value = "총 매출을 반환한다.", response = List.class)
	public List<Product> getSalesAll() {
		return pService.getSalesAll();
	}
	
	@GetMapping("/salesOne")
	@ApiOperation(value = "상품별 매출을 반환한다.", response = List.class)
	public List<Product> getSalesOne() {
		return pService.getSalesOne();
	}
}