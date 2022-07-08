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
public class AdminProductV1 extends BaseObject {

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String sku;

    @JsonSchema(required = true)
    private String name;

    @Null
    private String description;

    @Null
    @JsonProperty("short_name")
    private String shortName;

    private String permalink;

    @Null
    private String keywords;

    @JsonSchema(required = true)
    @JsonProperty("created_at")
    private String createdAt;

    @JsonSchema(required = true)
    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("mini_image")
    private String miniImage;

    @JsonSchema(required = true)
    @JsonProperty("is_deleted")
    private Boolean isDeleted;

    @JsonProperty("have_active_offers")
    private Boolean haveActiveOffers;

    @JsonSchema(required = true)
    private List<AdminTaxonV1> taxons;

    @JsonSchema(required = true)
    private List<AdminRetailerV1> retailers;

    @Null
    @JsonSchema(required = true)
    private AdminManufacturerV1 manufacturer;

    @Null
    @JsonSchema(required = true)
    private AdminBrandV1 brand;

    @JsonSchema(required = true)
    private AdminVariantV1 master;

    @JsonSchema(required = true)
    private List<AdminVariantV1> variants;

    @JsonSchema(required = true)
    private List<AdminImageV1> images;

    @JsonProperty("product_properties")
    @JsonSchema(required = true)
    private List<AdminProductPropertyV1> productProperties;

    @JsonProperty("option_types")
    @JsonSchema(required = true)
    private List<AdminOptionTypeV1> optionTypes;
}
