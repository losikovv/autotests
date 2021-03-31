package instamart.api.objects.delivery_club;

import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeliveryWindowDC extends BaseObject {
    public String startAt;
    public String endAt;
}
