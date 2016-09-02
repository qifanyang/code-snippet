package core.base;

import org.junit.Test;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/3/25
 */
public class LongTest {
    @Test
    public void testLongEqauls(){
        Long a = Long.valueOf(11111L);
        Long b = Long.valueOf(11111L);
        if(a == b){
            System.out.println("ok");
        }else {
            System.out.println("no");
        }
    }

}
