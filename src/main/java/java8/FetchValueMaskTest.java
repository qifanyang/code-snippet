package java8;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by yangqifan on 2019/3/23.
 */
public class FetchValueMaskTest {
    public static void main(String[] args) {
        new FetchValueMaskTest().doTest();
    }

    public void doTest() {
        List<Foo> list = Arrays.asList(new Foo());
        list.stream()
                .map(this::fetchValueMaskField)
                .limit(1).collect(toList());

        String[] arrayOfWords = {"Goodbye", "World"};
        Stream<String> streamOfwords = Arrays.stream(arrayOfWords);
        List<Stream<String>> collect = streamOfwords.map(x -> x.split(""))
                .map(Arrays::stream)
                .collect(toList());

    }


    public List fetchValueMaskField(Object o) {
        Field[] declaredFields = o.getClass().getDeclaredFields();
        return Collections.emptyList();
    }




    static class Foo {
        private String name;
    }
}
