package spring.valueof;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author yangqf
 * @version 1.0 2016/12/13
 */
@Component("APPLE")
public class Apple implements Fruit{
    @Override
    public FruitEnum code(){
        return FruitEnum.APPLE;
    }

//    @PostConstruct  使用beanPostProcessor处理
//    public void init(){
//        register(this);
//    }

    @Override
    public String name(){
        return "apple";
    }


}
