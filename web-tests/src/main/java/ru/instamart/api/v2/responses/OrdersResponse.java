package instamart.api.v2.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.v2.objects.Meta;
import instamart.api.v2.objects.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersResponse extends BaseResponseObject {

    private List<Order> orders = null;
    private Meta meta;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
