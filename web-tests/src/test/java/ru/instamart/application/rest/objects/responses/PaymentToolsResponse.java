package ru.instamart.application.rest.objects.responses;

import ru.instamart.application.rest.objects.Meta;
import ru.instamart.application.rest.objects.PaymentTool;

import java.util.List;

public class PaymentToolsResponse extends BaseResponseObject {


    private List<PaymentTool> payment_tools = null;
    private Meta meta;

    public List<PaymentTool> getPayment_tools() {
        return payment_tools;
    }

    public void setPayment_tools(List<PaymentTool> payment_tools) {
        this.payment_tools = payment_tools;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}

