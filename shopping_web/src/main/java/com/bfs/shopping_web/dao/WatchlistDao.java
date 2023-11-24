package com.bfs.shopping_web.dao;

import com.bfs.shopping_web.domain.Order;
import com.bfs.shopping_web.domain.Product;
import com.bfs.shopping_web.domain.User;
import com.bfs.shopping_web.domain.Watchlist;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;
@Repository
public class WatchlistDao extends AbstractHibernateDao{

    public List<Product> getUserWatchList(long user_id){
        Session session = getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Product> cr = cb.createQuery(Product.class);
        Root<Watchlist> watchlistRoot = cr.from(Watchlist.class);
        Join<Watchlist, Product> productJoin = watchlistRoot.join("product");
        cr.select(productJoin);
        cr.where(cb.equal(watchlistRoot.get("user").get("user_id"), user_id));
        Query<Product> query = session.createQuery(cr);
        List<Product> result = query.getResultList();
        return result;

    }
    public boolean addUserWatchList(long user_id, long product_id){
        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Watchlist> cr = cb.createQuery(Watchlist.class);
            Root<Watchlist> watchlistRoot = cr.from(Watchlist.class);
            Predicate condition = cb.and(
                    cb.equal(watchlistRoot.get("user").get("user_id"), user_id),
                    cb.equal(watchlistRoot.get("product").get("product_id"), product_id)
            );
            cr.select(watchlistRoot).where(condition);
            if (session.createQuery(cr).getResultList().isEmpty()) {
                User user = session.find(User.class, user_id);
                Product product = session.find(Product.class, product_id);
                Watchlist newWatchlist = new Watchlist();
                newWatchlist.setUser(user);
                newWatchlist.setProduct(product);
                session.persist(newWatchlist);
            }

            transaction.commit();
            return true;
        }catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }

    }

    public boolean deleteUserWatchList(long user_id, long product_id){
        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Watchlist> cr = cb.createQuery(Watchlist.class);
            Root<Watchlist> watchlistRoot = cr.from(Watchlist.class);
            Predicate condition = cb.and(
                    cb.equal(watchlistRoot.get("user").get("user_id"), user_id),
                    cb.equal(watchlistRoot.get("product").get("product_id"), product_id)
            );
            cr.select(watchlistRoot).where(condition);
            if (!session.createQuery(cr).getResultList().isEmpty()) {
                User user = session.find(User.class, user_id);
                Product product = session.find(Product.class, product_id);
                Watchlist newWatchlist = new Watchlist();
                newWatchlist.setUser(user);
                newWatchlist.setProduct(product);
                session.remove(newWatchlist);
            }
            transaction.commit();
            return true;
        }catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }

    }
}
