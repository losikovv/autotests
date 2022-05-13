package ru.instamart.kafka.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.stream.Stream;

@RequiredArgsConstructor
@ToString
@Getter
public enum Pods {
    ORDER_SERVICE("paas-content-operations-order-service", "app.kubernetes.io/component=app"),
    DISPATCH("paas-content-operations-dispatch", "app.kubernetes.io/component=app"),
    ROUTE_ESTIMATOR("paas-content-operations-route-estimator", "app.kubernetes.io/component=app"),
    SHIFT_SERVICE("paas-content-operations-shifts", "app=paas-content-operations-shifts");

    private final String nameSpace;
    private final String label;

    public static Stream<Pods> stream() {
        return Stream.of(Pods.values());
    }
}
