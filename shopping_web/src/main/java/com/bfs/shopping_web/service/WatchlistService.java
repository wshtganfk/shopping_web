package com.bfs.shopping_web.service;

import com.bfs.shopping_web.dao.WatchlistDao;
import com.bfs.shopping_web.domain.Product;
import com.bfs.shopping_web.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class WatchlistService {
    @Autowired
    private WatchlistDao watchlistDao;

    @Transactional
    public List<Product> getUserWatchList(User user) {
        long user_id = user.getUser_id();
        return watchlistDao.getUserWatchList(user_id);
    }
    @Transactional
    public boolean addUserWatchList(User user, long product_id) {
        long user_id = user.getUser_id();

        return watchlistDao.addUserWatchList(user_id, product_id);
    }
    @Transactional
    public boolean deleteUserWatchList(User user, long product_id){
        long user_id = user.getUser_id();

        return watchlistDao.deleteUserWatchList(user_id, product_id);
    }
}
