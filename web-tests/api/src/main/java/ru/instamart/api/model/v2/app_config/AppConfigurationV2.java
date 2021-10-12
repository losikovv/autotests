package ru.instamart.api.model.v2.app_config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class AppConfigurationV2 extends BaseObject {
    private AcquiringV2 acquiring;
    private AuthenticationV2 authentication;
    @JsonProperty(value = "privacy_terms_link")
    private String privacyTermsLink;
    private ContactsV2 contacts;
    @JsonProperty(value = "about_links")
    private AboutLinksV2 aboutLinks;
    @JsonProperty(value = "appReview")
    private AppReviewV2 app_review;
}
