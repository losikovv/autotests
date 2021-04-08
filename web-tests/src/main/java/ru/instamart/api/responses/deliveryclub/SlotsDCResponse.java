package ru.instamart.api.responses.deliveryclub;

import ru.instamart.api.objects.delivery_club.SlotDC;
import ru.instamart.api.objects.delivery_club.ZoneDC;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class SlotsDCResponse extends BaseResponseObject {
    private ZoneDC zone;
    private List<SlotDC> slots;
}
