package ru.instamart.api.model.v2.recs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class ImagesItemV2 extends BaseResponseObject {
    private String smallUrl;
    private String productUrl;
    private String originalUrl;
    private String previewUrl;
    private Integer position;
    private String miniUrl;
}