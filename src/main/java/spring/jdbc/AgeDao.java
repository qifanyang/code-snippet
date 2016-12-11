package spring.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

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
@Repository
public class AgeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionTemplate transactionTemplate;//封装开启事务,提交事务,回滚事务模板代码,  使用模板方法设计模式,名字带有template很准确


    public int selectAgeById(long id){
        String sql = "select age from user where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new SingleColumnRowMapper<Integer>());
    }

    public void updateAgeById(long id, int age){
        String sql = "update user set age = ? where id = ?";
        jdbcTemplate.update(sql, new Object[]{age, id});
    }







    public List<Integer> selectIntegerTest(){
        final String sql = "select age from tbl_age where id = ?";
//        return jdbc.queryForObject(sql, Integer.class, 3);
//        return jdbc.queryForObject(sql, new SingleColumnRowMapper<Integer>(Integer.class), 3);
        transactionTemplate.execute(new TransactionCallback(){

            @Override
            public Object doInTransaction(TransactionStatus status){
                return jdbcTemplate.query(sql, new Object[]{3}, new SingleColumnRowMapper<Integer>(Integer.class));
            }
        });
        return jdbcTemplate.query(sql, new Object[]{3}, new SingleColumnRowMapper<Integer>(Integer.class));
    }

}
