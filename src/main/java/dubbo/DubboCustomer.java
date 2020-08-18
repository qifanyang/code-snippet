package dubbo;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Protocol;
import custom.LoginUser;
import dubbo.custom.LoginService;
import dubbo.test.Provider;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2017/3/29.
 */
public class DubboCustomer {
    public static void main(String[] args) {
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "dubbo-customer.xml" });
        context.start();
        Provider demoService = (Provider) context.getBean("demoService1"); // 获取bean

        LoginService loginService = context.getBean(LoginService.class);
        LoginUser loginUser = new LoginUser();
        loginUser.setId("1");
        System.out.println(loginService.login("", "", loginUser));
        // service
        // invocation
        // proxy
//        User user = null;
//        try {
//            user = demoService.build("lili");
//            System.out.println(" the message from server is:" + user.getName());
//            System.out.println(" the message from server is:" + user.getAge());
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
}
