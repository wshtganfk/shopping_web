package com.bfs.shopping_web.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
