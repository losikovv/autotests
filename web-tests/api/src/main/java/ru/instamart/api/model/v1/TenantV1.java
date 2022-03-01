
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class TenantV1 extends BaseObject {
    @JsonSchema(required = true)
    @JsonProperty("base_app_link")
    private String baseAppLink;

    @JsonSchema(required = true)
    private ConfigV1 config;

    @JsonSchema(required = true)
    @JsonProperty("footer_app_link")
    private String footerAppLink;

    @JsonSchema(required = true)
    @JsonProperty("home_app_link")
    private String homeAppLink;

    @JsonSchema(required = true)
    private String hostname;

    @JsonSchema(required = true)
    private String id;

    @JsonSchema(required = true)
    @JsonProperty("is_instamart")
    private Boolean isInstamart;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private Boolean primary;

    @JsonSchema(required = true)
    @JsonProperty("retailer_locked")
    private Boolean retailerLocked;

    @JsonSchema(required = true)
    @JsonProperty("site_url")
    private String siteUrl;

    @JsonSchema(required = true)
    @JsonProperty("smart_banner_app_link")
    private String smartBannerAppLink;

    @Null
    @JsonProperty("with_home_page")
    private Boolean withHomePage;

    @Getter
    @ToString
    @EqualsAndHashCode(callSuper=false)
    public static class ConfigV1 extends BaseObject{

        @JsonSchema(required = true)
        @JsonProperty("pickup_enabled")
        private Boolean pickupEnabled;

    }
}
