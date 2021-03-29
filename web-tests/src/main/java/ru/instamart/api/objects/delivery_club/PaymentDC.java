package instamart.api.objects.delivery_club;

import instamart.api.objects.BaseObject;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDC extends BaseObject {
    private String type;
    private String requiredMoneyChange;
}
