package ru.instamart.api.model.v2.simple_ads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class MediaV2 extends BaseResponseObject {
    @Singular
    private List<AssetV2> assets;
    @JsonProperty("native_ad_id")
    private String nativeAdId;
    @JsonProperty("native_ad_type_id")
    private String nativeAdTypeId;
    @JsonProperty("campaign_id")
    private String campaignId;
}