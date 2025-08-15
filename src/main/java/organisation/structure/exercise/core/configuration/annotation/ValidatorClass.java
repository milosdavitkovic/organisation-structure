package organisation.structure.exercise.core.configuration.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;


/**
 * ValidatorClass annotation
 * has runtime visibility, and we can apply it to types (classes).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Service
public @interface ValidatorClass {
    @AliasFor(
            annotation = Service.class
    )
    String value() default "";
}
