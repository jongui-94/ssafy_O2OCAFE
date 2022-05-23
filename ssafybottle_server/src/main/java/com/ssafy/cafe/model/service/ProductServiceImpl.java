package com.ssafy.cafe.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cafe.model.dao.ProductDao;
import com.ssafy.cafe.model.dto.Product;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao pDao;

	@Override
	public List<Product> getProductList() {
		return pDao.selectAll();
	}
	
	
	@Override
	public List<Map<String, Object>> selectWithComment(Integer productId) {
		return pDao.selectWithComment(productId);
	}


	@Override
	public List<Product> getBeverage() {
		return pDao.selectBeverage();
	}
	
	@Override
	public List<Product> searchProduct(String productName) {
		return pDao.searchProduct(productName);
	}


	@Override
	public List<Product> getDessert() {
		return pDao.selectDessert();
	}
	
	@Override
	public List<Map<String, Object>> recommendByRating() {
		return pDao.recommendByRating();
	}
	
	@Override
	public List<Map<String, Object>> recommendBySell() {
		return pDao.recommendBySell();
	}
	
	@Override
	public List<Product> getSalesAll(){
		return pDao.getSalesAll();
	}
	
	@Override
	public List<Product> getSalesOne(){
		return pDao.getSalesOne();
	}

}
