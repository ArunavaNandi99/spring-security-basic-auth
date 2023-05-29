package com.arunava.demo.dao;

import com.arunava.demo.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeDao extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
}
