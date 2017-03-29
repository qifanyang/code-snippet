/**
 * Created by Administrator on 2017/3/11.
 */

import java.io.BufferedReader;

import java.io.InputStreamReader;


public class BtraceTest {

    public static void main(String[] args) throws Exception {

        BtraceTest test = new BtraceTest();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 0; i < 10; i++)

        {

            reader.readLine();

            int a = (int) Math.round(Math.random() * 1000);

            int b = (int) Math.round(Math.random() * 1000);

            System.out.println(test.add(a, b));

        }

    }

    public int add(int a, int b)

    {

        return a + b;

    }

}
