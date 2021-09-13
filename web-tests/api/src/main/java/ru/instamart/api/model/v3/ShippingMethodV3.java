package ru.instamart.api.model.v3;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShippingMethodV3 extends BaseObject {
    private String title;
    private String type;
    private List<ShippingMethodOptionV3> options;
}
