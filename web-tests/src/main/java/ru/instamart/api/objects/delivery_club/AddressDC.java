package ru.instamart.api.objects.delivery_club;

import ru.instamart.api.objects.BaseObject;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
public class AddressDC extends BaseObject {
    private CityDC city;
    private StreetDC street;
    private CoordinatesDC coordinates;
    private String region;
    private String houseNumber;
    private String flatNumber;
    private String entrance;
    private String intercom;
    private String floor;
    private String comment;
}
