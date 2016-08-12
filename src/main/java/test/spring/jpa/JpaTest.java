package test.spring.jpa;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author yangqf
 * @version 1.0 2016/8/12
 */
public class JpaTest{

    public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-jpa.xml");

        UserService userService = context.getBean(UserService.class);

        userService.createUser(33L, 6);

        context.close();//不关闭的话,使用了hibernate不会退出程序

        //遇到的问题
        //http://stackoverflow.com/questions/24721688/org-hibernate-persistentobjectexception-detached-entity-passed-to-persist-whe
    }
}
