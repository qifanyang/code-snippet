package spring.bean;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yangqf
 * @version 1.0 2016/4/21
 */
public class BeanForAutowiredInject{
    @Autowired
    private BeanForSetInject beanForSetInject;
}
