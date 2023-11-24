package com.bfs.shopping_web.service;

import com.bfs.shopping_web.dao.OrderDao;
import com.bfs.shopping_web.dao.Order_itemDao;
import com.bfs.shopping_web.dao.ProductDao;
import com.bfs.shopping_web.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    Order_itemDao orderItemDao;

    @Autowired
    ProductDao productDao;
    @Transactional
    public Order getProductById(long id) {
        return orderDao.getOrderById(id);
    }

    @Transactional
    public List<Order> addOrder(List<NewOrder> orders, User user) {
        java.util.Date date= new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        for (NewOrder newOrder : orders){
            Order order = new Order();
            order.setOrder_status("processing");
            order.setUser(user);
            order.setDate_placed(timestamp);
            orderDao.addOrder(order);

            Order temp = orderDao.getAllOrder().stream()
                    .filter(order1 -> user.equals(order1.getUser()) && timestamp.equals(order1.getDate_placed()))
                    .findFirst()
                    .orElse(null);
            Product product = productDao.getProductById(newOrder.getProductId());

            Order_item orderItem = new Order_item();
            orderItem.setOrder(temp);
            orderItem.setItem_id(newOrder.getProductId());
            orderItem.setQuantity(newOrder.getQuantity());
            orderItem.setWholesale_price(product.getWholesale_price());
            orderItem.setPurchased_price(product.getRetail_price());
            orderItem.setProduct(product);
            orderItemDao.add(orderItem);
        }

        return orderDao.getAllOrder();
    }
    @Transactional
    public Order updateOrder(Order order){
        orderDao.updateOrder(order);
        return orderDao.getOrderById(order.getOrder_id());
    }

    @Transactional
    public List<Order> getOrdersByUserId(long id){
        return orderDao.getOrdersByUserId(id);
    }
    @Transactional
    public Order getOrdersById(long id){
        return orderDao.getOrderById(id);
    }


}
