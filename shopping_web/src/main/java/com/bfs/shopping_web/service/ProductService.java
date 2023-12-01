package com.bfs.shopping_web.service;

import com.bfs.shopping_web.dao.OrderDao;
import com.bfs.shopping_web.dao.Order_itemDao;
import com.bfs.shopping_web.dao.ProductDao;
import com.bfs.shopping_web.domain.Order;
import com.bfs.shopping_web.domain.Order_item;
import com.bfs.shopping_web.domain.Product;
import com.bfs.shopping_web.domain.User;
import com.bfs.shopping_web.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private Order_itemDao orderItemDao;

    @Transactional
    public List<Product> getAllProducts()throws GlobalException  {
        return productDao.getAllProducts();
    }

    @Transactional
    public Product getProductById(long id)throws GlobalException  {
        return productDao.getProductById(id);
    }

    @Transactional
    public List<Product> addProduct(Product... products)throws GlobalException  {
        for (Product product : products) {
            productDao.addProduct(product);
        }
        return productDao.getAllProducts();
    }
    @Transactional
    public Product updateProduct(Product product, long id)throws GlobalException {
        product.setProduct_id(id);
        productDao.updateProduct(product);
        return productDao.getProductById(id);
    }

    @Transactional
    public List<Product> TopThreePopularProduct(int limit)throws GlobalException {
        List<Product> productList = productDao.getAllProducts();
        List<Order_item> order_items = orderItemDao.getAllOrder_items();

        Map<Product, Long> productOrderCount = order_items.stream()
                .collect(Collectors.groupingBy(Order_item::getProduct, Collectors.counting()));

        List<Product> topThreeProducts = productList.stream()
                .sorted((p1, p2) -> {
                    long count1 = productOrderCount.getOrDefault(p1, 0L);
                    long count2 = productOrderCount.getOrDefault(p2, 0L);
                    return Long.compare(count2, count1);
                })
                .limit(3)
                .collect(Collectors.toList());
        return topThreeProducts;
    }

    @Transactional
    public List<Product> getMostProfitableProduct(int limit)throws GlobalException {
        List<Product> productList = productDao.getAllProducts();
        List<Order> allOrders = orderDao.getAllOrder();
        List<Order_item> order_items = orderItemDao.getAllOrder_items();
        List<Order> completedOrders = allOrders.stream()
                .filter(order -> order.getOrder_status().equalsIgnoreCase("complete"))
                .collect(Collectors.toList());
        List<Order_item> orderItemsForCompletedOrders = order_items.stream()
                .filter(orderItem -> completedOrders.stream()
                        .anyMatch(order -> order.equals(orderItem.getOrder())))
                .collect(Collectors.toList());

        Map<Product, Double> productProfits = orderItemsForCompletedOrders.stream()
                .collect(Collectors.groupingBy(
                        Order_item::getProduct,
                        Collectors.summingDouble(orderItem -> calculateProductProfit(orderItem, productList))));

        List<Product> top3Products = productProfits.entrySet().stream()
                .sorted((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()))
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        System.out.println(top3Products);
        return top3Products;

    }
    private static double calculateProductProfit(Order_item orderItem, List<Product> products) {
        return products.stream()
                .filter(product -> orderItem.getProduct().equals(product))
                .findFirst()
                .map(product -> (product.getRetail_price() - product.getWholesale_price()))
                .orElse(0.0);
    }
    @Transactional
    public List<Product> getMostFrequentlyPurchasedProduct(User user, int limit)throws GlobalException {
        List<Order> userOrders = orderDao.getOrdersByUserId(user.getUser_id());
        List<Order_item> order_items = orderItemDao.getAllOrder_items();
        List<Order_item> orderItemsWithUser = userOrders.stream()
                .flatMap(order -> order_items.stream()
                        .filter(orderItem -> orderItem.getOrder().equals(order)))
                .collect(Collectors.toList());

        Map<Product, Long> productOrderItemCounts = orderItemsWithUser.stream()
                .collect(Collectors.groupingBy(Order_item::getProduct, Collectors.counting()));

        long maxOrderItemCount = productOrderItemCounts.values().stream()
                .max(Long::compare)
                .orElse(0L);

        List<Product> mostOrderedProducts = productOrderItemCounts.entrySet().stream()
                .filter(entry -> entry.getValue() == maxOrderItemCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

//        List<Product> userProducts = productList.stream()
//                .filter(product -> product.getOrder_items().stream()
//                        .anyMatch(orderItem -> orderItem.getOrder().getUser().getUser_id() == user.getUser_id()))
//                .collect(Collectors.toList());
//        Optional<Map.Entry<Product, Long>> productWithMostOrders = userProducts.stream()
//                .flatMap(product -> product.getOrder_items().stream())
//                .collect(Collectors.groupingBy(e -> e.getProduct(), Collectors.counting()))
//                .entrySet().stream()
//                .max(Map.Entry.comparingByValue());
        System.out.println("!!!!! maxOrderItemCount  " + mostOrderedProducts);
        if(limit > mostOrderedProducts.size()) return mostOrderedProducts;
        else return mostOrderedProducts.subList(0, limit);
    }
    @Transactional
    public List<Product> getMostRecentlyPurchasedProduct(User user, int limit)throws GlobalException {
        Optional<Order> mostRecentOrder = orderDao.getOrdersByUserId(user.getUser_id()).stream()
                .max((order1, order2) -> order1.getDate_placed().compareTo(order2.getDate_placed()));
        List<Order_item> order_items = orderItemDao.getAllOrder_items();
        List<Product> mostRecentProduct = new ArrayList<>();
        if(mostRecentOrder.isPresent()){
            mostRecentProduct = order_items.stream()
                    .filter(oi -> oi.getOrder().equals(mostRecentOrder.get()))
                    .map(Order_item::getProduct)
                    .collect(Collectors.toList());
        }
        if(limit > mostRecentProduct.size()) return mostRecentProduct;
        else return mostRecentProduct.subList(0, limit);
    }



}
