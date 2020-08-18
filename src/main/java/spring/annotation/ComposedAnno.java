package spring.annotation;

import org.apache.ibatis.type.Alias;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yangqifan on 2018/8/12.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
@Controller
public @interface ComposedAnno {

    @AliasFor(annotation = Controller.class, attribute = "value")
    String value() default "";

    @AliasFor(annotation = Service.class, attribute = "value")
    String xxxx() default "";
}
