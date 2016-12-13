package spring.valueof;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangqf
 * @version 1.0 2016/12/13
 */
public interface Fruit{
    Map<FruitEnum, Fruit> subclassInstanceMap = new HashMap<>();

    FruitEnum code();

    String name();

    //default方法默认protected, 只有子类可以访问, 其它package无法方法
    default void register(Fruit fruit){
        subclassInstanceMap.put(fruit.code(), fruit);
    }

    static Fruit valueOf(FruitEnum fruitEnum){
        return subclassInstanceMap.get(fruitEnum);
    }
}
