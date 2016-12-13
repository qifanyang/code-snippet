package core.base;

import java.text.NumberFormat;
import java.util.Calendar;

/**
 * @author yangqf
 * @version 1.0 2016/12/5
 */
public class PercentTest{
    public static void main(String[] args){
        System.out.println(1.0*(9-1)/9);
        System.out.println(NumberFormat.getPercentInstance().format(1.0*(9-1)/9));
        long t = System.currentTimeMillis()-1000L*60*60*25;
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(day);
        calendar.setTimeInMillis(t);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(day);
    }
}
