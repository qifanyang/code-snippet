package test.spring;

import org.springframework.stereotype.Component;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/2/25
 */
@Component
public class ForAutowireBean {

    public void say(){
        System.out.println("i am ForAutowireBean say method ...");
    }
}
