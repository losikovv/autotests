package ru.instamart.api.objects.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;
import ru.instamart.api.objects.v2.ImageV2;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class VariantV1 extends BaseObject {
    @JsonProperty(value = "items_per_pack")
    private Integer itemsPerPack;
    private Double weight;
    @JsonProperty(value = "displayed_volume")
    private String displayedVolume;
    private String sku;
    private List<ImageV2> images = null;
}
