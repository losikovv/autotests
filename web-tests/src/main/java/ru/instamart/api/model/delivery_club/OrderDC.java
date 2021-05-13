package ru.instamart.api.model.delivery_club;

import ru.instamart.api.model.BaseObject;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
public class OrderDC extends BaseObject {
    private String originalOrderId;
    private CustomerDC customer;
    private DeliveryDC delivery;
    private PaymentDC payment;
    @Singular private List<PositionDC> positions;
    private TotalDC total;
    private String comment;
}
