package ru.instamart.api.helpers;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.objects.shopper.admin.RouteScheduleV1;
import ru.instamart.api.requests.shopper.admin.ShopperAdminRequest;
import ru.instamart.api.responses.shopper.admin.RouteSchedulesSHPResponse;

import java.util.List;

@Slf4j
public class ShopperAdminApiHelper {

    public void deleteRouteScheduleIfExists(Integer sid, String date) {
        List<RouteScheduleV1> routeSchedules = getRouteSchedules(sid, date);
        if (!routeSchedules.isEmpty()) {
            log.info("Удаляем расписание магазина на день");
            final int routeScheduleId = routeSchedules.get(0).getId();
            Response response = ShopperAdminRequest.RouteSchedules.DELETE(routeScheduleId);
            response.then().statusCode(200);
        }
    }

    public List<RouteScheduleV1> getRouteSchedules(Integer sid, String date) {
        log.info("Получаем расписание магазина на день");
        Response response = ShopperAdminRequest.RouteSchedules.GET(sid, date);
        response.then().statusCode(200);
        return response.as(RouteSchedulesSHPResponse.class).getRouteSchedules();
    }
}
