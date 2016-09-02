package spring.bean;

/**
 * @author yangqf
 * @version 1.0 2016/4/21
 */
public class BeanForConstructorInject{
    private String name;
    private int age;

    public BeanForConstructorInject(){}
    public BeanForConstructorInject(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }
}
