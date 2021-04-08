package ru.instamart.api.responses.shopper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.OfferDataSHP;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OffersSHPResponse extends BaseResponseObject {
    private List<OfferDataSHP> data = null;
}
