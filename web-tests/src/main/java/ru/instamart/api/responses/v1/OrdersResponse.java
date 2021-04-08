package ru.instamart.api.responses.v1;

import ru.instamart.api.objects.v1.Order;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrdersResponse extends BaseResponseObject {
    private List<Order> orders = null;
}
