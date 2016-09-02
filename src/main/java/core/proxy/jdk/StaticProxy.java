package core.proxy.jdk;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/1/22
 */
public class StaticProxy implements IProxy{
    public IProxy proxy;

    public StaticProxy(IProxy proxy) {
        this.proxy = proxy;
    }

    public void say(){
        System.out.println("静态代理方法调用拦截");
        proxy.say();
    }
    public static class ProxyHandler implements IProxy{

        public void say() {
            System.out.println("static handler invoke!!!");
        }
    }

    public static void main(String[] args) {
        ProxyHandler proxyHandler = new ProxyHandler();
        StaticProxy staticProxy = new StaticProxy(proxyHandler);
        staticProxy.say();
    }
}
