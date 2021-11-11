
package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ServicesV2 extends BaseObject {

    private DescriptionV2 description;
    @JsonProperty("discount_type")
    private String discountType;
    private Long id;
    private String kind;
    private String link;
    @JsonProperty("logo_url")
    private String logoUrl;
    private String name;
    @JsonProperty("promo_discount")
    private PromoDiscountV2 promoDiscount;
    private SubscriptionV2 subscription;
    private String text;
}
