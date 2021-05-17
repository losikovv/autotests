package ru.instamart.api.response.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.OrderV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrdersV1Response extends BaseResponseObject {
    private List<OrderV1> orders = null;
}
