
package ru.instamart.api.response.v2;

import java.util.List;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.LossV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class TransferMethodLossesV2Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private List<LossV2> losses;
}
