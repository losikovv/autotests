package ru.instamart.api.responses.shopper;

import ru.instamart.api.responses.BaseResponseObject;
import ru.instamart.api.objects.shopper.ShipmentData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentsResponse extends BaseResponseObject {
    private List<ShipmentData> data = null;
}
