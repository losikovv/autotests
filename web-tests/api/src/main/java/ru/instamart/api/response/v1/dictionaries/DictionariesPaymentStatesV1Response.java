package ru.instamart.api.response.v1.dictionaries;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class DictionariesPaymentStatesV1Response extends BaseResponseObject {
    @JsonSchema(required = true)
    @JsonProperty("payment_states")
    private List<PaymentState> paymentStates;

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class PaymentState extends BaseObject {
        @JsonSchema(required = true)
        private String id;
        @JsonSchema(required = true)
        private String name;
    }
}
