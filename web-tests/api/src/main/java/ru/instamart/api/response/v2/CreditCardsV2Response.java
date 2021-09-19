package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.enums.v2.CreditCardV2;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public final class CreditCardsV2Response extends BaseObject {

    @JsonProperty("credit_cards")
    private List<CreditCardV2> creditCards;
    }

