package ru.instamart.api.responses.shopper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.ShipmentDataSHP;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentsSHPResponse extends BaseResponseObject {
    private List<ShipmentDataSHP> data = null;
}
