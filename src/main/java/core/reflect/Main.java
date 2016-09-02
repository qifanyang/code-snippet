package core.reflect;

import java.lang.annotation.Annotation;

public class Main {

    public static void main(String[] args) throws NoSuchFieldException {
        System.out.println("Hello World!");
        Test test = new Test();
        test.setName("test no autowired annotation");
        System.out.println(test.getName());
        Annotation[] annotations = test.getClass().getDeclaredField("name").getAnnotations();
        for(Annotation annotation : annotations){
            System.out.println(annotation.getClass());
            System.out.println(annotation.annotationType());
        }
    }
}
