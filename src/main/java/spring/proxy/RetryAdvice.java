package spring.proxy;

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
        //MethodInvocation ---> ReflectiveMethodInvocation
        System.out.println("call methodInterceptor");
        //这里可以决定是否继续调用拦截器链,不调用的话这里的返回值成为最终返回值
        invocation.proceed();//继续嵌套调用,并不是遍历调用, 中间任意一个MethodInterceptor可以决定是否继续调用
        return null;
    }
}
