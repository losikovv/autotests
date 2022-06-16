package ru.instamart.reforged.core.cdp.model;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder(setterPrefix = "with")
@ToString
public final class GeoLocation {

    private float latitude;
    private float longitude;
    private float accuracy;
}
