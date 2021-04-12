package ru.instamart.api.objects.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentV3 extends BaseObject {
    private String type;
    @JsonProperty("package_ids")
    private List<Integer> packageIds = null;
    private String state;
    @JsonProperty("option_id")
    private String optionId;
}
