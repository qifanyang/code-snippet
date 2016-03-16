package test.spring.proxy;

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
        ProxyFactory factory = new ProxyFactory(new SimplePojo());
        factory.addAdvice(new RetryAdvice());//advisor包装advice,MethodInterceptor也是一个advice
        Pojo pojo = (Pojo) factory.getProxy();
// this is a method call on the proxy!
        pojo.pojoName();
    }
}
