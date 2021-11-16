package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentToolTypesV2 extends BaseObject {
    private Integer id;
    private String name;
    private String type;
    private String description;
    @JsonProperty("confirm_button_text")
    private String confirmButtonText;
    @JsonProperty("max_order_amount")
    private Integer maxOrderAmount;
    @JsonProperty("min_order_amount")
    private Integer minOrderAmount;
}
