package com.bfs.shopping_web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long user_id;

    @Column(nullable = false)
    private String email;
    @Column
    private String password;
    @Column
    private String username;
    @Column
    private int role;
//    @JsonIgnore
//    @JsonIgnoreProperties( value = { "user" })
//    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
//    private List<Permission> permissions;
//    @OneToMany(mappedBy = "user")
//    private List<Order> orders;
//    @OneToMany(mappedBy = "user")
//    private List<Watchlist> watchlists;

}
