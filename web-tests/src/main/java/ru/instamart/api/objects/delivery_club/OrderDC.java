package instamart.api.objects.delivery_club;

import instamart.api.objects.BaseObject;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDC extends BaseObject {
    private String originalOrderId;
    private CustomerDC customer;
    private DeliveryDC delivery;
    private PaymentDC payment;
    @Singular private List<PositionDC> positions;
    private TotalDC total;
    private String comment;
}
