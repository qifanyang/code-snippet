package spring.autowire;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public B b(){
        return new B();
    }

    @Bean
    public A a(){
        return new A();
    }
}
