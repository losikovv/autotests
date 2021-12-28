
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
public class ProductV1 extends BaseObject {

    @Null
    private Object brand;

    @JsonProperty("canonical_permalink")
    @JsonSchema(required = true)
    private String canonicalPermalink;

    @Null
    @JsonSchema(required = true)
    private String description;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("description_original")
    private String descriptionOriginal;

    @JsonSchema(required = true)
    @JsonProperty("human_volume")
    private String humanVolume;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private List<ImageV1> images;

    @JsonSchema(required = true)
    @JsonProperty("is_alcohol")
    private Boolean isAlcohol;

    @JsonSchema(required = true)
    @JsonProperty("items_per_pack")
    private Long itemsPerPack;

    @Null
    @JsonProperty("license_info")
    private Object licenseInfo;

    @Null
    private Object manufacturer;

    @Null
    @JsonProperty("manufacturing_country")
    private Object manufacturingCountry;

    @JsonSchema(required = true)
    private String name;

    @Null
    private Object offer;

    @JsonSchema(required = true)
    private String permalink;

    @JsonProperty("promo_badge_ids")
    private List<Object> promoBadgeIds;

    @JsonSchema(required = true)
    private String sku;
}
