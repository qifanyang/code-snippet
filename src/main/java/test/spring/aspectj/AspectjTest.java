package test.spring.aspectj;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import test.spring.SimpleBean;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/2/17
 */
public class AspectjTest {

    public static void main(String[] args) {
        //aspectj用法,直接在类中定义pointcut,advice
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean-aspectj.xml");
        SimpleBean bean = applicationContext.getBean(SimpleBean.class);
        bean.test();
    }
}
