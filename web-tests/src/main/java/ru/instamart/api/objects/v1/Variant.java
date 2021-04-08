package ru.instamart.api.objects.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import ru.instamart.api.objects.v2.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class Variant extends BaseObject {
    @JsonProperty(value = "items_per_pack")
    private Integer itemsPerPack;
    private Double weight;
    @JsonProperty(value = "displayed_volume")
    private String displayedVolume;
    private String sku;
    private List<Image> images = null;
}
