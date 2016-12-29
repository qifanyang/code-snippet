package springboot;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@ComponentScan("springboot")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class,RedisAutoConfiguration.class,
        JmsAutoConfiguration.class,XADataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class SampleController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello fuck!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}
