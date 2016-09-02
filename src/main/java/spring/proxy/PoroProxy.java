package spring.proxy;

import org.springframework.aop.framework.ProxyFactory;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/2/4
 */
public class PoroProxy {
    //spring aop实现
    public static void main(String[] args) {
        //提供可编程代理,为自动化代理提供基础, 同编程式事务与声明式事务
        ProxyFactory factory = new ProxyFactory(new SimplePojo());
        factory.addAdvice(new RetryAdvice());//advisor包装advice,MethodInterceptor也是一个advice, 使用PointCut.TRUE
        ////这里会创建对应的动态代理对象
        //proxyFactory会根据被代理对象或代理设置 决定使用具体的代理实现AopProxy, 主要是jdkDynamicAopProxy和cglibAopProxy
        Pojo pojo = (Pojo) factory.getProxy();
        // this is a method call on the proxy!
        pojo.pojoName();

        //ProxyFactory是手动创建代理,开可以使用BeanNameAutoProxyCreator(需要指定名字匹配规则和interceptor), 这是一个beanPostProcessor,
        //实例化bean的时候会自动创建对应的代理bean
    }
}
