package test.core.proxy.cglib;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/1/24
 */

import net.sf.cglib.proxy.Enhancer;


import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {
    private Object target;

    public Object getProxyInstance(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);  // call back method
        return enhancer.create();  // create proxy instance
    }

    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("before target method...");
        Object result = proxy.invokeSuper(target, args);
        System.out.println("after target method...");
        return result;
    }
}
