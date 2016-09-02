package core.classloder;

/**
 * @author yangqf
 * @version 1.0 2016/8/6
 */
public class ClassForNameTest{
    public static void main(String[] args) throws ClassNotFoundException{
        Class.forName("test.core.classloder.ClassForNameTest$A", false, ClassForNameTest.class.getClassLoader());
//        Class.forName("test.core.classloder.ClassForNameTest$A", true, ClassForNameTest.class.getClassLoader());
        System.out.println(A.x);
//        new A();
    }

    public static class A{
        static {
            System.out.println("dddd");
        }
        public static final int x = 1;
    }
}
