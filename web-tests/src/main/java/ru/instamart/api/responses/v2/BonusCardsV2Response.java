package ru.instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;
import ru.instamart.api.objects.v2.BonusV2Cards;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public final class BonusCardsV2Response extends BaseObject {

    @JsonProperty(value = "bonus_cards")
    private List<BonusV2Cards> bonusCards;
}
