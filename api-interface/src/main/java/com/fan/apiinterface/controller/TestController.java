package com.fan.apiinterface.controller;

import com.fan.apicommon.model.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 * @author 19677
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/get")
    public String getByGet(String param){
        return "返回:" + param;
    }

    @PostMapping("/post")
    public String getByPost(@RequestParam  String param){
        return "测试:" + param;
    }

    @PostMapping("/user")
    public String getUserByPost(@RequestBody  User user){
        String result = "user:" + user.getUserName();
        return  result;
    }

}
