package ru.instamart.kraken.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum StartPointsTenants {

    METRO_3(55.7012983, 37.7283667),
    METRO_WORKFLOW_START(55.63533180125311, 37.62462253918757),
    METRO_WORKFLOW_END(55.60166514312233, 37.62097454092305),
    ETA(55.7575624, 37.6569041);

    private final Double lat;
    private final Double lon;

}
