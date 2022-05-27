
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ApiClientV1 extends BaseObject {

    @Null
    @JsonSchema(required = true)
    @JsonProperty("basic_auth")
    private String basicAuth;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("card_payment_method")
    private String cardPaymentMethod;

    @JsonSchema(required = true)
    @JsonProperty("client_id")
    private String clientId;

    @JsonSchema(required = true)
    @JsonProperty("custom_prices")
    private Boolean customPrices;

    @JsonSchema(required = true)
    @JsonProperty("custom_promo")
    private Boolean customPromo;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    @JsonProperty("retailer_ids")
    private List<Long> retailerIds;

    @Null
    @JsonSchema(required = true)
    private String secret;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("show_replacements")
    private Boolean showReplacements;

    @JsonSchema(required = true)
    @JsonProperty("skip_offer_limit_validation")
    private Boolean skipOfferLimitValidation;

    @JsonSchema(required = true)
    @JsonProperty("sku_kind")
    private String skuKind;

    @JsonSchema(required = true)
    @JsonProperty("tenant_id")
    private String tenantId;

    @JsonSchema(required = true)
    private Boolean verifiable;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("webhook_auth_token")
    private String webhookAuthToken;
}
