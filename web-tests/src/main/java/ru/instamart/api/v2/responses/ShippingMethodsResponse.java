package instamart.api.v2.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.v2.objects.ShippingMethod;

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
