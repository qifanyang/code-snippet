package spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.service.RoleService;
import spring.service.UserService;

/**
 * Created by yangqifan on 2017/7/20.
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier("roleServiceImpl")
    private RoleService roleService;


    @Override
    public String getName(Long userId) {
        System.out.println(roleService.getRole(null));
        return "hello";
    }
}
