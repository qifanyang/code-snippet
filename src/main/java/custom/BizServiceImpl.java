package custom;

import custom.annotation.Customizable;
import org.springframework.stereotype.Service;

/**
 * Created by yangqifan on 2018/4/7.
 */
@Service
@Customizable
public class BizServiceImpl implements BizService {

    @Override
    public Object exe() {
        System.out.println("biz exe()");
        return "ok";
    }

    @Override
    public Object custom(int age, LoginUser loginUser) {
        System.out.println("biz custom");
        System.out.println(age);
        return "custom";
    }
}
