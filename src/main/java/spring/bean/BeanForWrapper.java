package spring.bean;

import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;

/**
 * @author yangqf
 * @version 1.0 2016/6/1
 */
public class BeanForWrapper{

    public void setA(String a){
        System.out.println("print  + " + a);
    }

    public static void main(String[] args){
        BeanForWrapper beanForWrapper = new BeanForWrapper();
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(beanForWrapper);

        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
//        for(PropertyDescriptor pd : propertyDescriptors){
//            System.out.println(pd.getWriteMethod().getName());
//        }
    }

}
