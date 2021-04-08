package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import ru.instamart.api.objects.v1.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class User extends BaseObject {
    private String id;
    private String email;
    @JsonProperty(value = "display_email")
    private String displayEmail;
    @JsonProperty(value = "email_md5")
    private String emailMd5;
    @JsonProperty(value = "first_name")
    private String firstName;
    @JsonProperty(value = "last_name")
    private String lastName;
    private String fullname;
    private String phone;
    @JsonProperty(value = "is_admin")
    private Boolean isAdmin;
    private String uuid;
    @JsonProperty(value = "external_uuid")
    private String externalUuid;
    @JsonProperty(value = "promo_terms_accepted")
    private Boolean promoTermsAccepted;
    @JsonProperty(value = "promo_terms_changed_at")
    private String promoTermsChangedAt;
    @JsonProperty(value = "privacy_terms")
    private Boolean privacyTerms;
    @JsonProperty(value = "shipped_and_paid_orders_count")
    private Integer shippedAndPaidOrdersCount;
    private Boolean b2b;
    @JsonProperty(value = "has_confirmed_phone")
    private Boolean hasConfirmedPhone;
    private List<Role> roles = null;
}
