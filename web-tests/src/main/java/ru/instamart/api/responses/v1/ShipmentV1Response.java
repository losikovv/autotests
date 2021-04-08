package ru.instamart.api.responses.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v1.ShipmentV1;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentV1Response extends BaseResponseObject {
    private ShipmentV1 shipment;
}
