package instamart.api.responses.v1;

import instamart.api.objects.v1.Order;
import instamart.api.responses.BaseResponseObject;

import java.util.List;

public class OrdersResponse extends BaseResponseObject {

    private List<Order> orders = null;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
