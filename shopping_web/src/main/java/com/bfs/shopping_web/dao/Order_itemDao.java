package com.bfs.shopping_web.dao;

import com.bfs.shopping_web.domain.Order_item;
import com.bfs.shopping_web.domain.Product;
import com.bfs.shopping_web.exception.GlobalException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Order_itemDao extends AbstractHibernateDao{
    public Order_itemDao() {
        setClazz(Order_item.class);
    }

    public void addOrder_item(Order_item orderItem)throws GlobalException {
        add(orderItem);
    }
    public List<Order_item> getAllOrder_items()throws GlobalException  {
        return this.getAll();
    }
}
