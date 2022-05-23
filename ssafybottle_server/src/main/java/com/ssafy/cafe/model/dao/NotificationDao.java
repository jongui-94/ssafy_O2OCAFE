package com.ssafy.cafe.model.dao;

import java.util.List;
import com.ssafy.cafe.model.dto.Notification;

public interface NotificationDao {
	
	List<Notification> showNoti(String userId);
	
	int insert(Notification noti);
	
	int deleteAll(String userId);
	
	int delete(int id);
}
