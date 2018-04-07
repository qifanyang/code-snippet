package custom.annotation;

import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * 表示可定制
 * Created by yangqifan on 2018/4/7.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Primary
public @interface Customizable {
}
