package instamart.api.objects.delivery_club;

import instamart.api.objects.BaseObject;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
