package core.base;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/3/20
 */
public class MemoryBarrierTest {
     static int a = 0;
     static int b = 0;

    public static void foo(){
        a = 1;
        b = 1;
    }

    public static void bar(){
        while(b == 0){
            System.out.println(b);
            continue;
        }
        System.out.println(b);
//        assert (a == 1);
    }

    public static void main(String[] args) {
        //assert (a == 1);
        new Thread(){
            @Override
            public void run() {
                bar();
            }
        }.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        foo();
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }
}
