package ru.instamart.api.responses.delivery_club;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.delivery_club.SlotDC;
import ru.instamart.api.objects.delivery_club.ZoneDC;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class SlotsDCResponse extends BaseResponseObject {
    private ZoneDC zone;
    private List<SlotDC> slots;
}
