package spring.valueof;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author yangqf
 * @version 1.0 2016/12/13
 */
@Component
public class FruitBeanPostProcessor implements BeanPostProcessor{
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException{
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException{
        if(bean instanceof Fruit){
            Fruit fruit = (Fruit) bean;
            //直接访问集合这种方式比较危险
            Fruit.subclassInstanceMap.put(fruit.code(), fruit);
        }
        return bean;
    }
}
