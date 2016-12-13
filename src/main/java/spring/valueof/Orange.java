package spring.valueof;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author yangqf
 * @version 1.0 2016/12/13
 */
@Component("ORANGF")
public class Orange implements Fruit{

    @Override
    public FruitEnum code(){
        return FruitEnum.ORANGE;
    }

    @Override
    public String name(){
        return "orange";
    }
}
