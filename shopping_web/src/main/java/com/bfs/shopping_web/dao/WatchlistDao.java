package com.bfs.shopping_web.dao;

import com.bfs.shopping_web.domain.*;
import com.bfs.shopping_web.domain.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;
@Repository
public class WatchlistDao extends AbstractHibernateDao{
    public WatchlistDao() {
        setClazz(Watchlist.class);
    }

    public List<Watchlist> getAll(){
        return this.getAll();
    }

    public List<Product> getUserWatchList(long user_id){
        Session session = getCurrentSession();
//        session.beginTransaction();
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
    public List<Product> addUserWatchList(User user, Product product){
//        Session session = getCurrentSession();
////        Transaction transaction = session.getTransaction();
////        session.beginTransaction();
//        try {
//            CriteriaBuilder cb = session.getCriteriaBuilder();
//            CriteriaQuery<Watchlist> cr = cb.createQuery(Watchlist.class);
//            Root<Watchlist> watchlistRoot = cr.from(Watchlist.class);
//            Predicate condition = cb.and(
//                    cb.equal(watchlistRoot.get("user").get("user_id"), user_id),
//                    cb.equal(watchlistRoot.get("product").get("product_id"), product_id)
//            );
//            cr.select(watchlistRoot).where(condition);
//            if (session.createQuery(cr).getResultList().isEmpty()) {
//                User user = session.find(User.class, user_id);
//                Product product = session.find(Product.class, product_id);
//                Watchlist newWatchlist = new Watchlist();
//                newWatchlist.setUser(user);
//                newWatchlist.setProduct(product);
//                session.persist(newWatchlist);
//            }
//
////            transaction.commit();
//            return true;
//        }catch (Exception e) {
//
//            e.printStackTrace();
//            return false;
//        }

        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        watchlist.setProduct(product);
        this.add(watchlist);
        return getUserWatchList(user.getUser_id());

    }

    public List<Product> deleteUserWatchList(long product_id, User user){
        Session session = getCurrentSession();
//        Transaction transaction = session.getTransaction();
//        session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaDelete<Watchlist> delete = cb.createCriteriaDelete(Watchlist.class);
            Root watchlistRoot = delete.from(Watchlist.class);

            Predicate condition = cb.and(
                    cb.equal(watchlistRoot.get("user").get("user_id"), user.getUser_id()),
                    cb.equal(watchlistRoot.get("product").get("product_id"), product_id)
            );
            delete.where(condition);
            session.createQuery(delete).executeUpdate();
            return getUserWatchList(user.getUser_id());


//        this.deleteById(watchlist_id);
//        return getUserWatchList(user.getUser_id());

    }
}
