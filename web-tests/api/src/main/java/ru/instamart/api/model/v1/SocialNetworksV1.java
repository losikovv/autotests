
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SocialNetworksV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("facebook_page")
    private String facebookPage;

    @JsonSchema(required = true)
    @JsonProperty("instagram_page")
    private String instagramPage;

    @JsonSchema(required = true)
    @JsonProperty("ok_page")
    private String okPage;

    @JsonSchema(required = true)
    @JsonProperty("support_telegram")
    private String supportTelegram;

    @JsonSchema(required = true)
    @JsonProperty("support_whatsapp")
    private String supportWhatsapp;

    @JsonSchema(required = true)
    @JsonProperty("twitter_page")
    private String twitterPage;

    @JsonSchema(required = true)
    @JsonProperty("vkontakte_page")
    private String vkontaktePage;
}
