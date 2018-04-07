package dubbo;

import dubbo.test.Provider;

/**
 * Created by Administrator on 2017/3/29.
 */
public class DemoServiceImpl implements Provider {
    @Override
    public User build(String name) throws MyException {
        User user = new User();
        user.setName(name);
        user.setAge(44);
        if(user.getAge() == 44){
            throw new MyException("");
        }
        return user;
    }
}
