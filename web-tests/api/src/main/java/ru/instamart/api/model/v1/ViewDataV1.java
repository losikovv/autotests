
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ViewDataV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("assembly_items")
    private List<Object> assemblyItems;

    @Null
    @JsonSchema(required = true)
    private Object category;

    @JsonSchema(required = true)
    @JsonProperty("child_categories")
    private List<Object> childCategories;

    @Null
    @JsonSchema(required = true)
    private Object city;

    @Null
    @JsonSchema(required = true)
    private Object offer;

    @Null
    @JsonSchema(required = true)
    private Object order;

    @JsonSchema(required = true)
    private List<Object> orders;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("page_meta")
    private Object pageMeta;

    @Null
    @JsonSchema(required = true)
    private Object pagination;

    @JsonSchema(required = true)
    @JsonProperty("parent_categories")
    private List<Object> parentCategories;

    @Null
    @JsonSchema(required = true)
    private Object product;

    @JsonSchema(required = true)
    @JsonProperty("product_properties")
    private List<Object> productProperties;

    @JsonSchema(required = true)
    @JsonProperty("product_taxons")
    private List<Object> productTaxons;

    @JsonSchema(required = true)
    private List<Object> products;

    @JsonSchema(required = true)
    @JsonProperty("promo_badges")
    private List<Object> promoBadges;

    @JsonSchema(required = true)
    @JsonProperty("seo_mode")
    private Boolean seoMode;

    @Null
    @JsonSchema(required = true)
    private Object shipment;

    @JsonSchema(required = true)
    private List<Object> shipments;
}
