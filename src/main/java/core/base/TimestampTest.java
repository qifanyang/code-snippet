package core.base;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author yangqf
 * @version 1.0 2016/4/27
 */
public class TimestampTest{
    public static void main(String[] args){
        long time = new Date().getTime();
        System.out.println(time);
        Timestamp timestamp = new Timestamp(time);
        System.out.println(timestamp.getTime());

        System.out.println(Timestamp.valueOf("2015-05-04 17:25:31.294").getTime());
    }
}
