package com.bfs.shopping_web.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public abstract class AbstractHibernateDao<T> {
    @Autowired
    protected SessionFactory sessionFactory;

    protected Class<T> clazz;

    protected final void setClazz(final Class<T> clazzToSet) {
        clazz = clazzToSet;
    }

    public List<T> getAll() {
        Session session = getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria).getResultList();
    }

    public T findById(long id) {
        return getCurrentSession().get(clazz, id);
    }

    public void add(T item) {
        getCurrentSession().save(item);
    }
    public T update(T entity) {
        Session session = getCurrentSession();
        Transaction transaction = session.beginTransaction();
        System.out.println(entity);
        session.merge(entity);
        transaction.commit();

        System.out.println("done");

        return entity;
    }
    public void deleteById(int entityId) {
        Session session = getCurrentSession();
        Transaction transaction = session.beginTransaction();
        final T entity = session.get(clazz, entityId);
        session.delete(entity);
        transaction.commit();


    }

    protected Session getCurrentSession() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        return session;
    }
}
