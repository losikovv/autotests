package instamart.api.v2.objects;

import instamart.api.common.BaseObject;

import java.util.List;

public class ShipAddressChange extends BaseObject {

    private Order order;
    private List<Object> losses = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ShipAddressChange() {
    }

    /**
     *
     * @param order
     * @param losses
     */
    public ShipAddressChange(Order order, List<Object> losses) {
        super();
        this.order = order;
        this.losses = losses;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Object> getLosses() {
        return losses;
    }

    public void setLosses(List<Object> losses) {
        this.losses = losses;
    }

}