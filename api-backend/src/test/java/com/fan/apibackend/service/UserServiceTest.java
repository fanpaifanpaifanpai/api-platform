package com.fan.apibackend.service;


import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void userRegisterTest(){
        String userName = "invoker";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        userService.userRegister(userName,userPassword,checkPassword);
    }
}