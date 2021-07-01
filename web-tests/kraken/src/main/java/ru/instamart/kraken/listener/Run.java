package ru.instamart.kraken.listener;

import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Позволяет запускать тесты только на указанных серверах или тенантах, иначе пропускает запуск
 * аннотированного теста. Требует обязательного указания параметров иначе игнорируется
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Run {

    /**
     * Позволяет запускать тесты на конкретных серверах
     * сравнивает значение с {@link EnvironmentData#getServer()}
     * @return
     */
    String[] onServer() default {};
    /**
     * Позволяет запускать тесты на конкретных тенантах
     * сравнивает значение с {@link EnvironmentData#getTenant()} ()}
     * @return
     */
    String[] onTenant() default {};
}
