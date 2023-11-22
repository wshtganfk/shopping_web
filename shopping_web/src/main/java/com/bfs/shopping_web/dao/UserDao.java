package com.bfs.shopping_web.dao;

import com.bfs.shopping_web.domain.Product;
import com.bfs.shopping_web.domain.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserDao extends AbstractHibernateDao{
    public UserDao() {
        setClazz(User.class);
    }

    public User addUser(User user){
        add(user);
        return getUserByEmail(user.getEmail());
    }

    public List<User> getAllUser(){
        return this.getAll();
    }

    public User getUserByEmail(String email){
        Session session = getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        cr.select(root).where(cb.equal(root.get("email"), email));
        Query<User> query = session.createQuery(cr);
        query.setMaxResults(1);
        List<User> result = query.getResultList();
        session.close();
        return result.get(0);
    }
    public User getUserByUsername(String username){
        Session session = getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        cr.select(root).where(cb.equal(root.get("username"), username));
        Query<User> query = session.createQuery(cr);
        query.setMaxResults(1);
        List<User> result = query.getResultList();
        session.close();
        return result.get(0);
    }


}
