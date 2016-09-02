package core.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/1/24
 */
public class ReflectCallMethod {
    public void call() {
        System.out.println("reflect call method test");
    }
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ReflectCallMethod callObject = new ReflectCallMethod();

        Method callMethod = callObject.getClass().getDeclaredMethod("call");
        callMethod.invoke(callObject);
    }
}
