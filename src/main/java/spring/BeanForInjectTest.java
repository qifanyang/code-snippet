package spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.bean.BeanForAutowiredInject;
import spring.bean.BeanForConstructorInject;
import spring.bean.BeanForSetInject;
import spring.circle.CircleA;

/**
 * 注入测试
 * @author yangqf
 * @version 1.0 2016/4/21
 */
public class BeanForInjectTest{
    public static void main(String[] args){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean-forinject.xml");

        //注入,
        // 1.setter注入,没有set方法无法完成, 因为BeanWrapperImpl 是通过Bean的WriteMethod也就是set方法来完成注入的

        BeanForSetInject bean = applicationContext.getBean(BeanForSetInject.class);
        System.out.println(bean.getName());

        //2.构造方法注入, 如果没有对应的构造方法则抛出异常
        BeanForConstructorInject bean1 = applicationContext.getBean(BeanForConstructorInject.class);
        System.out.println(bean1.getName()+bean1.getAge());

        //autowired, 默认根据类型从容器中需要对应的Bean, 需要添加<context:annotation-config/>启用注解
        //会默默的注入AutowiredAnnotationBeanPostProcessor,RequiredAnnotationBeanPostProcessor等
        BeanForAutowiredInject bean2 = applicationContext.getBean(BeanForAutowiredInject.class);
        System.out.println();

        applicationContext.getBean(CircleA.class);
        //实例化CircleA 会提前暴露ObjectFactory, 当向CircleA注入依赖CircleB时,
        //会实例化CircleB , 然后CircleB注入时又会去找CircleA, 这时会调用实例化CircleA
        //时添加的SingletonFactory.getObject(), 其中CircleA bean是还没完全完成注入,会使用BeanPostProcessor
        //对bean进行增强,返回CircleA实例后, CircleB实例化完成并返回, 然后CircleA也完成初始化

//        addSingletonFactory(beanName, new ObjectFactory<Object>() {
//            @Override
//            public Object getObject() throws BeansException {
//                return getEarlyBeanReference(beanName, mbd, bean);
//            }
//        });
        //当bean定义有factory-method的时候,获取bean时返回该方法返回的对象

        //spring依赖解析处理流程
        //1.加载spring配置数据.xml,java code , annotation
        //2.对于bean,依赖其它bean,主要是属性,构造方法参数,或者静态方法参数, 当依赖的bean被创建的时候,才能够用来注入,所以初始化
        //一个bean可能会导致初始化一系列bean, 类似java类加载
        //3.属性或者构造方法参数能够被转换属性对应的类型,当类型不匹配时会发生自动转换

        //循环依赖
        //构造方法注入可能会造成循环依赖,最好改为setter注入

        //spring
        //1.对象管理:对象创建,对象依赖管理
        //2.AOP,所有对象交给spring管理,所以可以做很多自动化工作了,bean增强,mock等
        //3.spring mvc, 所有对象交给spring管理, 处理http请求可以有统一的请求分发,消息转换,视图渲染,
        //总之有了对象管理,spring可以将很多重复工作进行抽象,加快快发效率,假如不使用spring来管理对象,那么就需要
        //应用程序开发者来维护对象之间的关系,当有很多重复工作时,也只有自己来抽象逻辑,然后封装,但这些spring都
        //帮你做了


        //循环依赖测试, circleA, circleB
    }
}
