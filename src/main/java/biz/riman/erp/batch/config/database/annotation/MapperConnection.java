package biz.riman.erp.batch.config.database.annotation;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Target({ TYPE, METHOD, FIELD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME) 
@Documented 
@Component
public @interface MapperConnection {
    String value() default "";
}
