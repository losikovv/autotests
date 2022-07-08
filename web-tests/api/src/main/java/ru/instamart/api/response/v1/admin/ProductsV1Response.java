package ru.instamart.api.response.v1.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.AdminProductV1;
import ru.instamart.api.model.v1.MetaV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductsV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private List<AdminProductV1> products;

    @JsonSchema(required = true)
    private MetaV1 meta;
}
