package ru.instamart.api.model.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

/**
 * "bonus_cards": [{
 *       "id": 121,
 *       "number": "1234567890",
 *       "icon": {
 *         "url": "https://https://api.sbermarket.ru/v2/public/loyalty_cards_logos/16/mini/aero-img.png"
 *       }
 *     }]
 */
@EqualsAndHashCode(callSuper = true)
@Data
public final class BonusCardV2 extends BaseObject {
    private Integer id;
    private String number;
    private IconV2 icon;
}
