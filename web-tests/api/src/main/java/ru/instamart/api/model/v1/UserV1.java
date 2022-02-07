package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.ProfileConfigV2;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserV1 extends BaseObject {
    @JsonSchema(required = true)
    private Long id;
    @JsonSchema(required = true)
    private String email;
    @JsonSchema(required = true)
    @Null
    @JsonProperty(value = "display_email")
    private String displayEmail;
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
    @JsonSchema(required = true)
    private String phone;
    @JsonProperty(value = "is_admin")
    private Boolean isAdmin;
    @JsonSchema(required = true)
    private String uuid;
    @JsonProperty(value = "external_uuid")
    private String externalUuid;
    @Null
    @JsonProperty(value = "promo_terms_accepted")
    private Boolean promoTermsAccepted;
    @Null
    @JsonProperty(value = "promo_terms_changed_at")
    private String promoTermsChangedAt;
    @JsonProperty(value = "privacy_terms")
    private Boolean privacyTerms;
    @JsonProperty(value = "shipped_and_paid_orders_count")
    private Integer shippedAndPaidOrdersCount;
    private Boolean b2b;
    @JsonProperty(value = "has_confirmed_phone")
    private Boolean hasConfirmedPhone;
    private List<RoleV1> roles = null;
    @JsonProperty("completed_orders_count")
    private Integer completedOrdersCount;
    @JsonProperty("current_phone")
    private String currentPhone;
    private ProfileConfigV2 config;
    @JsonProperty("attached_services")
    private List<String> attachedServices;
}
