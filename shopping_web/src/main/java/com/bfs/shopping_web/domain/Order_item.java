package com.bfs.shopping_web.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="order_item")
@Data
public class Order_item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long item_id;
    @Column
    private double purchased_price;
    @Column
    private int quantity;
    @Column
    private double wholesale_price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
