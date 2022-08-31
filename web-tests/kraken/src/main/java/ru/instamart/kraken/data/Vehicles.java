package ru.instamart.kraken.data;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public final class Vehicles {

    private CarType kind;
    private String model;
    private String number;
    private String volume;
}
