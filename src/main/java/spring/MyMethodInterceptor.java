package spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/2/17
 */
public class MyMethodInterceptor implements MethodInterceptor {//方法拦截器也是一个Advice
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("call mymethodinterceptor vvvv...");
        return invocation.proceed();
    }
}
