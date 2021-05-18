package ru.instamart.api.helper;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.model.shopper.admin.RouteScheduleV1;
import ru.instamart.api.request.shopper.admin.ShopperAdminRequest;
import ru.instamart.api.response.shopper.admin.RouteSchedulesSHPResponse;

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
