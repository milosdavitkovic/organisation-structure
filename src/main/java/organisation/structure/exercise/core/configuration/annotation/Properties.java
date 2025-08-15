package organisation.structure.exercise.core.configuration.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;


/**
 * UtilClass annotation
 * has runtime visibility, and we can apply it to types (classes).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Service
public @interface Properties {
    @AliasFor(
            annotation = Component.class
    )
    String value() default "";
}
