package ru.instamart.kraken.listener;

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
     * сравнивает значение с {@link ru.instamart.kraken.config.EnvironmentProperties#SERVER}
     * @return - список доступных серверов
     */
    String[] onServer() default {};
    /**
     * Позволяет запускать тесты на конкретных тенантах
     * сравнивает значение с {@link ru.instamart.kraken.config.EnvironmentProperties#TENANT}
     * @return - список доступных тенантов
     */
    String[] onTenant() default {};
}
