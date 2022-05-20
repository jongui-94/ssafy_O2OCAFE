package com.ssafy.cafe.service;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.cafe.firebase.FirebaseCloudMessageService;

@SpringBootTest
class MobileFirebaseServerApplicationTests {

    @Autowired
    FirebaseCloudMessageService service;

    @Test
    void contextLoads() throws IOException {
    	
        String token = "dgDjjZSwSeCM6prk-qjM6A:APA91bEC33YPI6CR_N-2eO4qqxjLoA6QI5g7Y6dtq6YlLfqpyYIdUtm0iq7cRBDICLW58AEU4xSvK_yP_0r6vZySbhDRIOghIeKSexiuW73lrbu3lpj7fa4uX91_Ie24Da-3s4QjH0hK";
//        String token1 = "cHI9bL4QQTKQI_nJVtoUt7:APA91bF-VKnKY0NxeikBXORc0FhmS3iRHFYZgH5yBF34GNt-tYaUCvABVal1CSibhAEWkj9DDcmpZWlD4lA481JoC_UjVnykSYhyd5OHi3-91E-kVlX4YjEEK2v0fTQlqoW35yjNnlrd";
//		한건 메시지
//        service.sendMessageTo(token, "from 사무국", "싸피 여러분 화이팅!!");
        
//		멀티 메시지        
        service.addToken(token);
//        service.addToken(token1);
        
        service.broadCastMessage("from 사무국1", "싸피 여러분 화이팅!!!!!!!!");
    }
}
