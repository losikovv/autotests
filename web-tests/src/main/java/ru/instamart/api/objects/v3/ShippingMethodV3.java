package instamart.api.objects.v3;

import instamart.api.objects.BaseObject;

import java.util.List;
import java.util.StringJoiner;

public class ShippingMethodV3 extends BaseObject {

    private String title;
    private String type;
    private List<ShippingMethodOptionV3> options;

    public List<ShippingMethodOptionV3> getOptions() {
        return options;
    }

    public void setOptions(List<ShippingMethodOptionV3> options) {
        this.options = options;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new StringJoiner("\n", ShippingMethodV3.class.getSimpleName() + "\n", "\n")
                .add("title = '" + title + "'")
                .add("type = '" + type + "'")
                .toString();
    }
}
