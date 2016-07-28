package test.ibatis.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yangqf
 * @version 1.0 2016/7/25
 */
public interface UserMapper{
    public List<User> selectAllAuthors();

    public User selectById(int id);

    public User selectByIdAndAge(@Param("id") Integer id, @Param("age") Integer age);

    public User selectByUser(User user);
}
