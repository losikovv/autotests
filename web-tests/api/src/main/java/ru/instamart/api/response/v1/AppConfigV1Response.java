
package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.SupportTimeV1;
import ru.instamart.api.model.v1.TenantV1;
import ru.instamart.api.model.v1.UserV1;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppConfigV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("admin_sentry_dsn")
    private String adminSentryDsn;

    @JsonSchema(required = true)
    @JsonProperty("cloud_payments_public_id")
    private String cloudPaymentsPublicId;

    @JsonSchema(required = true)
    @JsonProperty("company_phone")
    private String companyPhone;

    @JsonSchema(required = true)
    @JsonProperty("company_phone_formatted")
    private String companyPhoneFormatted;

    @JsonSchema(required = true)
    private String env;

    @JsonSchema(required = true)
    @JsonProperty("support_time")
    private SupportTimeV1 supportTime;

    @JsonSchema(required = true)
    private TenantV1 tenant;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("ui_features")
    private Object uiFeatures;

    @Null
    @JsonSchema(required = true)
    private UserV1 user;

    @JsonSchema(required = true)
    @JsonProperty("yandex_maps_api_key")
    private String yandexMapsApiKey;
}
