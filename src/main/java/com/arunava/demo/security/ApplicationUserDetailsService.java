package com.arunava.demo.security;

import com.arunava.demo.dao.UserDao;
import com.arunava.demo.model.Privilege;
import com.arunava.demo.model.Role;
import com.arunava.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class ApplicationUserDetailsService implements UserDetailsService {
    @Autowired
    UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userDao.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not exists");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),true,true,true,true, getAuthority(user.getRoles()));
}

    private Collection<? extends GrantedAuthority> getAuthority(List<Role> roles) {
        return getGrantAuthorities(getPrivileges(roles));
    }

    private List<GrantedAuthority> getGrantAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges){
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;

    }

    private List<String> getPrivileges(List<Role> roles) {
        List<String> privileges =  new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();

        for(Role role : roles){
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection){
            privileges.add(item.getName());
        }
        return privileges;
    }
}
