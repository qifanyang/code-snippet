package dubbo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * dubbo 在不确定consumer是否知道能否反序列化某些异常时,会将异常转化成RuntimeException.
 * 在明确知道客户端能够范序列化某些异常时,可以抛出. 应该基于dubbo的方式扩展,比如替换ExceptionFilter
 * 这里使用spring beanpostprocessor处理
 * Created by Administrator on 2017/4/22.
 */
public class DubboKnowExceptionTranslator implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("bean post processor postProcessBeforeInitialization for bean :" + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("bean post processor postProcessAfterInitialization for bean :" + beanName);
        if(bean instanceof DemoServiceImpl){
            System.out.println(beanName);
        }
        //TODO 只创建dubbo serviceImpl的动态代理
        Class<?>[] interfaces = bean.getClass().getInterfaces();
        Object o = Proxy.newProxyInstance(getClass().getClassLoader(), interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                try{
                    return  method.invoke(bean, args);
                }catch (Throwable e){
                    //转化为consumer知道的异常
//                    if(e instanceof MyException){
                        throw new MyException(e.getMessage());
//                    }
                }
            }
        });
        return o;
    }
}
