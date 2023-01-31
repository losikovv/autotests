package ru.instamart.kraken.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum StartPointsTenants {

    METRO_3(55.7012983, 37.7283667),
    METRO_9(55.915098, 37.541685),
    METRO_WORKFLOW_START(55.915134, 37.543219),
    METRO_WORKFLOW_END(55.921504, 37.541856),
    ETA(55.7575624, 37.6569041),
    ASSEMBLY_START(55.731706, 49.131854);

    private final Double lat;
    private final Double lon;

}
