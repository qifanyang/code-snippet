package core.exception;

import java.io.IOException;

/**
 * Created by Administrator on 2016/11/12.
 */
public class ExceptionVsErrorPerformanceTest {

    public static void main(String[] args) {
        int num = 10000000;
        long start = System.nanoTime();
        for (int i = 0; i < num; i++) {
            try {
                throwError();
            } catch (Error e) {

            }
        }
        System.out.println(System.nanoTime() - start);

        start = System.nanoTime();
        for (int i = 0; i < num; i++) {
            try {
                throwException();
            } catch (Exception e) {

            }
        }
        System.out.println(System.nanoTime() - start);
    }

    private static void throwException() throws Exception {
        throw new IOException();
    }

    private static void throwError() {
        throw new Error();
    }
}
