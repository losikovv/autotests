package ru.instamart.api.model.delivery_club;

import ru.instamart.api.model.BaseObject;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
public class TotalDC extends BaseObject {
    private String totalPrice;
    private String discountTotalPrice;
    private String deliveryPrice;
}
