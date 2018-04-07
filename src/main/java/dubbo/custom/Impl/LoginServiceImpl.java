package dubbo.custom.Impl;

import custom.LoginUser;
import custom.annotation.Customizable;
import dubbo.custom.LoginService;
import org.springframework.stereotype.Service;

/**
 * Created by yangqifan on 2018/4/7.
 */
@Service
@Customizable
public class LoginServiceImpl implements LoginService {
    @Override
    public String login(String name, String password, LoginUser loginUser) {
        return "ok";
    }
}
