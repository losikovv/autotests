package instamart.api.objects.v2;

import instamart.api.objects.BaseObject;

public class Cancellation extends BaseObject {

    private String reason;
    private Order order;

    /**
     * No args constructor for use in serialization
     *
     */
    public Cancellation() {
    }

    /**
     *
     * @param reason
     * @param order
     */
    public Cancellation(String reason, Order order) {
        super();
        this.reason = reason;
        this.order = order;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
