package spring.jdbc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

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

//        MultipleThreadCAS bean = applicationContext.getBean(MultipleThreadCAS.class);
//        bean.test();

        UserService service = applicationContext.getBean(UserService.class);

        //case 1
        //数据库连接事务不是自动提交,也不使用事务注解,那么改变后查询值一样,因为spring jdbc不会提交事务,
        //而且每条语句都是新获取一个数据库连接,所以无法查询到改变后的值
//        service.incrementAge(1);

        //case 2
        //在service上使用事务注解
        //使用事务注解后,两次使用的是一个数据库连接,第二次查询可以看见第一次的修改
//        service.incrementAge(1);

        //case 3
        //从UserService带有事务注解的方法去调用另外一个没有事务注解的service方法
        //因为是同一线程,同一数据源,所以会使用同一个数据库连接,另外一个没有事务注解的service加入到当前事务中执行
        service.incrementAge(1);
    }
}
