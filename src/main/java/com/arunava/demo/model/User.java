package com.arunava.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    
    private String username;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName ="uid" ),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName ="rid" )
    )
    private List<Role> roles;

    private  String password;

}
