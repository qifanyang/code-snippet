package test.spring.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yangqf
 * @version 1.0 2016/8/12
 */
@Component
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;


    public User createUser(Long id, Integer age){
        User user = new User();
        user.setId(id);
        user.setAge(age);

        userDao.save(user);

        return user;
    }
}
