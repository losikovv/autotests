package ru.instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v2.MetaV2;
import ru.instamart.api.objects.v2.PaymentToolV2;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentToolsV2Response extends BaseResponseObject {
    @JsonProperty(value = "payment_tools")
    private List<PaymentToolV2> paymentTools = null;
    private MetaV2 meta;
}

