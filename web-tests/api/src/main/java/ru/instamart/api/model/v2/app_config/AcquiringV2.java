package ru.instamart.api.model.v2.app_config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class AcquiringV2 extends BaseObject {
    private String cloudpayments;
    private String sber;
    @JsonProperty(value = "authorize_amount")
    private Integer authorizeAmount;
}
