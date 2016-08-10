package test.spring.jta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yangqf
 * @version 1.0 2016/8/10
 */
@Component
@Transactional
public class JtaService{

    @Autowired
    private TestADao testADao;

    @Autowired
    private TestBDao testBDao;

    public void savea(){
        testADao.save();
    }

    public void saveb(){
        testBDao.save();
    }

    public void save(){
        savea();
        saveb();
    }
}
