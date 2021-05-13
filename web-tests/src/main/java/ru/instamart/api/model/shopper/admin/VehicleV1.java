package ru.instamart.api.model.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class VehicleV1 extends BaseObject {
    private Integer id;
    private Integer shopperId;
    private String model;
    private String number;
    private Integer volume;
    private Object favoredAt;
    private Boolean highCapacity;
}
