package com.imooc.controller;


import com.imooc.dao.repository.AuthorityRepository;
import com.imooc.dao.repository.WebUserRepository;
import com.imooc.entity.WebAuthority;
import com.imooc.entity.WebUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;


@RestController
@RequestMapping("/register")
@Data
@AllArgsConstructor
public class WebUserController {
    @Autowired
    private WebUserRepository webUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorityRepository authorityRepository;


    @GetMapping
    public String registerForm() {

        return "registration";
    }

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String processRegistration(@RequestBody WebUser webUser) {
        System.out.println("register-----------------ok");
        WebUser newWebUser = new WebUser(webUser.getUsername(), passwordEncoder.encode(webUser.getPassword()));
        newWebUser.getAuthorities().add(new WebAuthority("ROLE_USER"));
        //?? persist authority first ??
        authorityRepository.saveAll(newWebUser.getAuthorities());
        webUserRepository.save(newWebUser);
        return "Registration Succeed";
    }
}
