
package ru.instamart.api.model.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PromoDiscountV2 extends BaseObject {

    private DescriptionV2 description;
    private String promocode;
    private String text;
}
