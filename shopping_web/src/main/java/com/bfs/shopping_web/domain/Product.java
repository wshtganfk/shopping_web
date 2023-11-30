package com.bfs.shopping_web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long product_id;
    @Column
    private String description;
    @Column
    private String name;
    @Column
    private int quantity;
    @Column
    private double retail_price;
    @Column
    private double wholesale_price;
//    @OneToMany(mappedBy = "product")
//    private List<Order_item> order_items;
//    @OneToMany(mappedBy = "product")
//    private List<Watchlist> watchlists;
}
