package ru.instamart.api.response.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.RouteScheduleV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class RouteScheduleSHPResponse extends BaseResponseObject {
    private RouteScheduleV1 routeSchedule;
}
