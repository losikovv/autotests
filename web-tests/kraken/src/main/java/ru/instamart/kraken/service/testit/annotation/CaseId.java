package ru.instamart.kraken.service.testit.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CaseId {
    String value();
}
