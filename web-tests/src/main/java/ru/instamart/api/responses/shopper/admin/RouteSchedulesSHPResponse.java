package ru.instamart.api.responses.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.admin.MetaV1;
import ru.instamart.api.objects.shopper.admin.RoutesScheduleV1;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class RouteSchedulesSHPResponse extends BaseResponseObject {
    private List<RoutesScheduleV1> routeSchedules = null;
    private MetaV1 meta;
}
