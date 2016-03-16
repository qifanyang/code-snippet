package test.spring.bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import test.spring.SimpleBean;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/2/23
 */
public class RegistryBeanTest {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-register.xml");

//        Object r = context.getBean("r");
//        System.out.println(r);
//         r = context.getBean("r");
//        System.out.println(r);

        SimpleBean bean = context.getBean(SimpleBean.class);
        bean.test();
    }
}
