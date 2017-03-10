package spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/1/29
 */
public class SpringBeanTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean-test.xml");

        //实际使用,不要调用getBean(),这样就不会对spring api有依赖,但是除非使用xml定义bean
        //不然使用注解@Autowired还是有依赖,导出一个带有@Autowired的类的jar包,但是如果在新的环境
        // classpath中没有@Autowired,加载类会经测试时可以使用的
//        User user = applicationContext.getBean(User.class);//user 使用了lookup-method,所以没法查找

        //虽然是原型作用域,单例创建对象时只会向容器请求一次,所以返回的是同一个对象
        //为了让user.getPb()每次返回新对象,lookup-method , 在user中定义抽象方法
        //spring会使用cglib来生成子类并重写抽象方法,这些都是自动的
//        System.out.println(user.getPb());
//        System.out.println(user.getPb());
//        System.out.println(user.getClass());

        //SimpleBean 通过动态代理创建
        SimpleBean bean = applicationContext.getBean(SimpleBean.class);
//        bean.test();

        //注入,
        // 1.setter注入,没有set方法无法完成
//        SimpleBean bb = applicationContext.getBean(SimpleBean.class);
//        System.out.println(bb.getForSetter());
    }


}
