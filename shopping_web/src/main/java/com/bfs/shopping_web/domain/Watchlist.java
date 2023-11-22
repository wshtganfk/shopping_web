package com.bfs.shopping_web.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Table(name="watchlist")
@Data
public class Watchlist {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
