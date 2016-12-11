package spring.jdbc;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/2/15
 */

public class SelectTest {

    private static <T> T singleValue(ArrayList<T> results) {
        if (results == null || results.size() == 0) {
            return null;
        } else {
            return results.iterator().next();
        }
    }

    private static int cc(ArrayList<Integer> results){
        return singleValue(results);
        //如果singleValue返回值为null,java自动装箱机制会报空指针异常
    }
    public static void main(String[] args) {
        //spring jdbc queryForInt, 查询结果集rs.isNull, 则返回的list包含null

        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(null);

        System.out.println(cc(list));//迭代返回的是第一个元素null

    }
}
