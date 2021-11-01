
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class TenantV1 extends BaseObject {

    @JsonProperty("base_app_link")
    private String baseAppLink;
    private ConfigV1 config;
    @JsonProperty("footer_app_link")
    private String footerAppLink;
    @JsonProperty("home_app_link")
    private String homeAppLink;
    private String hostname;
    private String id;
    @JsonProperty("is_instamart")
    private Boolean isInstamart;
    private String name;
    private Boolean primary;
    @JsonProperty("retailer_locked")
    private Boolean retailerLocked;
    @JsonProperty("site_url")
    private String siteUrl;
    @JsonProperty("smart_banner_app_link")
    private String smartBannerAppLink;
    @JsonProperty("with_home_page")
    private Boolean withHomePage;

    @Getter
    @ToString
    @EqualsAndHashCode(callSuper=false)
    public static class ConfigV1 extends BaseObject{

        @JsonProperty("pickup_enabled")
        private Boolean pickupEnabled;

    }
}
