package test.spring.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
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
public class AgeDao {

    private JdbcTemplate jdbc;

    private TransactionTemplate transactionTemplate;//封装开启事务,提交事务,回滚事务模板代码,  使用模板方法设计模式,名字带有template很准确

    public JdbcTemplate getJdbc() {
        return jdbc;
    }

    public void setJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate){
        this.transactionTemplate = transactionTemplate;
    }

    public List<Integer> selectIntegerTest(){
        final String sql = "select age from tbl_age where id = ?";
//        return jdbc.queryForObject(sql, Integer.class, 3);
//        return jdbc.queryForObject(sql, new SingleColumnRowMapper<Integer>(Integer.class), 3);
        transactionTemplate.execute(new TransactionCallback(){

            @Override
            public Object doInTransaction(TransactionStatus status){
                return jdbc.query(sql, new Object[]{3}, new SingleColumnRowMapper<Integer>(Integer.class));
            }
        });
        return jdbc.query(sql, new Object[]{3}, new SingleColumnRowMapper<Integer>(Integer.class));
    }

}
