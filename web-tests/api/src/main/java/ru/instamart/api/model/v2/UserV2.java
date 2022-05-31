package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v1.RoleV1;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserV2 extends BaseObject {
    private String id;
    private String email;
    @Null
    @JsonProperty(value = "display_email")
    private String displayEmail;
    @Null
    @JsonProperty(value = "email_md5")
    private String emailMd5;
    @Null
    @JsonProperty(value = "first_name")
    private String firstName;
    @Null
    @JsonProperty(value = "last_name")
    private String lastName;
    @Null
    private String fullname;
    @Null
    private String phone;
    @Null
    @JsonProperty(value = "is_admin")
    private Boolean isAdmin;
    @Null
    private String uuid;
    @Null
    @JsonProperty(value = "external_uuid")
    private String externalUuid;
    @JsonSchema(required = true)
    @JsonProperty(value = "promo_terms_accepted")
    private Boolean promoTermsAccepted;
    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "promo_terms_changed_at")
    private String promoTermsChangedAt;
    @JsonSchema(required = true)
    @JsonProperty(value = "privacy_terms")
    private Boolean privacyTerms;
    @Null
    @JsonProperty(value = "shipped_and_paid_orders_count")
    private Integer shippedAndPaidOrdersCount;
    @JsonSchema(required = true)
    private Boolean b2b;
    @JsonProperty(value = "has_confirmed_phone")
    private Boolean hasConfirmedPhone;
    @Null
    private List<RoleV1> roles = null;
    @JsonSchema(required = true)
    @JsonProperty("completed_orders_count")
    private Integer completedOrdersCount;
    @JsonSchema(required = true)
    @JsonProperty("current_phone")
    private String currentPhone;
    @JsonSchema(required = true)
    private ProfileConfigV2 config;
    @Null
    @JsonProperty("attached_services")
    private List<String> attachedServices;
}
