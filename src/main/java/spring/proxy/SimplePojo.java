package spring.proxy;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/2/4
 */
public class SimplePojo implements Pojo {
    @Override
    public String pojoName() {
        System.out.println("call pojoName method...");
        return null;
    }
}
