
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class FeatureSettingsV1 extends BaseObject {

    @JsonProperty("apple_pay_admin_settings")
    private Boolean applePayAdminSettings;
    @JsonProperty("brands_is_app_category_banner_enabled")
    private Boolean brandsIsAppCategoryBannerEnabled;
    @JsonProperty("brands_is_new_promo_slider_visible")
    private Boolean brandsIsNewPromoSliderVisible;
    @JsonProperty("brands_is_own_ml_cart_recommendations_enabled")
    private Boolean brandsIsOwnMlCartRecommendationsEnabled;
    @JsonProperty("dc_send_nutrinional_value_in_products")
    private Boolean dcSendNutrinionalValueInProducts;
    @JsonProperty("enable_products_filter_by_countries")
    private Boolean enableProductsFilterByCountries;
    @JsonProperty("google_pay_admin_settings")
    private Boolean googlePayAdminSettings;
    @JsonProperty("is_new_gen_banner_on_main_enabled")
    private Boolean isNewGenBannerOnMainEnabled;
    @JsonProperty("is_recommendation_on_main_enabled")
    private Boolean isRecommendationOnMainEnabled;
    @JsonProperty("mobile_new_retailer_cards_enabled")
    private Boolean mobileNewRetailerCardsEnabled;
    @JsonProperty("mobile_phone_auth")
    private Boolean mobilePhoneAuth;
    @JsonProperty("on_demand_sigma")
    private Integer onDemandSigma;
    @JsonProperty("partners_map_min_zoom")
    private Integer partnersMapMinZoom;
    @JsonProperty("qa_mode_phone_auth_enabled")
    private Boolean qaModePhoneAuthEnabled;
    @JsonProperty("sber_gateway_enabled")
    private Boolean sberGatewayEnabled;
    @JsonProperty("sber_gateway_enabled_for_new_users_from")
    private String sberGatewayEnabledForNewUsersFrom;
    @JsonProperty("sber_id_attach")
    private Boolean sberIdAttach;
    @JsonProperty("sber_id_auth_by_verified_phone")
    private Boolean sberIdAuthByVerifiedPhone;
    @JsonProperty("sber_pay_mobile")
    private Boolean sberPayMobile;
    @JsonProperty("sberbank_business_cash_hold_certificate_center_id")
    private String sberbankBusinessCashHoldCertificateCenterId;
    @JsonProperty("sberbank_business_cash_hold_company_name")
    private String sberbankBusinessCashHoldCompanyName;
    @JsonProperty("sberbank_business_cash_hold_email")
    private String sberbankBusinessCashHoldEmail;
    @JsonProperty("sberbank_business_cash_hold_fio")
    private String sberbankBusinessCashHoldFio;
    @JsonProperty("sberbank_business_cash_hold_initial_cert_id")
    private String sberbankBusinessCashHoldInitialCertId;
    @JsonProperty("sberbank_business_cash_hold_organizational_unit")
    private String sberbankBusinessCashHoldOrganizationalUnit;
    @JsonProperty("sberbank_business_cash_hold_scope")
    private String sberbankBusinessCashHoldScope;
    @JsonProperty("sberbank_business_cash_hold_user_position")
    private String sberbankBusinessCashHoldUserPosition;
    @JsonProperty("sberbank_business_scope")
    private String sberbankBusinessScope;
    @JsonProperty("sberprime_promo_enabled")
    private Boolean sberprimePromoEnabled;
    @JsonProperty("show_delivery_forecast_text")
    private Boolean showDeliveryForecastText;
    @JsonProperty("show_delivery_price_for_new_retailer_cards")
    private Boolean showDeliveryPriceForNewRetailerCards;
    @JsonProperty("split_checkout_by_retailers_on_mobile")
    private Boolean splitCheckoutByRetailersOnMobile;
    @JsonProperty("validate_admin_cart")
    private Boolean validateAdminCart;
}
