package dubbo;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

/**
 * Created by Administrator on 2017/4/22.
 */
@Activate(group = Constants.PROVIDER, before = "exception")
public class MyExceptionFilter implements Filter{
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            Result result = invoker.invoke(invocation);
        }catch (Throwable t){
            throw new MyException(t.getMessage());
        }
        return null;
    }
}
