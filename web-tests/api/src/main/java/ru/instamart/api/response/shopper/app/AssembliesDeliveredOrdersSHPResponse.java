package ru.instamart.api.response.shopper.app;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;
import ru.instamart.api.model.shopper.app.DataItem;

@Data
@EqualsAndHashCode(callSuper = false)
public class AssembliesDeliveredOrdersSHPResponse extends BaseResponseObject {
	private List<DataItem> data;
}