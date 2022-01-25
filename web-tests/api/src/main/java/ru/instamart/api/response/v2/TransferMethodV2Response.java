package ru.instamart.api.response.v2;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.LossV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class TransferMethodV2Response extends BaseResponseObject {
    @JsonSchema(required = true)
    private OrderV2 order;

    @JsonSchema(required = true)
    private List<LossV2> losses;

}
