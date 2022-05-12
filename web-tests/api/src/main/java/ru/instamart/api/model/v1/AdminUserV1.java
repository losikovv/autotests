
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false) // TODO: найти/создать нужные объекты для Object
public class AdminUserV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("access_locked")
    private Boolean accessLocked;

    @JsonSchema(required = true)
    @JsonProperty("active_external_partners_subscriptions")
    private List<Object> activeExternalPartnersSubscriptions;

    @JsonSchema(required = true)
    private Boolean b2b;

    @JsonSchema(required = true)
    @JsonProperty("company_documents")
    private List<Object> companyDocuments;

    @JsonSchema(required = true)
    private UserConfigV1 config;

    @JsonSchema(required = true)
    @JsonProperty("contact_email")
    private String contactEmail;

    @JsonSchema(required = true)
    @JsonProperty("created_at")
    private String createdAt;

    @JsonSchema(required = true)
    @JsonProperty("credit_cards")
    private List<Object> creditCards;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("customer_comment")
    private String customerComment;

    @JsonSchema(required = true)
    private String email;

    @Null
    @JsonSchema(required = true)
    private String firstname;

    @JsonSchema(required = true)
    @JsonProperty("has_spree_api_key")
    private Boolean hasSpreeApiKey;

    @JsonSchema(required = true)
    private Long id;

    @Null
    @JsonSchema(required = true)
    private String lastname;

    @JsonSchema(required = true)
    @JsonProperty("new_admin_roles_enabled")
    private Boolean newAdminRolesEnabled;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("phone_token_number")
    private String phoneTokenNumber;

    @JsonSchema(required = true)
    @JsonProperty("preferred_card_payment_method")
    private String preferredCardPaymentMethod;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("promo_terms_accepted")
    private Boolean promoTermsAccepted;

    @JsonSchema(required = true)
    @JsonProperty("role_ids")
    private List<Long> roleIds;

    @JsonSchema(required = true)
    @JsonProperty("sber_id_account_merges_from")
    private List<Object> sberIdAccountMergesFrom;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("social_profile")
    private String socialProfile;

    @JsonSchema(required = true)
    @JsonProperty("tenant_id")
    private String tenantId;

    @JsonSchema(required = true)
    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonSchema(required = true)
    @JsonProperty("user_locks")
    private List<Object> userLocks;

    @JsonSchema(required = true)
    @JsonProperty("user_phone_verification_actions")
    private List<Object> userPhoneVerificationActions;
}
