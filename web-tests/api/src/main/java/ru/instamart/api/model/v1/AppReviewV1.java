
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppReviewV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("app_review_app_gallery_link")
    private String appReviewAppGalleryLink;

    @JsonSchema(required = true)
    @JsonProperty("app_review_app_store_link")
    private String appReviewAppStoreLink;

    @JsonSchema(required = true)
    @JsonProperty("app_review_google_play_link")
    private String appReviewGooglePlayLink;

    @JsonSchema(required = true)
    @JsonProperty("app_review_interval_crash")
    private Integer appReviewIntervalCrash;

    @JsonSchema(required = true)
    @JsonProperty("app_review_interval_network_error")
    private Integer appReviewIntervalNetworkError;

    @JsonSchema(required = true)
    @JsonProperty("app_review_interval_request")
    private Integer appReviewIntervalRequest;

    @JsonSchema(required = true)
    @JsonProperty("app_review_mode")
    private String appReviewMode;
}
