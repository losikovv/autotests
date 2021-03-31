package instamart.api.responses.v1;

import instamart.api.objects.v1.Order;
import instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrdersResponse extends BaseResponseObject {
    private List<Order> orders = null;
}
