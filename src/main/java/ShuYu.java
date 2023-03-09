public class ShuYu {


    public static int add(int x, int y) {
        return x + y;
    }

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(add(3, 5));
    }
}
