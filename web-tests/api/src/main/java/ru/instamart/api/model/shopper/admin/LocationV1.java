package ru.instamart.api.model.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class LocationV1 extends BaseObject {
    private String fullName;
    private String phone;
    private String address1;
    private String comments;
    private String entrance;
    private String doorPhone;
    private String vatsPhone;
    private Object floor;
    private Double lat;
    private Double lon;
    private Boolean deliveryToDoor;
    private String fullAddress;
    private String city;
    private String street;
    private String building;
}
