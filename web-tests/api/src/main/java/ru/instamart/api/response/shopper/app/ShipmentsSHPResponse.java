package ru.instamart.api.response.shopper.app;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.app.ShipmentSHP;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentsSHPResponse extends BaseResponseObject {
    @NotEmpty
    @JsonSchema(required = true)
    private List<ShipmentSHP.Data> data = null;
}
