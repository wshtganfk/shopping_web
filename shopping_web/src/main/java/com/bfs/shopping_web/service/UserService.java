package com.bfs.shopping_web.service;

import com.bfs.shopping_web.dao.PermissionDao;
import com.bfs.shopping_web.dao.UserDao;
import com.bfs.shopping_web.domain.Permission;
import com.bfs.shopping_web.domain.User;
import com.bfs.shopping_web.security.AuthUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserDao userDao;
    @Autowired
    PermissionDao permissionDao;
    @Transactional
    public Optional<User> addUser(User user){
        return userDao.addUser(user);
    }

    @Transactional
    public Optional<User> getUserById(long id){
        return Optional.ofNullable(userDao.getUserById(id));
    }
    @Transactional
    public List<User> getAllUsers(){
        return userDao.getAllUser();
    }
    @Transactional
    public Optional<User> getUserByUsername(String username){
        return userDao.getUserByUsername(username);
    }
    @Transactional
    public Optional<User>  getUserByEmail(String email){
        return userDao.getUserByEmail(email);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> userOptional = userDao.getUserByUsername(username);
        List<User> userList = userDao.getAllUser();

        Optional<User> userOptional = userList.stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst();
        System.out.println("in service ");

        if (!userOptional.isPresent()){
            throw new UsernameNotFoundException("Username does not exist");
        }

        User user = userOptional.get(); // database user

        return AuthUserDetail.builder() // spring security's userDetail
                .username(user.getUsername())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .authorities(getAuthoritiesFromUser(user))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }
    private List<GrantedAuthority> getAuthoritiesFromUser(User user){
        List<GrantedAuthority> userAuthorities = new ArrayList<>();
        List<Permission> permissions = permissionDao.getAllPermissions();

        for (Permission permission :  permissions){
            if(permission.getUser().equals(user))
            userAuthorities.add(new SimpleGrantedAuthority(permission.getValue().toString()));
        }

        return userAuthorities;
    }
}
