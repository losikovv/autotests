package instamart.api.objects.v2;

import instamart.api.objects.BaseObject;

public class ShippingRate extends BaseObject {

    private Double total_value;
    private Double value;
    private Boolean is_boost;
    private Boolean is_drop;
    private Boolean is_free;
    private DeliveryWindow delivery_window;
    private Boolean is_express_delivery;

    /**
     * No args constructor for use in serialization
     *
     */
    public ShippingRate() {
    }

    /**
     *
     * @param total_value
     * @param is_drop
     * @param is_boost
     * @param is_free
     * @param delivery_window
     * @param value
     */
    public ShippingRate(Double total_value, Double value, Boolean is_boost, Boolean is_drop, Boolean is_free, DeliveryWindow delivery_window) {
        super();
        this.total_value = total_value;
        this.value = value;
        this.is_boost = is_boost;
        this.is_drop = is_drop;
        this.is_free = is_free;
        this.delivery_window = delivery_window;
    }

    public Double getTotal_value() {
        return total_value;
    }

    public void setTotal_value(Double total_value) {
        this.total_value = total_value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getIs_boost() {
        return is_boost;
    }

    public void setIs_boost(Boolean is_boost) {
        this.is_boost = is_boost;
    }

    public Boolean getIs_drop() {
        return is_drop;
    }

    public void setIs_drop(Boolean is_drop) {
        this.is_drop = is_drop;
    }

    public Boolean getIs_free() {
        return is_free;
    }

    public void setIs_free(Boolean is_free) {
        this.is_free = is_free;
    }

    public DeliveryWindow getDelivery_window() {
        return delivery_window;
    }

    public void setDelivery_window(DeliveryWindow delivery_window) {
        this.delivery_window = delivery_window;
    }

    public Boolean getIs_express_delivery() {
        return is_express_delivery;
    }

    public void setIs_express_delivery(Boolean is_express_delivery) {
        this.is_express_delivery = is_express_delivery;
    }
}
