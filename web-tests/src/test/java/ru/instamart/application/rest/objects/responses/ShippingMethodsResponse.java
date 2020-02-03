package ru.instamart.application.rest.objects.responses;

import ru.instamart.application.rest.objects.ShippingMethod;

import java.util.List;

public class ShippingMethodsResponse extends BaseResponseObject {

    private List<ShippingMethod> shipping_methods = null;

    public List<ShippingMethod> getShipping_methods() {
        return shipping_methods;
    }

    public void setShipping_methods(List<ShippingMethod> shipping_methods) {
        this.shipping_methods = shipping_methods;
    }

}
