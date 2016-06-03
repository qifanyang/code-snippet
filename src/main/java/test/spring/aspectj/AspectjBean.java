package test.spring.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/2/17
 */
@Aspect
public class AspectjBean {

    @Pointcut("execution(* *(..))")
    public void p(){}

    @Around("p()")
    public void interceptor(JoinPoint joinPoint){
        System.out.println(joinPoint.getTarget());
        System.out.println("call interceptor...");
        if(joinPoint instanceof MethodInvocationProceedingJoinPoint){
            MethodInvocationProceedingJoinPoint mijpoint = (MethodInvocationProceedingJoinPoint) joinPoint;
            try{
                mijpoint.proceed();
            }catch(Throwable throwable){
                throwable.printStackTrace();
            }
        }
    }


    public void bb(){
        System.out.println("before call ");
    }

    public void aa(){
        System.out.println("after call ");
    }

}
