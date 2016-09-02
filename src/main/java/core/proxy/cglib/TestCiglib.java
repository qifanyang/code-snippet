package core.proxy.cglib;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/1/24
 */
public class TestCiglib {
    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        Hello hello = (Hello) proxy.getProxyInstance(new Hello());
        hello.say();
    }
}
