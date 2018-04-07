package custom;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by yangqifan on 2018/4/7.
 */
public class CustomTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-scan-customizer.xml");
        context.start();

        LoginUser loginUser = new LoginUser();
        loginUser.setId("1");
        BizService bizService = context.getBean(BizServiceImpl.class);
        System.out.println(bizService.custom(99, loginUser));
        System.out.println();
    }

}
