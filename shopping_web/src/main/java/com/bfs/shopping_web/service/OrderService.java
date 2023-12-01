package com.bfs.shopping_web.service;

import com.bfs.shopping_web.dao.OrderDao;
import com.bfs.shopping_web.dao.Order_itemDao;
import com.bfs.shopping_web.dao.ProductDao;
import com.bfs.shopping_web.domain.*;
import com.bfs.shopping_web.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    Order_itemDao orderItemDao;
    @Autowired
    ProductDao productDao;


    @Transactional
    public Order getOrderById(long id)throws GlobalException  {
        return orderDao.getOrderById(id);
    }

    @Transactional
    public List<Order> addOrder(List<NewOrder> orders, User user)throws GlobalException  {
        java.util.Date date= new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        for (NewOrder newOrder : orders){
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!"+newOrder);
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
            int current_quantity = product.getQuantity() - newOrder.getQuantity();
            product.setQuantity(current_quantity);
            productDao.updateProduct(product);

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
    public Order updateOrder(Order order, String status)throws GlobalException {
        System.out.println(order);

        if(status.equals("cancel")){
            List<Order_item> orderItems = orderItemDao.getAllOrder_items();
            List<Order_item> orderItemList = orderItems.stream()
                    .filter(orderItem1 -> orderItem1.getOrder().getOrder_id() ==order.getOrder_id())
                    .collect(Collectors.toList());
            for(Order_item orderItem : orderItemList){
                Product product = orderItem.getProduct();
                int quantity_after_cancel = product.getQuantity() + orderItem.getQuantity();
                product.setQuantity(quantity_after_cancel);
                productDao.updateProduct(product);
            }

        }
        orderDao.updateOrder(order);
        return orderDao.getOrderById(order.getOrder_id());

    }

    @Transactional
    public List<Order> getOrdersByUserId(long id)throws GlobalException {
        return orderDao.getOrdersByUserId(id);
    }
    @Transactional
    public List<Order> getAllOrders()throws GlobalException {
        return orderDao.getAllOrder();
    }

    public List<Product> getOrderdetailsById(long id)throws GlobalException {
        List<Product> products = new ArrayList<>();
        Order order = orderDao.getOrderById(id);
        List<Order_item> orderItems = orderItemDao.getAllOrder_items();
        List<Order_item> orderItemList = orderItems.stream()
                .filter(orderItem1 -> orderItem1.getOrder().equals(order))
                .collect(Collectors.toList());
        for(Order_item orderItem : orderItemList){
            Product product = orderItem.getProduct();
            product.setQuantity(orderItem.getQuantity());
            products.add(product);
        }
        System.out.println(orderItems);
        System.out.println(orderItemList);

        System.out.println(products);
        return products;
    }
    @Transactional
    public Order getOrdersById(long id)throws GlobalException {
        Order order = orderDao.getOrderById(id);
        return order;
    }


}
