package spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.service.RoleService;
import spring.service.UserService;

/**
 * Created by yangqifan on 2017/7/20.
 */
@Transactional
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private UserService userService;
    @Override
    public String getRole(String userId) {
        return "admin";
    }
}
