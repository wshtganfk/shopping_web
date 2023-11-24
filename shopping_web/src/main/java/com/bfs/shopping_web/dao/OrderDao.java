package com.bfs.shopping_web.dao;

import com.bfs.shopping_web.domain.Order;
import com.bfs.shopping_web.domain.Product;
import com.bfs.shopping_web.domain.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class OrderDao extends AbstractHibernateDao<Order>{
    public OrderDao() {
        setClazz(Order.class);
    }
    public Order getOrderById(long id) {
        return this.findById(id);
    }
    public List<Order> getAllOrder() {
        return this.getAll();
    }
    public void addOrder(Order order) {
        this.add(order);
    }
    public void updateOrder(Order order){ this.update(order);}
    public List<Order> getOrdersByUserId(long id) {
        Session session = getCurrentSession();
//        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> cr = cb.createQuery(Order.class);
        Root<Order> root = cr.from(Order.class);
        cr.select(root).where(cb.equal(root.get("user"), id));
        Query<Order> query = session.createQuery(cr);
        List<Order> result = query.getResultList();
        return result;

    }

}
