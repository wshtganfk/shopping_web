package com.bfs.shopping_web.dao;

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
public class ProductDao extends AbstractHibernateDao<Product> {

    public ProductDao() {
        setClazz(Product.class);
    }

    public Product getProductById(long id){
        return this.findById(id);
    }

    public List<Product> getAllProducts()throws GlobalException  {
        return this.getAll();
    }

    public void addProduct(Product product)throws GlobalException  {
        this.add(product);
    }
    public void updateProduct(Product product)throws GlobalException { this.update(product);}

//    public List<Product> getMostFrequentlyPurchasedProduct(){
//        Session session = getCurrentSession();
//        session.beginTransaction();
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<Product> cr = cb.createQuery(Product.class);
//        Root<Product> root = cr.from(Product.class);
//        cr.select(root).where(cb.equal(root.get(""), email));
//        Query<Product> query = session.createQuery(cr);
//        query.setMaxResults(3);
//        List<Product> result = query.getResultList();
//        session.close();
//        return result;
//    }

}
