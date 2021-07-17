package spring.annotation;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;


/**
 * Created by yangqifan on 2018/8/12.
 */
@ComposedAnno(xxxx = "serverTiest")
public class AnnoTest {
    public static void main(String[] args) {
        AnnoTest annoTest = new AnnoTest();
        Controller controller = AnnotatedElementUtils.findMergedAnnotation(annoTest.getClass(), Controller.class);
        Service service = AnnotatedElementUtils.findMergedAnnotation(annoTest.getClass(), Service.class);
        System.out.println(controller.value());
        System.out.println(service.value());
    }
}
