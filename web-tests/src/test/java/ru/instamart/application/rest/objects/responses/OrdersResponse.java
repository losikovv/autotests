package ru.instamart.application.rest.objects.responses;

import ru.instamart.application.rest.objects.Order;

public class OrdersResponse extends BaseResponseObject {

    private Order order;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrdersResponse() {
    }

    /**
     *
     * @param order
     */
    public OrdersResponse(Order order) {
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
