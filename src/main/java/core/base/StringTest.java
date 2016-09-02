package core.base;

import java.lang.reflect.Field;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/3/3
 */
public class StringTest {
    public static void main(String[] args) throws Exception {
        String a = "aaa";
        String na = new String("aaa");

        Field nav = na.getClass().getDeclaredField("value");
        nav.setAccessible(true);
        Object o2 = nav.get(na);

        Field av = a.getClass().getDeclaredField("value");
        av.setAccessible(true);
        Object o1 = av.get(a);

        System.out.println(o1 == o2);

        byte[] bb = {65,65};
        String s = new String(bb);
        System.out.println(s);
        bb[0] = 66;
        System.out.println(s);
    }
}
