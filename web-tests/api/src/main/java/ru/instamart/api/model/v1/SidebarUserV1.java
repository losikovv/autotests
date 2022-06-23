package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v1.b2b.CompanyDocumentV1;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SidebarUserV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("tenant_id")
    private String tenantId;

    @JsonProperty("has_spree_api_key")
    private boolean hasSpreeApiKey;

    @Null
    private String firstname;

    @JsonProperty("company_documents")
    private List<CompanyDocumentV1> companyDocuments;

    @Null
    @JsonProperty("promo_terms_accepted")
    private boolean promoTermsAccepted;

    @JsonProperty("new_admin_roles_enabled")
    private boolean newAdminRolesEnabled;

    @JsonProperty("sber_id_account_merges_from")
    private List<SberIdAccountMergesFromV1> sberIdAccountMergesFrom;

    @JsonSchema(required = true)
    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("role_ids")
    private List<Integer> roleIds;

    @JsonProperty("contact_email")
    private String contactEmail;

    @JsonProperty("user_phone_verification_actions")
    private List<UserPhoneVerificationActionsV1> userPhoneVerificationActions;

    @JsonSchema(required = true)
    private boolean b2b;

    @JsonSchema(required = true)
    @JsonProperty("updated_at")
    private String updatedAt;

    @Null
    @JsonProperty("social_profile")
    private String socialProfile;

    @JsonProperty("preferred_card_payment_method")
    private String preferredCardPaymentMethod;

    @JsonProperty("user_locks")
    private List<UserLocksV1> userLocks;

    @JsonSchema(required = true)
    private int id;

    @JsonSchema(required = true)
    private String email;

    @Null
    @JsonProperty("phone_token_number")
    private String phoneTokenNumber;

    @JsonProperty("access_locked")
    private boolean accessLocked;

    @Null
    private String lastname;

    @JsonProperty("active_external_partners_subscriptions")
    private List<ActiveExternalPartnersSubscriptionsV1> activeExternalPartnersSubscriptions;

    @JsonProperty("credit_cards")
    private List<CreditCardV1> creditCards;

    private UserConfigV1 config;

    @Null
    @JsonProperty("customer_comment")
    private String customerComment;
}