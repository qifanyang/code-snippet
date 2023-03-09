package spring.autowire;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class A {
    private int a;
    @Autowired
    private B b;
}
