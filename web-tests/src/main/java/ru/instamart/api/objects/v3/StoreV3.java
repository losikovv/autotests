package instamart.api.objects.v3;

import instamart.api.objects.BaseObject;

import java.util.List;
import java.util.StringJoiner;

public class StoreV3 extends BaseObject {
    private String id;
    private String name;
    private List<ShippingMethodV3> shipping_methods = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShippingMethodV3> getShipping_methods() {
        return shipping_methods;
    }

    public void setShipping_methods(List<ShippingMethodV3> shipping_methods) {
        this.shipping_methods = shipping_methods;
    }

    @Override
    public String toString() {
        return new StringJoiner("\n", StoreV3.class.getSimpleName() + "\n", "\n")
                .add("id = '" + id + "'")
                .add("name = '" + name + "'")
                .add("shipping_methods = " + shipping_methods)
                .toString();
    }
}
