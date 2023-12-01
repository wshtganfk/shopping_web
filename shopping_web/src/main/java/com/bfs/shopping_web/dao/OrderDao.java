package com.bfs.shopping_web.dao;

import com.bfs.shopping_web.domain.Order;
import com.bfs.shopping_web.domain.Product;
import com.bfs.shopping_web.domain.User;
import com.bfs.shopping_web.exception.GlobalException;
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
    public Order getOrderById(long id) throws GlobalException {
        return this.findById(id);
    }
    public List<Order> getAllOrder()throws GlobalException  {
        return this.getAll();
    }
    public void addOrder(Order order)throws GlobalException  {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!in Dao");
        this.add(order);
    }
    public void updateOrder(Order order)throws GlobalException { this.update(order);}
    public List<Order> getOrdersByUserId(long id)throws GlobalException  {
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
