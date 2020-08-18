package core;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/1/29
 */
public class A {

    static boolean foo(char c) {
        System.out.print(c);
        return true;
    }
    public static void main(String[] argv) {
        int i = 0;
        for (foo('A'); foo('B') && (i < 2); foo('C')) {
            i++;
            foo('D');
        }
        List<Integer> sss = Arrays.asList(3, 1, 7);
        Collections.sort(sss);
        System.out.println(sss);
        new Thread(){
            @Override
            public void run() {
                //do something
            }
        }.start();
    }
}
