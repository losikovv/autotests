package instamart.api.responses.v2;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.ShippingMethod;

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
