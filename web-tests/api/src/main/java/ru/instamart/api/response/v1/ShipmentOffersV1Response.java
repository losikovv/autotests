
package ru.instamart.api.response.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.ShipmentOfferV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentOffersV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private List<ShipmentOfferV1> offers;
}
