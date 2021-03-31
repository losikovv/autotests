package instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.Meta;
import instamart.api.objects.v2.PaymentTool;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentToolsResponse extends BaseResponseObject {
    @JsonProperty(value = "payment_tools")
    private List<PaymentTool> paymentTools = null;
    private Meta meta;
}

