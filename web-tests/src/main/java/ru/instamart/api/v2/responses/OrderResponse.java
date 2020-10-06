package instamart.api.v2.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.v2.objects.Order;

public class OrderResponse extends BaseResponseObject {

    private Order order;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrderResponse() {
    }

    /**
     *
     * @param order
     */
    public OrderResponse(Order order) {
        super();
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
