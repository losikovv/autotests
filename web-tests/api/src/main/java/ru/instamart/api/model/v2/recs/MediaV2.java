package ru.instamart.api.model.v2.recs;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class MediaV2 extends BaseResponseObject {
    private String originalPrice;
    private List<ImagesItemV2> images;
    private String discountEndsAt;
    private String originalUnitPrice;
    private String humanVolume;
    private String unitPrice;
    private Integer gramsPerUnit;
    private String price;
    private String name;
    private String priceType;
    private Integer itemsPerPack;
    private Integer id;
    private String sku;
    private String permalink;
}