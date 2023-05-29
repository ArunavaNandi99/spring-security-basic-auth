package com.arunava.demo.datastore;

import com.arunava.demo.dao.PrivilegeDao;
import com.arunava.demo.dao.RoleDao;
import com.arunava.demo.dao.UserDao;
import com.arunava.demo.model.Privilege;
import com.arunava.demo.model.Role;
import com.arunava.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataStore implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup  = false;

    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    PrivilegeDao privilegeDao;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup){
            return;
        }

        Privilege readP = createPrivilege("READ");
        Privilege writeP = createPrivilege("WRITE");

        List<Privilege> adminP = new ArrayList<>(Arrays.asList(readP,writeP));
        List<Privilege> userp = new ArrayList<>(Arrays.asList(readP));

        Role adminRole = createRole("ROLE_ADMIN",adminP);
        createRole("ROLE_USER",userp);

        createUser("arunava",passwordEncoder.encode("arunava"),new ArrayList<>(Arrays.asList(adminRole)));

        alreadySetup = true;
    }

//    @Transactional
    private User createUser(String username, String password, ArrayList<Role> roles) {
        User user = userDao.findByUsername(username);
        if(user == null){
            user = new User();
            user.setUsername(username);
            user.setPassword(password);
        }
        user.setRoles(roles);
        user =  userDao.save(user);
        return user;
    }

    @Transactional
    private Role createRole(String name, List<Privilege> privileges) {
        Role role = roleDao.findByName(name);
        if(role == null){
            role = new Role();
            role.setName(name);
        }
        role.setPrivileges(privileges);
        role = roleDao.save(role);
        return role;
    }

    @Transactional
    private Privilege createPrivilege(String name) {
        Privilege privilege = privilegeDao.findByName(name);
        if(privilege == null){
            privilege = new Privilege();
            privilege.setName(name);
            privilege = privilegeDao.save(privilege);
        }
        return privilege;
    }


}
