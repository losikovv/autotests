package ru.instamart.api.response.v2;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.CityV2;
import ru.instamart.api.model.v2.MetaV2;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class CitiesV2Response extends BaseResponseObject {

    @NotEmpty
    @JsonSchema(required = true)
    private List<CityV2> cities;

    @JsonSchema(required = true)
    private MetaV2 meta;
}
