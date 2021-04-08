package ru.instamart.api.responses.shopper;

import ru.instamart.api.responses.BaseResponseObject;
import ru.instamart.api.objects.shopper.OfferData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OffersResponse extends BaseResponseObject {
    private List<OfferData> data = null;
}
