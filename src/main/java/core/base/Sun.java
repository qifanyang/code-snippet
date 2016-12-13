package core.base;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;

/**
 * @author yangqf
 * @version 1.0 2016/12/7
 */
public class Sun extends Father{
    private int a;
    public int b;
    public static void main(String[] args) throws NoSuchFieldException{
        Sun nextNode = new Sun();
        Father father = new Father();
        father.setId(8888);
        nextNode.setId(99);
        nextNode.setTtt(father);
//        Field id = sun.getClass().getDeclaredField("id");
//        Field id2 = sun.getClass().getField("id");
        BeanWrapper beanWrapper = new BeanWrapperImpl(nextNode);
        Object id = beanWrapper.getPropertyValue("id");
        System.out.println("id = "+id);
        Field[] fields = nextNode.getClass().getFields();//public 字段
//        Field[] fields = sun.getClass().getDeclaredFields();//获取私有和公有的字段
        for(int i =0; i < fields.length; i++){
            System.out.println(fields[i].getName());
        }
//        System.out.println(id2);
    }
}
