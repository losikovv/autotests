package ru.instamart.api.model.v2.app_config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class AppReviewV2 extends BaseObject {
    private Object mode;
    @JsonProperty(value = "review_request_interval")
    private Object reviewRequestInterval;
    @JsonProperty(value = "review_interval_crash")
    private Object reviewIntervalCrash;
    @JsonProperty(value = "review_interval_network_error")
    private Object reviewIntervalNetworkError;
    @JsonProperty(value = "app_store_review_link")
    private String appStoreReviewLink;
    @JsonProperty(value = "google_play_review_link")
    private String googlePlayReviewLink;
    @JsonProperty(value = "app_gallery_review_link")
    private String appGalleryReviewLink;
}
