package io.qase.api.annotation;

import java.lang.annotation.*;

/**
 * Внимание!!! Использовать с тестами с dataProvider
 * Запрос к qase.io отправляется по порядку в dataProvider
 * Порядок caseid должен совпадать в dataProvider
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CaseIDs {
    CaseId[] value();
}
