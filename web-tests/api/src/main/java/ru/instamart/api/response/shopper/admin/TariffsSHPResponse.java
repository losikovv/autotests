package ru.instamart.api.response.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.MetaV1;
import ru.instamart.api.model.shopper.admin.TariffV1;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class TariffsSHPResponse extends BaseResponseObject {
    @NotEmpty
    @JsonSchema(required = true)
    private List<TariffV1> tariffs = null;

    @JsonSchema(required = true)
    private MetaV1 meta;
}
