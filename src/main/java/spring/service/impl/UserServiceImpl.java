package spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.User;
import spring.service.RoleService;
import spring.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by yangqifan on 2017/7/20.
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
//    @Qualifier("roleServiceImpl")
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @PostConstruct
    private void init(){
        System.out.println();
    }

    @Override
    public String getName(Long userId) {
        System.out.println(roleService.getRole(null));
        return "hello";
    }
}
