package ibatis.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yangqf
 * @version 1.0 2016/7/25
 */
public interface UserMapper extends UserMapperExtend{
     List<User> selectAllAuthors();

     User selectById(int id);

     User selectByIdAndAge(@Param("id") Integer id, @Param("age") Integer age);

     User selectByUser(User user);
}
