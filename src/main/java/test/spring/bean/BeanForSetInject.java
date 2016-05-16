package test.spring.bean;

/**
 * @author yangqf
 * @version 1.0 2016/4/21
 */
public class BeanForSetInject{
    private String name;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
