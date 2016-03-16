package test.spring.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

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
    }


    public void bb(){
        System.out.println("before call ");
    }

    public void aa(){
        System.out.println("after call ");
    }

}
