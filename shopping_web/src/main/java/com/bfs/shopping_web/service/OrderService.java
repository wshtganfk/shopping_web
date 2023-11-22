package com.bfs.shopping_web.service;

import com.bfs.shopping_web.dao.OrderDao;
import com.bfs.shopping_web.domain.Order;
import com.bfs.shopping_web.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;

    @Transactional
    public Order getProductById(long id) {
        return orderDao.getOrderById(id);
    }

    @Transactional
    public List<Order> addOrder(Order... orders) {
        for (Order order : orders) {
            orderDao.addOrder(order);
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


}
