package ru.instamart.api.model.v3;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingMethodOptionV3  extends BaseObject {
    private String id;
    private Integer price;
    private Integer original_price;
    private String starts_at;
    private String ends_at;
}