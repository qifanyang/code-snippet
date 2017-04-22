package dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2017/3/29.
 */
public class DubboCustomer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "dubbo-customer.xml" });
        context.start();
        Provider demoService = (Provider) context.getBean("demoService1"); // 获取bean
        // service
        // invocation
        // proxy
        User user = null;
        try {
            user = demoService.build("lili");
            System.out.println(" the message from server is:" + user.getName());
            System.out.println(" the message from server is:" + user.getAge());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
