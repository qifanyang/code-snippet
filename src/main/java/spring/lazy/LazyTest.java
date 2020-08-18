package spring.lazy;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

import javax.annotation.Resource;

@Import(ScopedBean.class)
@Configuration
public class LazyTest {

    @Lazy
    @Resource
    ScopedBean bean;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context;
        context = new AnnotationConfigApplicationContext(LazyTest.class);

        LazyTest bean = context.getBean(LazyTest.class);

        //如果bean上没有@Lazy注解, 则2个获取的bean是一个实例, 加了@Lazy注解后, 则2次获取的是2个实例
        System.out.println(bean.bean);
        System.out.println(bean.bean);

        context.close();
    }
}

@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class ScopedBean {
}
