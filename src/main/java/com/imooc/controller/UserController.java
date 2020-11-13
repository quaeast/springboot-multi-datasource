package com.imooc.controller;

import com.imooc.entity.User;
import com.imooc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/useradd")
    public User getUser(@RequestBody User user){
        userService.addOne(user);
        return user;
    }
}
