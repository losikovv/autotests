package ru.instamart.kraken.listener;

import org.testng.annotations.Test;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;

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
     * сравнивает значение с {@link EnvironmentData#getServer()}
     * @return
     */
    String[] onServer() default {};
    /**
     * Позволяет пропускать тесты на конкретных тенантах
     * сравнивает значение с {@link EnvironmentData#getTenant()} ()}
     * @return
     */
    String[] onTenant() default {};
}
