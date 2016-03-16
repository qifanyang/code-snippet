package test.spring.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.util.List;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/2/15
 */
public class AgeDao {

    private JdbcTemplate jdbc;

    public JdbcTemplate getJdbc() {
        return jdbc;
    }

    public void setJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Integer> selectIntegerTest(){
        String sql = "select age from tbl_age where id = ?";
//        return jdbc.queryForObject(sql, Integer.class, 3);
//        return jdbc.queryForObject(sql, new SingleColumnRowMapper<Integer>(Integer.class), 3);
        return jdbc.query(sql, new Object[]{3}, new SingleColumnRowMapper<Integer>(Integer.class));
    }

}
