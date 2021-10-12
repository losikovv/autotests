package ru.instamart.kraken.listener;

import org.testng.annotations.Test;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.enums.Tenant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Интерфейс позволяющий пропускать тесты исключая их отображение в отчете.
 * Служит как замена стандартному пропуску в TestNG {@link Test#enabled()}
 * По умолчанию при указании аннотации без параметров пропускает данный тест без каких либо условий
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Skip {

    /**
     * Позволяет пропускать тесты на конкретных серверах
     * сравнивает значение с {@link ru.instamart.kraken.config.EnvironmentProperties#SERVER}
     * @return - список исключенных серверов
     */
    Server[] onServer() default {};
    /**
     * Позволяет пропускать тесты на конкретных тенантах
     * сравнивает значение с {@link ru.instamart.kraken.config.EnvironmentProperties#TENANT}
     * @return - список исключенных тенантов
     */
    Tenant[] onTenant() default {};
}
