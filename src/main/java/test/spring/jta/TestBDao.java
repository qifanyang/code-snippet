package test.spring.jta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.resource.spi.AuthenticationMechanism;

/**
 * @author yangqf
 * @version 1.0 2016/8/10
 */
@Component
public class TestBDao{
    @Autowired
    @Qualifier("jdbcTemplateB")
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(){
        String sql = "INSERT INTO counts (id, num) VALUE (21, 1111)";
        jdbcTemplate.execute(sql);
//        throw new RuntimeException("test");
    }
}
