package test.spring.extend.customtag;

import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author yangqf
 * @version 1.0 2016/6/3
 */
public class XfBeanDefinitionParser implements BeanDefinitionParser{
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext){
        Object o = parserContext.extractSource(element);
        String age = element.getAttribute("age");
        String name = element.getAttribute("name");

        GenericBeanDefinition bd = new GenericBeanDefinition();
        PropertyValue page = new PropertyValue("age", age);
        PropertyValue pname= new PropertyValue("name", name);
        bd.getPropertyValues().addPropertyValue(page);
        bd.getPropertyValues().addPropertyValue(pname);
        bd.setBeanClassName("test.spring.extend.customtag.XfBean");

        parserContext.getReaderContext().getRegistry().registerBeanDefinition("xfbean", bd);

        return bd;
    }
}
