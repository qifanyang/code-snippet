package dubbo.custom.Impl;

import custom.LoginUser;
import custom.annotation.Customizer;
import org.springframework.stereotype.Service;

/**
 * 某个租户始终登录失败
 * Created by yangqifan on 2018/4/7.
 */

@Service
@Customizer(id = "1")
public class FailLoginServiceImpl extends LoginServiceImpl {
    @Override
    public String login(String name, String password, LoginUser loginUser) {
        return "fail";
    }
}
