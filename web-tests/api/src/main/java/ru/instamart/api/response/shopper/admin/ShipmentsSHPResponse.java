package ru.instamart.api.response.shopper.admin;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.MetaSHP;
import ru.instamart.api.model.shopper.admin.ShipmentsItemSHP;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentsSHPResponse extends BaseResponseObject {
	private MetaSHP meta;
	private List<ShipmentsItemSHP> shipments;
}