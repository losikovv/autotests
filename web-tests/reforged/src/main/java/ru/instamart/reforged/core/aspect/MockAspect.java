package ru.instamart.reforged.core.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.Routable;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.mock.Mock;
import ru.instamart.reforged.core.mock.MockRout;

import java.util.NoSuchElementException;

@Aspect
@Slf4j
public final class MockAspect {

    @Pointcut("@annotation(ru.instamart.reforged.core.mock.Mock)")
    public void withStepAnnotation() {
        //pointcut body, should be empty
    }

    @Pointcut("execution(* *(..))")
    public void anyMethod() {
        //pointcut body, should be empty
    }

    @Around("anyMethod() && withStepAnnotation()")
    public Object mockStart(final ProceedingJoinPoint joinPoint) throws Throwable {
        final var methodSignature = (MethodSignature) joinPoint.getSignature();
        final var mock = methodSignature.getMethod().getAnnotation(Mock.class);

        final var providerClass = mock.routeMockClass();
        final var mockRoute = mock.route();
        final var annotatedFiled = FieldUtils.getFieldsListWithAnnotation(providerClass, MockRout.class)
                .stream()
                .filter(f -> f.getAnnotation(MockRout.class).name().equals(mockRoute))
                .findFirst();

        Object result;
        try(final var ignored = new NetworkInterceptor(Kraken.getWebDriver(), (Routable) FieldUtils.readField(annotatedFiled.get(), providerClass))) {
            result = joinPoint.proceed();
        } catch (Exception e) {
            throw new NoSuchElementException(String.format("Can't found filed '%s' in class '%s'", mockRoute, providerClass));
        }

        return result;
    }
}
