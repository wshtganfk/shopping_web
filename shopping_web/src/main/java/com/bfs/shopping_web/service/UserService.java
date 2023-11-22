package com.bfs.shopping_web.service;

import com.bfs.shopping_web.dao.UserDao;
import com.bfs.shopping_web.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    @Transactional
    public User addUser(User user){
        if(userDao.getUserByEmail(user.getEmail()) != null
                || userDao.getUserByUsername(user.getUsername()) != null) return null;
        else return userDao.addUser(user);
    }
    @Transactional
    public List<User> getAllUsers(){
        return userDao.getAllUser();
    }
}
