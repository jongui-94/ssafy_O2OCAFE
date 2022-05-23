package com.ssafy.cafe.model.dao;

import java.util.List;

import com.ssafy.cafe.model.dto.OrderDetail;
import com.ssafy.cafe.model.dto.OrderDetailByOrderId;

public interface OrderDetailDao {

	int insert(OrderDetail detail);

	int delete(Integer detailId);

	OrderDetail select(Integer detailId);

	List<OrderDetail> selectAll();
	
	OrderDetailByOrderId selectOrderDetail(int id);
}
