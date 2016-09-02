package core.base;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/3/19
 */
public class VolatileTest {
    static volatile int  a;
    static int  b;

    public static void main(String[] args) {
        a = 1;
        System.out.println(a);
        b = 1;
    }
}
