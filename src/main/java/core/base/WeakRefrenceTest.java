package core.base;

import java.lang.ref.WeakReference;

/**
 * @author yangqf
 * @version 1.0 2016/7/20
 */
public class WeakRefrenceTest{
    public static void main(String[] args){
        WeakReference<String> wr = new WeakReference<String>(new String("ffff"));
        System.out.println(wr.get());
        System.gc();
        System.out.println(wr.enqueue());
        System.out.println(wr.get());
    }
}
