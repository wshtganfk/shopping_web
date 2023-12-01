package com.bfs.shopping_web.service;

import com.bfs.shopping_web.dao.ProductDao;
import com.bfs.shopping_web.dao.WatchlistDao;
import com.bfs.shopping_web.domain.Product;
import com.bfs.shopping_web.domain.User;
import com.bfs.shopping_web.domain.Watchlist;
import com.bfs.shopping_web.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class WatchlistService {
    @Autowired
    private WatchlistDao watchlistDao;
    @Autowired
    private ProductDao productDao;

    @Transactional
    public List<Product> getUserWatchList(User user) {
        long user_id = user.getUser_id();
        return watchlistDao.getUserWatchList(user_id);
    }
    @Transactional
    public List<Product> addUserWatchList(User user, long product_id) {
        Product product = productDao.getProductById(product_id);
        return watchlistDao.addUserWatchList(user, product);
    }
    @Transactional
    public List<Product> deleteUserWatchList(User user, long product_id){
//        List<Watchlist> watchlists = watchlistDao.getAll();
//        Product product = productDao.getProductById(product_id);
//        Watchlist foundWatchlist = watchlists.stream()
//                .filter(watchlist -> watchlist.getProduct().equals(product))
//                .findFirst().get();
//        System.out.println(foundWatchlist);
//        return watchlistDao.deleteUserWatchList(foundWatchlist.getWatch_list_id(), user);
        return watchlistDao.deleteUserWatchList(product_id, user);
    }
}
