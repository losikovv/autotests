package instamart.api.objects.delivery_club;

import instamart.api.objects.BaseObject;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
public class DeliveryDC extends BaseObject {
    private String expectedDateTime;
    private AddressDC address;
    private String slotId;
}
