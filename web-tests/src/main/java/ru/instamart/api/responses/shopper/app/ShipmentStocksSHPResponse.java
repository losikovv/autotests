package ru.instamart.api.responses.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.app.OfferSHP;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentStocksSHPResponse extends BaseResponseObject {
    private List<OfferSHP> offers = null;
}
