package com.ssafy.cafe.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cafe.model.dto.Notification;
import com.ssafy.cafe.model.service.NotificationService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/rest/notification")
@CrossOrigin(allowCredentials = "true", originPatterns = {"*"})
public class NotificationRestController {
	
	@Autowired
	NotificationService nService;
	
	@GetMapping("/{userId}")
	@ApiOperation(value = "{userId}에 해당하는 알림 내역을 반환한다.", response = List.class)
	public List<Notification> showNotification(@PathVariable String userId) {
		return nService.showNoti(userId);
	}
	
	@DeleteMapping("/all/{userId}")
	@Transactional
	@ApiOperation(value = "{userId}에 해당하는 전체 알림내역을 삭제한다.", response = Integer.class)
	public int deleteAllNotification(@PathVariable String userId) {
		return nService.deleteAll(userId);
	}
	
	@DeleteMapping("/{notificationId}")
	@Transactional
	@ApiOperation(value = "{notificationId}에 해당하는 알림내역을 삭제한다.", response = Integer.class)
	public int deleteNotification(@PathVariable Integer notificationId) {
		return nService.delete(notificationId);
	}
}
