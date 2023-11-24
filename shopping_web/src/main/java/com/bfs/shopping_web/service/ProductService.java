package com.bfs.shopping_web.service;

import com.bfs.shopping_web.dao.OrderDao;
import com.bfs.shopping_web.dao.ProductDao;
import com.bfs.shopping_web.domain.Order;
import com.bfs.shopping_web.domain.Order_item;
import com.bfs.shopping_web.domain.Product;
import com.bfs.shopping_web.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private OrderDao orderDao;

    @Transactional
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Transactional
    public Product getProductById(long id) {
        return productDao.getProductById(id);
    }

    @Transactional
    public List<Product> addProduct(Product... products) {
        for (Product product : products) {
            productDao.addProduct(product);
        }
        return productDao.getAllProducts();
    }
    @Transactional
    public Product updateProduct(Product product){
        productDao.updateProduct(product);
        return productDao.getProductById(product.getProduct_id());
    }

    @Transactional
    public List<Product> TopThreePopularProduct(int limit){
        List<Product> productList = productDao.getAllProducts();
        Map<Product, Long> productOrderCount = productList.stream()
                .flatMap(product -> product.getOrder_items().stream())
                .collect(Collectors.groupingBy(e -> e.getProduct(), Collectors.counting()));

        List<Product> topThreeProducts = productOrderCount.entrySet().stream()
                .sorted(Map.Entry.<Product, Long>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return topThreeProducts;
    }

    public Product getMostFrequentlyPurchasedProduct(User user){
        List<Product> productList = productDao.getAllProducts();
        List<Product> userProducts = productList.stream()
                .filter(product -> product.getOrder_items().stream()
                        .anyMatch(orderItem -> orderItem.getOrder().getUser().getUser_id() == user.getUser_id()))
                .collect(Collectors.toList());
        Optional<Map.Entry<Product, Long>> productWithMostOrders = userProducts.stream()
                .flatMap(product -> product.getOrder_items().stream())
                .collect(Collectors.groupingBy(e -> e.getProduct(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue());

        if (productWithMostOrders.isPresent()) {
            return productWithMostOrders.get().getKey();
        } else {
            return null;
        }
    }

    public List<Product> getMostRecentlyPurchasedProduct(User user){

        Optional<Order> mostRecentOrder = orderDao.getOrdersByUserId(user.getUser_id()).stream()
                .max((order1, order2) -> order1.getDate_placed().compareTo(order2.getDate_placed()));
        if(mostRecentOrder.isPresent()){
            List<Order_item> orderItem = mostRecentOrder.get().getOrder_items();
            List<Product> mostRecentProduct = orderItem.stream()
                    .map(Order_item::getProduct)
                    .collect(Collectors.toList());
            return mostRecentProduct;
        }else return null;
    }
}
