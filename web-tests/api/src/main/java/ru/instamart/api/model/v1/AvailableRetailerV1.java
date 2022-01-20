
package ru.instamart.api.model.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AvailableRetailerV1 extends BaseObject {

    private String retailerName;

    private List<Long> shipmentIds;
}
