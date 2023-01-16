package ru.instamart.test.api.on_demand.orderservice;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.shopper.admin.ShopperAdminRequest;
import ru.instamart.api.response.shopper.admin.RouteRoutingSettings;
import ru.instamart.jdbc.dao.orders_service.publicScheme.PlaceSettingsDao;
import ru.instamart.jdbc.entity.order_service.publicScheme.PlaceSettingsEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import io.qameta.allure.TmsLink;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.Group.API_SHOPPER_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("On Demand")
@Feature("DISPATCH")
public class RouteSettingsTest extends RestBase {

    @BeforeMethod(alwaysRun = true)
    public void auth() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdminOld());
    }

    @TmsLink("165")
    @Story("Routing settings")
    @Test(description = "Установка маршрутизатизации на диспатч",
            groups = {API_SHOPPER_REGRESS, "dispatch-orderservice-smoke"})
    public void patchRoutingSettingsDispatch() {
        String schedule_type = "dispatch";

        final Response response = ShopperAdminRequest.RoutingSettings.PATCH(EnvironmentProperties.DEFAULT_SID, schedule_type);
        RouteRoutingSettings parameters = response.as(RouteRoutingSettings.class);
        final var place_uuid = parameters.getStore().getUuid();

        PlaceSettingsEntity placeSettingsEntity = PlaceSettingsDao.INSTANCE.getScheduleType(place_uuid);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, RouteRoutingSettings.class);
        Allure.step("Проверка данных магазина", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(parameters.getStore().getId(), EnvironmentProperties.DEFAULT_SID, "Вернулся неверный ID магазина");
            softAssert.assertEquals(parameters.getStore().getScheduleType(), schedule_type, "Вернулись не верные настройки маршрутизации");
            compareTwoObjects(parameters.getStore().getScheduleType(), placeSettingsEntity.getScheduleType(), softAssert);
            softAssert.assertAll();
        });
    }

    @TmsLink("165")
    @Story("Routing settings")
    @Test(description = "Установка маршрутизации на револьверную",
            groups = {API_SHOPPER_REGRESS, "dispatch-orderservice-smoke"})
    public void patchRoutingSettingsList() {
        String schedule_type = "list";

        final var response = ShopperAdminRequest.RoutingSettings.PATCH(EnvironmentProperties.DEFAULT_SID, schedule_type);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RouteRoutingSettings.class);
        final var parameters = response.as(RouteRoutingSettings.class);
        final var place_uuid = parameters.getStore().getUuid();
        final var placeSettingsEntity = PlaceSettingsDao.INSTANCE.getScheduleType(place_uuid);
        Allure.step("Проверка маршрутизации", () -> {
            final SoftAssert softAssert = new SoftAssert();
            assertEquals(parameters.getStore().getId(), EnvironmentProperties.DEFAULT_SID, "Вернулся неверный ID магазина");
            assertEquals(parameters.getStore().getScheduleType(), schedule_type, "Вернулись не верные настройки маршрутизации");
            compareTwoObjects(parameters.getStore().getScheduleType(), placeSettingsEntity.getScheduleType());
            softAssert.assertAll();
        });
    }
}
