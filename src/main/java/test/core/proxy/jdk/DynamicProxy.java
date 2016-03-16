package test.core.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/1/22
 */
public class DynamicProxy {
    public static void main(String[] args) {
        IProxy proxy = (IProxy) Proxy.newProxyInstance(DynamicProxy.class.getClassLoader(), new Class[]{IProxy.class}, new Handler(new A()));
        proxy.say();
    }

    public static class A implements IProxy{
        public void say() {
            System.out.println("i am A proxy implements!!!");
        }
    }

    public static class Handler implements InvocationHandler{

        private Object obj;
        public Handler(Object obj){
            this.obj = obj;
        }

        public Object invoke(Object proxya, Method method, Object[] args) throws Throwable {
            //proxya是动态创建的代理类,就是proxy.say()中的proxy, 包含属性就是传入的InvocationHandler
            //当调用say()方法,会反射调用InvocationHandler的invoke方法
            System.out.println("call proxy invoke before !!!");
            method.invoke(obj,args);
            return null;
        }
    }
}
