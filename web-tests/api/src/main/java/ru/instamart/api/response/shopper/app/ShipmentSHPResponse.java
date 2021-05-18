package ru.instamart.api.response.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.app.ShipmentDataSHP;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentSHPResponse extends BaseResponseObject {
    private ShipmentDataSHP data;
}
