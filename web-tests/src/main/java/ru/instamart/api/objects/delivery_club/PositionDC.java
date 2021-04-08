package ru.instamart.api.objects.delivery_club;

import ru.instamart.api.objects.BaseObject;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
public class PositionDC extends BaseObject {
    private String id;
    private Integer quantity;
    private String price;
    private String discountPrice;
}
