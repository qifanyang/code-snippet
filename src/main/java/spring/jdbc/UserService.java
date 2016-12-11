package spring.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/12/11.
 */
@Service
public class UserService {
    @Autowired
    private AgeDao ageDao;
    @Autowired
    private NameService nameService;

    public int selectAge(long id){
        int age = ageDao.selectAgeById(id);
        System.out.println("age = " + age);
        return age;
    }

    @Transactional
    public void incrementAge(long id){
        int age = selectAge(id);
        ageDao.updateAgeById(id, ++age);
        selectAge(id);
        //注意,nameService上没有事务注解,
        //虽然nameSerice上没有事务注解,但是incrementAge(...)方法上有事务注解,所以当前线程已经开启事务
        //只要nameService和UserService使用的是一个数据源,那么将会使用一个事务,就算nameService没有使用
        //事务注解,也会加入到当前事务中执行
        nameService.chageNameById(id, "修改名字");
    }
}
