package instamart.api.objects.delivery_club;

import instamart.api.objects.BaseObject;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDC extends BaseObject {
    private String expectedDateTime;
    private AddressDC address;
    private String slotId;
}
