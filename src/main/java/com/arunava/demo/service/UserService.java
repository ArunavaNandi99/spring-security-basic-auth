package com.arunava.demo.service;

import com.arunava.demo.model.User;

public interface UserService {
    User adduser(User user) throws Exception;

    String login(User user);
}
