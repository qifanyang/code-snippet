package spring.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/12/11.
 */
@Repository
public class NameDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String selectNameById(long id){
        String sql = "select name from user where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new SingleColumnRowMapper<String>());
    }

    public void updateNameById(long id, String name){
        String sql = "update user set name = ? where id = ?";
        jdbcTemplate.update(sql, new Object[]{name, id});
    }
}
