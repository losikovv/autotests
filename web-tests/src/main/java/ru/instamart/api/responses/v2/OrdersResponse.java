package instamart.api.responses.v2;

import instamart.api.objects.v2.Meta;
import instamart.api.objects.v2.Order;
import instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrdersResponse extends BaseResponseObject {
    private List<Order> orders = null;
    private Meta meta;
}
