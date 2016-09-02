package core.base;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author yangqf
 * @version 1.0 2016/8/18
 */
public class GetMethodTyepInfoTest{

    public static void a(List<String> list){ }
    public static void a(){ }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException{
            Method m = GetMethodTyepInfoTest.class.getMethod("a", List.class);
            Class<?>[] parameterTypes = m.getParameterTypes();
            for(Class c : parameterTypes){
                System.out.println(c.getName());
//                Object o = c.newInstance();
                System.out.println();
            }
            Type[] genericParameterTypes = m.getGenericParameterTypes();
            for(Type t : genericParameterTypes){
                ParameterizedType pp = (ParameterizedType) t;
//                ParameterizedType parameterizedType = pp.getClass().newInstance();
                System.out.println();
            }

        Method[] declaredMethods = GetMethodTyepInfoTest.class.getDeclaredMethods();
        //这里返回的method和调用getMethod("a", List.class);返回的还不一样,传入List.class,多做了一些工作
        System.out.println();

    }


}
