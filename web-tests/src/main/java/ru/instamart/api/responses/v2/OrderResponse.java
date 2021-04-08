package ru.instamart.api.responses.v2;

import ru.instamart.api.responses.BaseResponseObject;
import ru.instamart.api.objects.v2.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderResponse extends BaseResponseObject {
    private Order order;
}
