package test.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/2/17
 */
@Component
public class SimpleBean {

    @Autowired
    private ForAutowireBean fab;

    public void test(){
        System.out.println("call simplebean test()");
        fab.say();
    }
}
