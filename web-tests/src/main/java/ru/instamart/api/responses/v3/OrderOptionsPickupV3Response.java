package ru.instamart.api.responses.v3;

import ru.instamart.api.objects.v3.PaymentMethodV3;
import ru.instamart.api.objects.v3.ReplacementOptionV3;
import ru.instamart.api.objects.v3.ShippingMethodOptionV3;
import ru.instamart.api.objects.v3.ShippingMethodV3;
import ru.instamart.api.responses.BaseResponseObject;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class OrderOptionsPickupV3Response extends BaseResponseObject {
    private List<Object> optionals = null;
    private List<PaymentMethodV3> payment_methods = null;
    private List<ShippingMethodV3> shipping_methods = null;
    private List<ReplacementOptionV3> replacement_options = null;

    public List<Object> getOptionals() {
        return optionals;
    }

    public void setOptionals(List<Object> optionals) {
        this.optionals = optionals;
    }

    public List<PaymentMethodV3> getPayment_methods() {
        return payment_methods;
    }

    public void setPayment_methods(List<PaymentMethodV3> payment_methods) {
        this.payment_methods = payment_methods;
    }

    public List<ShippingMethodV3> getShipping_methods() {
        return shipping_methods;
    }

    public void setShipping_methods(List<ShippingMethodV3> shipping_methods) {
        this.shipping_methods = shipping_methods;
    }

    public List<ReplacementOptionV3> getReplacement_options() {
        return replacement_options;
    }

    public void setReplacement_options(List<ReplacementOptionV3> replacement_options) {
        this.replacement_options = replacement_options;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("optionals", optionals).append("payment_methods", payment_methods).append("shipping_methods", shipping_methods).append("replacement_options", replacement_options).toString();
    }

}
