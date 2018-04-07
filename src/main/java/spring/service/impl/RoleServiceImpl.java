package spring.service.impl;

import custom.annotation.Customizable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.service.RoleService;
import spring.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by yangqifan on 2017/7/20.
 */
@Transactional
@Service
@Customizable
public class RoleServiceImpl implements RoleService {
    @Autowired
    private UserService userService;
    @Autowired
    private List<RoleService> roleServiceList;
    @Autowired
    private RoleService roleService;

    @PostConstruct
    public void init(){
        System.out.println();
    }
    @Override
    public String getRole(String userId) {
        return "admin";
    }
}
