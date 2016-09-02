package spring.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author yangqf
 * @version 1.0 2016/8/12
 */
@Repository
public class UserDao{

    @PersistenceContext
    private EntityManager em;

    public Long save(User user) {
        //在hibernate中,设置了id,表示detach对象, 不能使用persist, 主键使用GenerateValue会忽略设置的id值
        if(user.getId() == null){
         em.persist(user);
        }else {
            em.merge(user);
        }
        return user.getId();
    }
}
