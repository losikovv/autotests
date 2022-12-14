package ru.instamart.kraken.allure;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TestInstanceParameter {

    String value() default "";
}
