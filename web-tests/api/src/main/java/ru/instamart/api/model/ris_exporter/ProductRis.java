package ru.instamart.api.model.ris_exporter;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductRis extends BaseObject {
    @JsonSchema(required = true)
    private String id;
    @JsonSchema(required = true)
    private String price;
    @JsonSchema(required = true)
    private String discountPrice;
    @JsonSchema(required = true)
    private String name;
    @JsonSchema(required = true)
    private List<String> categories;
    @JsonSchema(required = true)
    private Integer grammar;
    @JsonSchema(required = true)
    private Integer weight;
    @JsonSchema(required = true)
    private Integer volume;
    @JsonSchema(required = true)
    private Integer vat;
    @JsonSchema(required = true)
    private List<String> imageUrl;
    private String brand;
}
