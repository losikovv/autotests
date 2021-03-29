package instamart.api.responses.deliveryclub;

import instamart.api.objects.delivery_club.SlotDC;
import instamart.api.objects.delivery_club.ZoneDC;
import instamart.api.responses.BaseResponseObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SlotsDCResponse extends BaseResponseObject {
    public ZoneDC zone;
    public List<SlotDC> slots;
}
