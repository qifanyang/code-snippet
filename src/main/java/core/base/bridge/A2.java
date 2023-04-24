package core.base.bridge;

public class A2 extends A1{

    public void setData(Integer s){
        System.out.println("A2");
    }

    public static void main(String[] args) {
        A1 a2 = new A2();
        a2.setData(Integer.valueOf(1));
    }
}
