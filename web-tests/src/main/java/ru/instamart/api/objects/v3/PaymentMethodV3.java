package ru.instamart.api.objects.v3;

import ru.instamart.api.objects.BaseObject;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class PaymentMethodV3 extends BaseObject {
    private String type;
    private String title;
    private List<PaymentMethodOptionV3> options = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PaymentMethodOptionV3> getOptions() {
        return options;
    }

    public void setOptions(List<PaymentMethodOptionV3> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("type", type).append("title", title).append("options", options).toString();
    }

}