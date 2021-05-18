package ru.instamart.api.response.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.app.ShipmentDataSHP;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentsSHPResponse extends BaseResponseObject {
    private List<ShipmentDataSHP> data = null;
}
