
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class GeneralSettingsV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("app_analytics")
    private AppAnalyticsV1 appAnalytics;

    @JsonSchema(required = true)
    @JsonProperty("app_review")
    private AppReviewV1 appReview;

    @JsonSchema(required = true)
    @JsonProperty("app_versions")
    private AppVersionsV1 appVersions;

    @JsonSchema(required = true)
    @JsonProperty("app_web_view_whitelist")
    private AppWebViewWhitelistV1 appWebViewWhitelist;

    @JsonSchema(required = true)
    private String currency;

    @JsonSchema(required = true)
    private EmailsV1 emails;

    @JsonSchema(required = true)
    @JsonProperty("mobile_links")
    private MobileLinksV1 mobileLinks;

    @JsonSchema(required = true)
    private SeoV1 seo;

    @JsonSchema(required = true)
    @JsonProperty("social_networks")
    private SocialNetworksV1 socialNetworks;
}
