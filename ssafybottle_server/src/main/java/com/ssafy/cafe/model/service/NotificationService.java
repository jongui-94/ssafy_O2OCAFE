package com.ssafy.cafe.model.service;

import com.ssafy.cafe.model.dto.Notification;
import java.util.List;

public interface NotificationService {
	
	public List<Notification> showNoti(String userId);
	
	public int deleteAll(String userId);
	
	public int delete(Integer notificationId);
}
