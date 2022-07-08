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
public class AdminVariantV1 extends BaseObject {

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String sku;

    @Null
    @JsonSchema(required = true)
    private Double weight;

    @Null
    @JsonSchema(required = true)
    private Double height;

    @Null
    @JsonSchema(required = true)
    private Double width;

    @Null
    @JsonSchema(required = true)
    private Double depth;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("deleted_at")
    private String deletedAt;

    @JsonSchema(required = true)
    @JsonProperty("is_master")
    private Boolean isMaster;

    @JsonSchema(required = true)
    private Integer position;

    @JsonSchema(required = true)
    private Double volume;

    @JsonSchema(required = true)
    @JsonProperty("volume_type")
    private String volumeType;

    @JsonSchema(required = true)
    @JsonProperty("items_per_pack")
    private Integer itemsPerPack;

    @JsonSchema(required = true)
    @JsonProperty("track_inventory")
    private Boolean trackInventory;

    @JsonSchema(required = true)
    @JsonProperty("option_values")
    private List<AdminOptionValueV1> optionValues;

}
