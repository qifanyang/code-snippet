package test.spring.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/2/26
 */
public class MultipleThreadCAS {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void test(){
        final CountDownLatch latch = new CountDownLatch(20);

        for(int i = 0; i < 20; i++){
            new Thread(){
                @Override
                public void run(){
                    latch.countDown();
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from tbl_age");
                    Object age = maps.get(0).get("age");
                    int r = Integer.valueOf(age.toString()) + 1;
                    System.out.println("read age = " + age.toString());
                    jdbcTemplate.update("update tbl_age set age = ? where age = ?",r, age.toString());
                }
            }.start();
        }
    }
}
