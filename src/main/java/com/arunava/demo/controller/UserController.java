package com.arunava.demo.controller;

import com.arunava.demo.model.User;
import com.arunava.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public User addUser(@RequestBody User user) throws Exception {
        return userService.adduser(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody User user){
        return  userService.login(user);
    }

    @GetMapping("/dashboard")
    public String adminDashboard(){
        return "admin dashboard";
    }
    @GetMapping("/user")
    public String userDashboard(){
        return "userDashboard";
    }
    @GetMapping("/test")
    @PreAuthorize("hasAuthority('WRITE')")
    public String test(){
        return "test successful";
    }

    @GetMapping("/success")
    @PreAuthorize("hasAuthority('READ')")
    public String Success(){
        return  "Success";
    }

    //TODO:: 1. add exception handling , 2. UI 3. structure code base



}
