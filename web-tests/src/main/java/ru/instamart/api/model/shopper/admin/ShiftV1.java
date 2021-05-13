package ru.instamart.api.model.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShiftV1 extends BaseObject {
    private Integer id;
    private String date;
    private Integer storeId;
    private Integer tariffId;
    private Integer position;
    private TariffV1 tariff;
    private List<ShiftAssignmentV1> shiftAssignments = null;
}
