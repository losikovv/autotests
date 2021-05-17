package ru.instamart.api.model.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class EquipmentV1 extends BaseObject {
    private Integer id;
    private Integer shopperId;
    private String serial;
    private String suppliedAt;
    private String kind;
}
