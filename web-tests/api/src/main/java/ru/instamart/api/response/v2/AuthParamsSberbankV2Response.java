
package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AuthParamsSberbankV2Response extends BaseResponseObject {
    @JsonSchema(required = true)
    @JsonProperty("authorize_url")
    private String authorizeUrl;

    @JsonSchema(required = true)
    @JsonProperty("client_id")
    private String clientId;

    @JsonSchema(required = true)
    @JsonProperty("response_type")
    private String responseType;

    @JsonSchema(required = true)
    private String state;

    @JsonSchema(required = true)
    private String scope;

    @JsonSchema(required = true)
    private String nonce;

    @JsonSchema(required = true)
    @JsonProperty("client_type")
    private String clientType;
}
