package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.external_partners.CheckoutV2;
import ru.instamart.api.model.v2.external_partners.MainBannerV2;
import ru.instamart.api.model.v2.external_partners.PrimeCategoryWithSubscriptionV2;
import ru.instamart.api.model.v2.external_partners.PrimeCategoryWithoutSubscriptionV2;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExternalPartnersV2Response extends BaseObject {

    @JsonProperty("main_banner")
    private MainBannerV2 mainBanner;
    @JsonProperty("prime_category_with_subscription")
    private PrimeCategoryWithSubscriptionV2 primeCategoryWithSubscription;
    @JsonProperty("prime_category_without_subscription")
    private PrimeCategoryWithoutSubscriptionV2 primeCategoryWithoutSubscription;
    @JsonProperty("checkout")
    private CheckoutV2 checkout;
}
