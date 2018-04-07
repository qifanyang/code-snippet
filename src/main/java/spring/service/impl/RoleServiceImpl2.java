package spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.service.RoleService;

/**
 * Created by yangqifan on 2017/7/20.
 */
@Service
public class RoleServiceImpl2 implements RoleService {
    @Autowired
    private RoleService roleService;
    @Override
    public String getRole(String userId) {
        return null;
    }
}
