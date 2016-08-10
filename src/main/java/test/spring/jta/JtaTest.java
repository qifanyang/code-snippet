package test.spring.jta;

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

        JtaTransactionManager jtaTransactionManager = context.getBean(JtaTransactionManager.class);
        jtaTransactionManager.getUserTransaction().begin();
        JtaService jtaService = context.getBean(JtaService.class);

        jtaService.savea();
        jtaService.saveb();
        jtaTransactionManager.getUserTransaction().commit();
    }
}
