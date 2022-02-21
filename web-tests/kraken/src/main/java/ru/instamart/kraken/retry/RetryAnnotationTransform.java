package ru.instamart.kraken.retry;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Позволяет массово установить аннотацию для перезапуска тестов через suite
 * для этого нужно в блоке <listeners> указать следующее
 * <listener class-name="ru.instamart.kraken.retry.RetryAnnotationTransform"/>
 * после установки !ВСЕ! тесты при фейле будут перезапускаться {@link RetryAnalyzer#DEFAULT_MAX_RETRY} раз
 * если нужно изменить для конкретного теста количество перезапусков, то нужно указать аннотацию {@link Retry}
 */
public final class RetryAnnotationTransform implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
