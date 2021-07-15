package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.MetaV2;
import ru.instamart.api.model.v2.PaymentToolTypesV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentToolTypesV2Response extends BaseResponseObject {
    @JsonProperty(value = "payment_tool_types")
    private List<PaymentToolTypesV2> paymentToolTypes = null;
    private MetaV2 meta;
}

