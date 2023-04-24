package core.generic;

import spring.autowire.App;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PECSTest {
    public static void main(String[] args) {

        List<Fruit> fruitList = Arrays.asList(new Apple());
        iterator(fruitList); //pass, 方法签名一样
        List<Apple> appleList = Arrays.asList(new Apple());
        //编译不通过, required List<Fruit> , provided List<Apple>, 说明两种List不是同一种类型
        //iterator(appleList);
        //编译通过, 告诉编译器可以传递Fruit的子类
        iterator1(appleList);

        List<Fruit> appleList1 = Arrays.asList(new Apple());
        //编译不通过required List<Fruit>, provided List<Apple>
        //addFruit(appleList);
        addFruit2(appleList1);

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
    }
    //? super Banana 表示banana的超类,里面可以放Banana
    public static void addFruit2(List<? super Banana> list){
        list.add(new Banana());
    }
}
