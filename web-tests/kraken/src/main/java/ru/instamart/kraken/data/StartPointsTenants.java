package ru.instamart.kraken.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum StartPointsTenants {

    METRO_3(55.7012983, 37.7283667);

    private final Double lat;
    private final Double lon;

}
