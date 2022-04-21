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
    SHIFTS("paas-content-operations-shifts", 15432);

    private final String namespace;
    private final int port;
}
