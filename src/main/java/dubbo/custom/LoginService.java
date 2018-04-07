package dubbo.custom;

import custom.LoginUser;

/**
 * Created by yangqifan on 2018/4/7.
 */
public interface LoginService {
    String login(String name, String password, LoginUser loginUser);
}
