package instamart.api.responses.v2;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.Meta;
import instamart.api.objects.v2.PaymentTool;

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

