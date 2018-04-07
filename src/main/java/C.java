import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by yangqifan on 30/10/2017.
 */
public class C {
//    public static void main(String[] args) {
//        int[] x = new int[1];
//        System.out.println(x[-0]);
//        System.out.println(-0 == 0);
//    }

        static volatile Queue<String> que = new ArrayDeque<>();
        public static void main(String args[]) {
            Thread1 thread = new Thread1();
            thread.start();
            Scanner sc = new Scanner(System.in);
            while(true) {

                String str = sc.nextLine();
                que.add(str);
//                System.out.println(que.peek());
            }
        }
        static class Thread1 extends Thread{
            public void run() {
                for(;;) {

                    if(que.isEmpty() == false) {
                        System.out.println(que.poll());
                    }
                }
            }
        }
}
