
package ru.instamart.api.response.v2;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.PaymentToolTypesV2;
import ru.instamart.api.model.v2.PaymentToolV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentToolsWithTypesV2Response extends BaseResponseObject {

    @JsonProperty("payment_tool_types")
    private List<PaymentToolTypesV2> paymentToolTypes;
    @JsonProperty("payment_tools")
    private List<PaymentToolV2> paymentTools;
}
