package com.ssafy.cafe.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cafe.firebase.FirebaseCloudMessageService;
import com.ssafy.cafe.model.dao.NotificationDao;
import com.ssafy.cafe.model.dao.OrderDao;
import com.ssafy.cafe.model.dao.OrderDetailDao;
import com.ssafy.cafe.model.dao.ProductDao;
import com.ssafy.cafe.model.dao.StampDao;
import com.ssafy.cafe.model.dao.UserDao;
import com.ssafy.cafe.model.dto.Notification;
import com.ssafy.cafe.model.dto.Order;
import com.ssafy.cafe.model.dto.OrderDetail;
import com.ssafy.cafe.model.dto.Product;
import com.ssafy.cafe.model.dto.Stamp;
import com.ssafy.cafe.model.dto.User;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDao oDao;

	@Autowired
	OrderDetailDao dDao;

	@Autowired
	StampDao sDao;

	@Autowired
	UserDao uDao;
	
	@Autowired
	ProductDao pDao;
	
	@Autowired
	NotificationDao nDao;
	
	@Autowired
	private FirebaseCloudMessageService fService;

	@Override
	@Transactional
	public void makeOrder(Order order) {
		
		// 주문 및 주문 상세 테이블 저장
		oDao.insert(order);
		List<OrderDetail> details = order.getDetails();
		int quantitySum = 0;
		for(OrderDetail detail: details) {
			detail.setOrderId(order.getId());
			dDao.insert(detail);
			quantitySum += detail.getQuantity();
		}
		
		// 스템프 정보 저장
		Stamp stamp = Stamp.builder().userId(order.getUserId()).quantity(quantitySum).orderId(order.getId()).build();
		sDao.insert(stamp);
		// 사용자 정보 업데이트
		User user = User.builder().id(order.getUserId()).stamps(stamp.getQuantity()).build();
		uDao.updateStamp(user);
		
				
		// 사용자, 관리자에게 푸시 알림
		
		Product product = pDao.select(details.get(0).getProductId());
		User user2 = uDao.select(order.getUserId());
		User admin = uDao.select("admin");
		System.out.println("FCM Token: " + user2.getFtoken());
		
		try {
			fService.sendMessageTo(user2.getFtoken(), "주문완료", user2.getName() + "님의 주문 " + product.getName() + "포함 " + quantitySum + "잔이 완료되었습니다.");
			fService.sendMessageTo(admin.getFtoken(), "주문접수", user2.getName() + "님의 주문 " + product.getName() + "포함 " + quantitySum + "잔이 접수되었습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 알림 테이블에 저장
		Notification noti1 = new Notification(user2.getId(),  "주문완료", user2.getName() + "님의 주문 " + product.getName() + "포함 " + quantitySum + "잔이 완료되었습니다.");
		Notification noti2 = new Notification("admin", "주문접수", user2.getName() + "님의 주문 " + product.getName() + "포함 " + quantitySum + "잔이 접수되었습니다.");
		nDao.insert(noti1);
		nDao.insert(noti2);
	}

	@Override
	public Order getOrderWithDetails(Integer orderId) {
		return oDao.selectWithDetail(orderId);
	}

	@Override
	public List<Order> getOrdreByUser(String id) {
		return oDao.selectByUser(id);
	}

	@Override
	public void updateOrder(Order order) {
		oDao.update(order);
	}

	@Override
	public List<Map> selectOrderTotalInfo(int id) {
		return oDao.selectOrderTotalInfo(id);
	}

	@Override
	public List<Map<String, Object>> getLastMonthOrder(String id) {
		return oDao.getLastMonthOrder(id);
	}
	
	@Override
	public List<Order> getOrderList() {
		return oDao.getOrderList();
	}

}
