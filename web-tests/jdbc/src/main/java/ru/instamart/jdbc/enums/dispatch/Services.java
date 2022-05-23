package ru.instamart.jdbc.enums.dispatch;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum Services {

    WORKFLOW("paas-content-operations-workflow", 35432),
    CANDIDATES("paas-content-operations-candidates", 25432),
    SHIFTS("paas-content-operations-shifts", 15432),
    ETA("paas-content-operations-eta", 45432),
    ORDER_SERVICE("paas-content-operations-order-service", 65432);


    private final String namespace;
    private final int port;
}
