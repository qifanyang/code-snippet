package test.spring;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/2/2
 */
public class PrototypeBean {
    private PrototypeBeanInner inner;

    public PrototypeBeanInner getInner() {
        return inner;
    }

    public void setInner(PrototypeBeanInner inner) {
        this.inner = inner;
    }
}
