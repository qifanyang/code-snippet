package test.spring.proxy;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/2/4
 */
public class RetryAdvice implements MethodInterceptor {
    //必须要实现MethodInterceptor,动态代理的时候会生成
//    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("call methodInterceptor");
        invocation.proceed();
        return null;
    }
}
