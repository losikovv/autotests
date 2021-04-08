package ru.instamart.api.objects.shopper;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Address extends BaseObject {
    private String fullName;
    private String phone;
    private String address1;
    private String comments;
    private String entrance;
    private String doorPhone;
    private String vatsPhone;
    private String floor;
    private Double lat;
    private Double lon;
    private Boolean deliveryToDoor;
    private String fullAddress;
    private String city;
    private String street;
    private String building;
}
