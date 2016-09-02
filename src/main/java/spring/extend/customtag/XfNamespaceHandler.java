package spring.extend.customtag;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author yangqf
 * @version 1.0 2016/6/3
 */
public class XfNamespaceHandler extends NamespaceHandlerSupport{
    @Override
    public void init(){
        registerBeanDefinitionParser("xxxx" , new XfBeanDefinitionParser());
    }
}
