package custom.aop;

import custom.annotation.Customizable;
import custom.annotation.Customizer;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangqifan on 2018/4/7.
 */
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor, ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {
    private ApplicationContext applicationContext;

    //spring容器初始化完毕后可以清空该map
    private Map<Class, MethodInterceptor> cacheInteceptorMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //定制化和被定制化如何关联?
        //interface, 通过接口关联
        //多个类实现相同的接口,并且
        Class<?>[] interfaces = bean.getClass().getInterfaces();
        //如果有Customizable,表示可定制,对外暴露为Customizable类,代理需要实现分发
        Customizable customizable = bean.getClass().getAnnotation(Customizable.class);
        if(customizable != null){
            ProxyFactory proxyFactory = new ProxyFactory(bean);
            CustomizerDispacherInterceptor dispacherInterceptor = new CustomizerDispacherInterceptor();
            cacheInteceptorMap.put(bean.getClass(), dispacherInterceptor);
            proxyFactory.addAdvice(dispacherInterceptor);
            return proxyFactory.getProxy();
        }

        //如果有Customizer,表示是定制实现类
        Customizer customizer = bean.getClass().getAnnotation(Customizer.class);
        if(customizer != null){
            Class<?> superclass = bean.getClass().getSuperclass();
            if(!cacheInteceptorMap.containsKey(superclass)){
                //可能supperClass还没有实例化.这里主动获取一次
                Class<?>[] superclassInterfaces = superclass.getInterfaces();
                if(null != superclass && superclassInterfaces.length > 0){
                    for(Class<?> c : superclassInterfaces){
                        applicationContext.getBean(c);
                    }

                }
            }
            if(cacheInteceptorMap.containsKey(superclass)){
                MethodInterceptor dispacherInterceptor = cacheInteceptorMap.get(superclass);
                try {
                    Method injectMethod = dispacherInterceptor.getClass().getMethod("inject", String.class, Object.class);
                    String id = customizer.id();
                    if("".equals(id)){
                        throw new IllegalStateException("Customizer id can not be empty ");
                    }
                    injectMethod.invoke(dispacherInterceptor, id, bean);

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    throw new IllegalStateException("get inject method fail...");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new IllegalStateException("inject method fail...");
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    throw new IllegalStateException("inject method fail...");
                }

            }

        }


        return bean;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //初始化完成后clear
        cacheInteceptorMap.clear();
    }
}
