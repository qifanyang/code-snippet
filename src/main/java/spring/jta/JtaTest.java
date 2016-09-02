package spring.jta;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.*;

/**
 * @author yangqf
 * @version 1.0 2016/8/10
 */
public class JtaTest{
    public static void main(String[] args) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-jta.xml");

//        JtaTransactionManager jtaTransactionManager = context.getBean(JtaTransactionManager.class);
//        jtaTransactionManager.getUserTransaction().begin();
        JtaService jtaService = context.getBean(JtaService.class);

        jtaService.save();

        //不能够直接调用,会当做两个事务,应为事务传播特性存在的缘故,
        //退出savea时会提交事务,执行saveb时当前没有事务然后又创建一个事务
//        jtaService.savea();
//        jtaService.saveb();
//        jtaTransactionManager.getUserTransaction().commit();
    }
}
