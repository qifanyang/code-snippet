package core.generic;

import java.util.Arrays;
import java.util.List;

/**
 * 核心知识点:
 * 1. List<A>和List<B>是不同的类型, 可以等同于两个类,但java为了保持兼容性使用类型擦除,所以是伪泛型
 * 2. extends和super只是编译期间检查,是语法糖,运行时擦除,
 */
public class PECSTest {
    public static void main(String[] args) {

        List<Fruit> fruitList = Arrays.asList(new Apple());
        iterator(fruitList); //pass, 方法签名一样
        List<Apple> appleList = Arrays.asList(new Apple());
        //编译不通过, required List<Fruit> , provided List<Apple>, 说明两种List不是同一种类型
        //iterator(appleList);
        //编译通过, 告诉编译器可以传递Fruit的子类
        iterator1(appleList);

        List<Fruit> appleList1 = Arrays.asList(new Apple(), new Banana());
        //编译不通过required List<Fruit>, provided List<Apple>
        //addFruit(appleList);
        addFruit2(appleList1);
        addFruit3(appleList1, appleList1);

    }


    public static void iterator(List<Fruit> list){
        for(Fruit f : list){
            f.print();
        }
    }
    public static void iterator1(List<? extends Fruit> list){
        for(Fruit f : list){
            f.print();
        }
    }

    public static void addFruit(List<Fruit> list){
        list.add(new Apple());
    }
    public static void addFruit1(List<? extends Fruit> list){
        //编译不通过,capture of ? extends Fruit, 往集合添加元素,从集合角度来看是集合消费, consumer 使用super
        //list.add(new Apple());
        Fruit fruit = list.get(0);
    }
    //? super Banana 表示banana的超类,里面可以放Banana
    public static void addFruit2(List<? super Banana> list){
        list.add(new Banana());
        //list.add(new Fruit());
//        Fruit object = list.get(0);
    }

    public static void addFruit3(List<? super Banana> list, List<? extends Fruit> list1){
        list.add(new Banana());
        Object object = list.get(0);
        Fruit fruit = list1.get(0);
    }
}
