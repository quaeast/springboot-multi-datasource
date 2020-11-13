package com.imooc.controller;


import com.imooc.config.security.SecurityConfig;
import com.imooc.dao.repository.WebUserRepository;
import com.imooc.entity.WebUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/register")
@Data
@AllArgsConstructor
public class WebUserController {
    @Autowired
    private WebUserRepository webUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String processRegistration(@RequestBody WebUser webUser) {
        System.out.println("register-----------------ok");
        webUserRepository.save(new WebUser(webUser.getUsername(), passwordEncoder.encode(webUser.getPassword())));
        return "Registration Succeed";
    }
}
