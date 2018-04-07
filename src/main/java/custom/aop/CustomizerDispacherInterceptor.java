package custom.aop;

import custom.LoginUser;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.ReflectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现定制分发逻辑
 * Created by yangqifan on 2018/4/7.
 */
public class CustomizerDispacherInterceptor implements MethodInterceptor {
    private Map<String, Object> map = new HashMap<>();


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        //执行方法调用时,需要确定执行哪个自定义逻辑,类似Session Id,在执行自定义方法
        //调用需要提供这样一个参数
        Object[] arguments = invocation.getArguments();
        if(map.size() > 0 && null != arguments && arguments.length > 0){
            for(Object arg : arguments){
                if(arg instanceof LoginUser){
                    LoginUser loginUser = LoginUser.class.cast(arg);
                    Object interceptor = map.get(loginUser.getId());
                    if(null != interceptor){
                        return ReflectionUtils.invokeMethod(invocation.getMethod(), interceptor, invocation.getArguments());
                    }
                    break;
                }
            }
        }
        return invocation.proceed();
    }

    public void inject(String key, Object obj) {
        map.put(key, obj);
    }
}
