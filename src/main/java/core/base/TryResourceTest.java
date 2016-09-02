package core.base;

/**
 * @author yangqf
 * @version 1.0 2016/8/19
 */
public class TryResourceTest{

    public static void main(String[] args) throws Exception{
        try(B b = new B();
        ){

        }
    }

    public static class A implements Cloneable{

    }

    public static class B implements AutoCloseable{

        @Override
        public void close() throws Exception{
            System.out.println("call autocloseable close");
        }
    }
}
