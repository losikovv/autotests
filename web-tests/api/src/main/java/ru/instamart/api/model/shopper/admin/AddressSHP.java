package ru.instamart.api.model.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class AddressSHP extends BaseObject {
    private String vatsPhone;
    private Object comments;
    private Boolean deliveryToDoor;
    private String city;
    private String address1;
    private String fullName;
    private Double lon;
    private String building;
    private Object doorPhone;
    private String phone;
    private String street;
    private String fullAddress;
    private Object entrance;
    private Object floor;
    private Double lat;
}