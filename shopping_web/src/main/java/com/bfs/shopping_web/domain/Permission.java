package com.bfs.shopping_web.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="permission")
@Data
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long permission_id;
    @Column
    private String value;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
