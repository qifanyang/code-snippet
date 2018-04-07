package core.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Created by yangqifan on 2017/9/10.
 */
public class SingleTest {

    private static final SingleTest instance = new SingleTest();

    private SingleTest(){
        if(instance != null){
            throw new IllegalStateException("it can not be instanced repeated");
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(instance);
        Constructor<? extends SingleTest> constructor = instance.getClass().getDeclaredConstructor();
        constructor.setAccessible(true);
        SingleTest singleTest = constructor.newInstance();
        System.out.println(singleTest);
        Field instance1 = instance.getClass().getDeclaredField("instance");
        instance1.setAccessible(true);

        instance1.set(instance, new SingleTest());
        System.out.println(instance);

    }
}
