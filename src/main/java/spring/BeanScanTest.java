package spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.service.RoleService;
import spring.service.UserService;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/2/25
 */
public class BeanScanTest {
    @RequestMapping(value = "aaa", path = "cccc")
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-scan.xml");
        context.start();
//        SimpleBean bean = context.getBean(SimpleBean.class);
//        bean.test();

        UserService bean = context.getBean(UserService.class);
        System.out.println(bean.getName(null));
        RoleService rol = context.getBean(RoleService.class);
        System.out.println(rol.getRole(null));
        System.out.println();
    }
}
