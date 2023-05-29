package com.arunava.demo.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "roles")
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;
    
    private String name;
    
    
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_privilege",
            joinColumns = @JoinColumn(name = "role_id",referencedColumnName = "rid"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id",referencedColumnName = "pid")
    )
    private List<Privilege> privileges;

    public Role(String name) {
    }

    // Constructors, getters, and setters
}