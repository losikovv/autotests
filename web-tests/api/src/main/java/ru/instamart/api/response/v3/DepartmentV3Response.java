package ru.instamart.api.response.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v3.DepartmentV3;
import ru.instamart.api.model.v3.MetaV3;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class DepartmentV3Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("promo_badges")
    private List<Object> promoBadges;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("favorite_products")
    private List<Object> favoriteProducts;

    @JsonSchema(required = true)
    private DepartmentV3 department;

    @JsonSchema(required = true)
    private List<DepartmentV3> departments;

    @JsonSchema(required = true)
    private MetaV3 meta;
}
