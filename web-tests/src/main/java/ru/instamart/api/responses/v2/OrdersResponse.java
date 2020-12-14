package instamart.api.responses.v2;

import instamart.api.objects.v2.Meta;
import instamart.api.objects.v2.Order;
import instamart.api.responses.BaseResponseObject;

import java.util.List;

public class OrdersResponse extends BaseResponseObject {

    private List<Order> orders = null;
    private Meta meta;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
