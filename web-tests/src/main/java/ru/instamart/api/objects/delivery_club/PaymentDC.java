package instamart.api.objects.delivery_club;

import instamart.api.objects.BaseObject;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
public class PaymentDC extends BaseObject {
    private String type;
    private String requiredMoneyChange;
}
