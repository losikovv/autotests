package ru.instamart.api.model.delivery_club;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeliveryWindowDC extends BaseObject {
    private String startAt;
    private String endAt;
}
