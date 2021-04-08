package ru.instamart.api.responses.shopper;

import ru.instamart.api.objects.shopper.Offer;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentStocksResponse extends BaseResponseObject {
    private List<Offer> offers = null;
}
