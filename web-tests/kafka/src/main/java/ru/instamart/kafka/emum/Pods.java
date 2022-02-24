package ru.instamart.kafka.emum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum Pods {
    ORDER_SERVICE("paas-content-operations-order-service", "app.kubernetes.io/component=app"),
    DISPATCH("paas-content-operations-dispatch", "app.kubernetes.io/component=app"),
    ROUTE_ESTIMATOR("paas-content-operations-route-estimator", "app.kubernetes.io/component=app");

    private final String nameSpace;
    private final String label;

}
