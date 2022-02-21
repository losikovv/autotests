package ru.instamart.kraken.retry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Позволяет устанавливать отдельное количество перезапусков
 * на конкретный метод. Реализация в {@link RetryAnalyzer}
 * через рефлексию получает количество перезапусков заданное для метода.
 * {@code Retry annotation = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Retry.class);}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Retry {

    /**
     * Задаётся количество попыток перезапуска теста
     */
    int count() default 0;
}