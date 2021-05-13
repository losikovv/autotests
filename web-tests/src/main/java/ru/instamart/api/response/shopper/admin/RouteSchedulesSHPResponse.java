package ru.instamart.api.response.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.MetaV1;
import ru.instamart.api.model.shopper.admin.RouteScheduleV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class RouteSchedulesSHPResponse extends BaseResponseObject {
    private List<RouteScheduleV1> routeSchedules = null;
    private MetaV1 meta;
}
