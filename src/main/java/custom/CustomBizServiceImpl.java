package custom;

import custom.annotation.Customizer;
import org.springframework.stereotype.Service;

/**
 * Created by yangqifan on 2018/4/7.
 */
@Customizer(id = "1")
@Service
public class CustomBizServiceImpl extends BizServiceImpl {
    @Override
    public Object exe() {
        System.out.println("custom exe()");
        return "custom exe()";
    }

    @Override
    public Object custom(int age, LoginUser loginUser) {
        System.out.println("I am customizer service");
        super.custom(age, loginUser);
        return "custom custom()";
    }
}
