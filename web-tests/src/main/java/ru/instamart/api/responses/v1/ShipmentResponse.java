package ru.instamart.api.responses.v1;

import ru.instamart.api.objects.v1.Shipment;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentResponse extends BaseResponseObject {
    private Shipment shipment;
}
