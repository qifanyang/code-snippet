package spring.valueof;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * @author yangqf
 * @version 1.0 2016/12/13
 */
public class MainTest{
    public static void main(String[] args){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean-valueof.xml");

        Object bean = applicationContext.getBean(FruitEnum.APPLE.name());
        Fruit apple = Fruit.valueOf(FruitEnum.APPLE);
        System.out.println(apple.name());
        System.out.println(Fruit.valueOf(FruitEnum.PEAR));

        Map<FruitEnum, Fruit> subclassInstanceMap = Fruit.subclassInstanceMap;
        System.out.println(subclassInstanceMap);

        //spring是用来管理对象依赖,将实例放入接口中,通过接口获取实例和通过spring api获取区别不是很大
        //可以给Fruit实例取一个别名,客户端请求的参数可能是个数值,通过数值转换为别名也可以

        //通过接口访问,
    }
}
