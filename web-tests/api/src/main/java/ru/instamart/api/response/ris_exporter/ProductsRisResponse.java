package ru.instamart.api.response.ris_exporter;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.ris_exporter.ProductsDataRis;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductsRisResponse extends BaseResponseObject {
    @JsonSchema(required = true)
    private Integer totalCount;
    @JsonSchema(required = true)
    private ProductsDataRis data;
}