package ru.instamart.api.response.v1.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.AdminProductV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private AdminProductV1 product;
}
