package com.bfs.shopping_web.dao;

import com.bfs.shopping_web.domain.Permission;
import com.bfs.shopping_web.domain.Product;
import com.bfs.shopping_web.exception.GlobalException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PermissionDao extends AbstractHibernateDao{
    public PermissionDao() {
        setClazz(Permission.class);
    }

    public List<Permission> getAllPermissions() {
        return this.getAll();
    }
}
