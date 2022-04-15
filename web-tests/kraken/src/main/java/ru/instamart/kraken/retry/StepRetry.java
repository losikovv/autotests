package ru.instamart.kraken.retry;

import io.qameta.allure.Step;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация позволяет повторить шаг несколько раз и после этого выбросить возникшее исключение.
 * Для корректного использования на методе должны быть обе аннотации {@link Step} и {@link StepRetry}
 * в противном случае не будет повтора шага.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StepRetry {

    //Количество повторов шага -- по умолчанию 1
    int count() default 1;
}
