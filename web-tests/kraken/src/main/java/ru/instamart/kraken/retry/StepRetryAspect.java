package ru.instamart.kraken.retry;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import static java.util.Objects.nonNull;

@Aspect
public final class StepRetryAspect {

    private static final ThreadLocal<Boolean> processing = ThreadLocal.withInitial(() -> false);

    //Получаем методы с аннотацией {@link StepRetry}
    @Pointcut("@annotation(ru.instamart.kraken.retry.StepRetry)")
    public void methodWithAnnotation() {
    }

    //Получаем методы с аннотацией {@link Step}
    @Pointcut("@annotation(io.qameta.allure.Step)")
    public void methodWithStepAnnotation() {
    }

    //Получаем публичные методы
    @Pointcut("execution(public * *(..))")
    public void anyPublicMethod() {
    }

    /**
     * Метод инджектится в процесс выполнения метода соответствующего паттерну на основе поинткатов выше
     * повторяется несколько раз, если выпадает исключение при вызове или после первого успешного выполнения выходит из цикла.
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("methodWithAnnotation() && methodWithStepAnnotation() && anyPublicMethod()")
    public Object handler(final ProceedingJoinPoint joinPoint) throws Throwable {
        processing.set(true);

        final var methodSignature = (MethodSignature) joinPoint.getSignature();
        final var method = methodSignature.getMethod();
        final var stepRetry = method.getAnnotation(StepRetry.class);
        final var retryCount = stepRetry.count();

        Object result = null;
        Throwable storedException = null;
        boolean processed = false;
        int i = 0;

        while (!processed && i < retryCount) {
            try {
                result = joinPoint.proceed();
                processed = true;
            } catch (Throwable throwable) {
                storedException = throwable;
            }
            i++;
        }

        processing.set(false);

        if (!processed) {
            assert nonNull(storedException);
            throw storedException;
        }

        return result;
    }
}
