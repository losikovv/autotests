package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.BonusCardV2;

@EqualsAndHashCode(callSuper = true)
@Data
public final class BonusCardV2Response extends BaseObject {
    @JsonProperty(value = "bonus_card")
    private BonusCardV2 bonusCard;
}
