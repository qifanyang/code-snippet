package test.spring.jdbc;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import test.spring.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/2/15
 */
public class JdbcTest {

    private static <T> T singleValue(List<T> results) {
        if (results == null || results.size() == 0) {
            return null;
        } else {
            return results.iterator().next();
        }
    }
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean-jdbc.xml");

        MultipleThreadCAS bean = applicationContext.getBean(MultipleThreadCAS.class);
//        bean.test();
        AgeDao ageDao = applicationContext.getBean(AgeDao.class);

        System.out.println(singleValue(ageDao.selectIntegerTest()));
        System.out.println(ageDao.selectIntegerTest());


    }
}
