package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.credit_cards.CreditCardV2;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreditCardV2Response extends BaseObject {
	@JsonProperty("credit_card")
    private CreditCardV2 creditCard;
}