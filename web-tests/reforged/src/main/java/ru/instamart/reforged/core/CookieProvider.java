package ru.instamart.reforged.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CookieProvider {

    String[] cookies() default {"COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_GUEST"};
    Class<?> cookieFactory() default Object.class;
}
