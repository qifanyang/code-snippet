package core;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

    public static void main(String[] args){
        System.out.println(new Timestamp(new Date().getTime()).getTime());
        Integer vv = new Integer(777);
        System.out.println(vv.equals(777));
        //{"status":1000,"message":"操作成功","content":"6F9FF5ED4B2AC3E2190D2181560E4D7F50353DA3"}
        String s = "{\"status\":1000,\"message\":\"操作成功\",\"content\":\"6F9FF5ED4B2AC3E2190D2181560E4D7F50353DA3\"}";
        String regex="\"content\":\"(.*?)\"}";
        Matcher matcher=Pattern.compile(regex).matcher(s);
        while(matcher.find())
        {
            String ret=matcher.group(1);
            System.out.println(ret);
        }


        Integer integer = get();
        System.out.println(integer);
        ArrayList<Integer> list = new ArrayList<>();

    }

    public static Integer get(){
        return (Integer)null;
    }
}
