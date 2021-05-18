package ru.instamart.api.model.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class RouteScheduleV1 extends BaseObject {
    private Integer id;
    private String date;
    private String status;
    private String warningMessage;
    private String scheduleType;
    private String scheduleTypeHumanName;
    private List<RouteV1> routes = null;
    private List<UnscheduledShipmentV1> unscheduledShipments = null;
    private StoreV1 store;
}
