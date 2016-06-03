package test.spring.extend.customtag;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * xml扩展demo
 * @author yangqf
 * @version 1.0 2016/6/3
 */
public class TestSpringExtend{
    public static void main(String[] args){
        ClassPathXmlApplicationContext ap = new ClassPathXmlApplicationContext("spring-xml-extend.xml");
        XfBean bean = ap.getBean(XfBean.class);
        System.out.println("age = " + bean.getAge());
        System.out.println("name = " + bean.getName());
    }
}
