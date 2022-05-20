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
    	
        String token = "fFSPXa5wQcWN-t2AtodK5m:APA91bEuh1mTC9nYXGYuF57-BIliW6ht8Avc44imm9yAJex114y4LaPnPaQkyzVEt1lxkQ1hfg1OI-cLtr5cOaoU2MApGHpyXh0z8lB9lb0DC6I0b2GZBQ7or-r20BqJhav_SYJO6185";
//        String token1 = "cHI9bL4QQTKQI_nJVtoUt7:APA91bF-VKnKY0NxeikBXORc0FhmS3iRHFYZgH5yBF34GNt-tYaUCvABVal1CSibhAEWkj9DDcmpZWlD4lA481JoC_UjVnykSYhyd5OHi3-91E-kVlX4YjEEK2v0fTQlqoW35yjNnlrd";
//		한건 메시지
//        service.sendMessageTo(token, "from 사무국", "싸피 여러분 화이팅!!");
        
//		멀티 메시지        
        service.addToken(token);
//        service.addToken(token1);
        
        service.broadCastMessage("from 사무국1", "싸피 여러분 화이팅!!!!!!!!");
    }
}
