package ru.instamart.api.objects.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

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
public final class BonusV2Cards extends BaseObject {

    private int id;
    private long number;
    private IconV2 icon;
}
