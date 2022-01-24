
package ru.instamart.api.model.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class LossV2 extends BaseObject {

    private List<OfferV2> offers;
    private RetailerV2 retailer;
}
