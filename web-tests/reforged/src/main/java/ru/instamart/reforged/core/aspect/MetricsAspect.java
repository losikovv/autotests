package ru.instamart.reforged.core.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.openqa.selenium.devtools.v109.performance.Performance;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.report.CustomReport;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Aspect
@Slf4j
public final class MetricsAspect {

    @Pointcut("@annotation(ru.instamart.reforged.core.annotation.Metrics)")
    public void withMetricsAnnotation() {
        //pointcut body, should be empty
    }

    @Pointcut("execution(* *(..))")
    public void anyMethod() {
        //pointcut body, should be empty
    }

    @Before("anyMethod() && withMetricsAnnotation()")
    public void metricsStart(final JoinPoint joinPoint) {
        Kraken.getDevTools().send(Performance.enable(Optional.empty()));
    }

    @AfterThrowing(pointcut = "anyMethod() && withMetricsAnnotation()", throwing = "e")
    public void metricsFailed(final Throwable e) {
        stopMetrics();
    }

    @AfterReturning(pointcut = "anyMethod() && withMetricsAnnotation()")
    public void metricsStop() {
        stopMetrics();
    }

    private void stopMetrics() {
        final var metrics = Kraken.getDevTools().send(Performance.getMetrics());
        if (nonNull(metrics) && !metrics.isEmpty())
            CustomReport.addPerfMetrics(metrics);
        Kraken.getDevTools().send(Performance.disable());
    }
}
