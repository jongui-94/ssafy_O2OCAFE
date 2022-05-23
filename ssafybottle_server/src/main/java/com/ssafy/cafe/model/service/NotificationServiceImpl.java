package com.ssafy.cafe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cafe.model.dao.NotificationDao;
import com.ssafy.cafe.model.dto.Notification;


@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	NotificationDao nDao;
	
	@Override
	@Transactional
	public List<Notification> showNoti(String userId) {
		return nDao.showNoti(userId);
	}

	@Override
	public int deleteAll(String userId) {
		return nDao.deleteAll(userId);
	}

	@Override
	public int delete(Integer notificationId) {
		return nDao.delete(notificationId);
	}

}
