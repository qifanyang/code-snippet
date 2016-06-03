package test.sync;

/**
 * @author yangqf
 * @version 1.0 2016/5/19
 */
public abstract class A extends B{
    @Override
    public abstract void bb();

    public static void main(String[] args){
        B b = new A(){
            @Override
            public void bb(){
                System.out.println("aa");
            }
        };

        b.bb();
    }
}
