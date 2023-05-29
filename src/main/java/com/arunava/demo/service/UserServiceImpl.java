package com.arunava.demo.service;

import com.arunava.demo.dao.RoleDao;
import com.arunava.demo.dao.UserDao;
import com.arunava.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

import java.util.Arrays;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public User adduser(User user) throws Exception {

        if(userDao.findByUsername(user.getUsername()) != null){
           throw new Exception("username already exists" + user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(roleDao.findByName("ROLE_USER")));
        return userDao.save(user);
    }

    @Override
    public String login(User user) {
        Authentication authentication;
        try{
            authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception e){
            throw new UsernameNotFoundException("Invalid user name and password");
        }

        return "login successful";
    }
}
