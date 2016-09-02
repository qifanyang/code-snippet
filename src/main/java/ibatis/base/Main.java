package ibatis.base;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author yangqf
 * @version 1.0 2016/7/25
 */
public class Main{
    public static void main(String[] args) throws IOException{
        String resource = "test/ibatis/base/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//            List<User> users = mapper.selectAllAuthors();
            User user = new User();
            user.setId(2);
//            User user1 = mapper.selectByUser(user);
//            mapper.selectById(2);
            mapper.selectByIdAndAge(2, 5);
            System.out.println("");
        }finally{
            sqlSession.close();
        }
    }
}
