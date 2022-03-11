package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentToolTypesV2 extends BaseObject {
    private Integer id;
    private String name;
    private String type;
    @Null
    private String description;
    @Null
    @JsonProperty("confirm_button_text")
    private String confirmButtonText;
    @Null
    @JsonProperty("max_order_amount")
    private Integer maxOrderAmount;
    @Null
    @JsonProperty("min_order_amount")
    private Integer minOrderAmount;
}
