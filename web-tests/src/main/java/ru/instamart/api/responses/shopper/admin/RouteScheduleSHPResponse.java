package ru.instamart.api.responses.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.admin.RouteScheduleV1;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class RouteScheduleSHPResponse extends BaseResponseObject {
    private RouteScheduleV1 routeSchedule;
}
