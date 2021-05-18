package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.BonusCardV2;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public final class BonusCardsV2Response extends BaseObject {

    @JsonProperty(value = "bonus_cards")
    private List<BonusCardV2> bonusCards;
}
