package instamart.api.v2.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.v2.objects.Meta;
import instamart.api.v2.objects.PaymentTool;

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

