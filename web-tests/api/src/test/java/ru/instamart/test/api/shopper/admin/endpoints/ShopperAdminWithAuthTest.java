package ru.instamart.test.api.shopper.admin.endpoints;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.checkpoint.BaseApiCheckpoints;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.shopper.admin.*;
import ru.instamart.api.request.shopper.admin.ShopperAdminRequest;
import ru.instamart.api.request.shopper.admin.ShopperAdminRequest.PutEstimatorSettings;
import ru.instamart.api.response.shopper.admin.*;
import ru.instamart.jdbc.dao.orders_service.PlaceSettingsDao;
import ru.instamart.jdbc.entity.order_service.PlaceSettingsEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import io.qameta.allure.TmsLink;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.Group.API_SHOPPER_PROD;
import static ru.instamart.api.Group.API_SHOPPER_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.kraken.helper.DateTimeHelper.getDateFromMSK;
import static ru.instamart.kraken.util.TimeUtil.getPastDateWithoutTime;

@Epic("Shopper Admin Panel API")
@Feature("Endpoints")
public class ShopperAdminWithAuthTest extends RestBase {
    private final Integer sid = EnvironmentProperties.DEFAULT_SHOPPER_SID;
    private final Integer shopperId = 1;
    private final Integer driverId = 2;
    private final String routeScheduleStatus = "disabled";
    private final String shopperStatus = "disabled";
    private final String today = getDateFromMSK();
    private Integer routeId;
    private Integer routeScheduleId;
    private Integer tariffId;
    private Integer shiftId;
    private Integer shiftAssignmentId;

    @BeforeMethod(alwaysRun = true)
    public void auth() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdminOld());
    }

    @TmsLink("23")
    @Test(description = "Список сотрудников",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getShoppers200() {
        Response response = ShopperAdminRequest.Shoppers.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShoppersSHPResponse.class);
    }

    @TmsLink("24")
    @Test(description = "Список магазинов",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getStores200() {
        Response response = ShopperAdminRequest.Stores.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoresSHPResponse.class);
    }

    @TmsLink("25")
    @Test(description = "Список расписаний",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getRouteSchedules200() {
        Response response = ShopperAdminRequest.RouteSchedules.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RouteSchedulesSHPResponse.class);
    }

    @TmsLink("26")
    @Test(description = "Список операционных зон",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getOperationalZones200() {
        Response response = ShopperAdminRequest.OperationalZones.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OperationalZonesSHPResponse.class);
    }

    @TmsLink("27")
    @Test(description = "Список ретейлеров",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getRetailers200() {
        Response response = ShopperAdminRequest.Retailers.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersSHPResponse.class);
    }

    @TmsLink("28")
    @Test(description = "Список доставок",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getShipments200() {
        Response response = ShopperAdminRequest.Shipments.GET(sid, getPastDateWithoutTime(1L));
        checkStatusCode200(response);
    }

    @TmsLink("29")
    @Test(description = "Список смен",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getShifts200() {
        Response response = ShopperAdminRequest.Shifts.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShiftsSHPResponse.class);
    }

    @TmsLink("31")
    @Test(description = "Список тарифов",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getTariffs200() {
        Response response = ShopperAdminRequest.Tariffs.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, TariffsSHPResponse.class);
    }

    @TmsLink("84")
    @Test(description = "Создание расписания на день",
            groups = {API_SHOPPER_REGRESS})
    public void postRouteSchedules200() {
        shopperAdmin.deleteRouteScheduleIfExists(sid, today);

        Response response = ShopperAdminRequest.RouteSchedules.POST(sid, today, routeScheduleStatus);

        checkStatusCode200(response);

        RouteScheduleV1 routeSchedule = response.as(RouteScheduleSHPResponse.class).getRouteSchedule();
        routeScheduleId = routeSchedule.getId();

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(sid, routeSchedule.getStore().getId(), "Сид магазина не совпадает");
        softAssert.assertEquals(today, routeSchedule.getDate(), "Дата в расписании не совпадает");
        softAssert.assertEquals(routeScheduleStatus, routeSchedule.getStatus(), "Статус расписания не совпадает");
        softAssert.assertAll();
    }

    @TmsLink("85")
    @Test(description = "Создание маршрута",
            groups = {API_SHOPPER_REGRESS},
            dependsOnMethods = "postRouteSchedules200")
    public void postRoutes200() {
        Response response = ShopperAdminRequest.Routes.POST(routeScheduleId, driverId);

        checkStatusCode200(response);

        RouteV1 route = response.as(RouteSHPResponse.class).getRoute();
        routeId = route.getId();

        assertEquals(driverId, route.getDriver().getId(), "ИД водителя не совпадает");
    }

    @TmsLink("30")
    @Test(description = "Получение инфы о маршруте",
            groups = {API_SHOPPER_REGRESS},
            dependsOnMethods = "postRoutes200")
    public void getRoute200() {
        Response response = ShopperAdminRequest.Routes.GET(routeId);

        checkStatusCode200(response);

        assertEquals(routeId, response.as(RouteSHPResponse.class).getRoute().getId(), "ИД маршрута не совпадает");
    }

    @TmsLink("32")
    @Test(description = "Изменение расписания",
            groups = {API_SHOPPER_REGRESS},
            dependsOnMethods = "postRouteSchedules200")
    public void patchRouteSchedule200() {
        Response response = ShopperAdminRequest.RouteSchedules.PATCH(routeScheduleId, routeScheduleStatus);

        checkStatusCode200(response);

        RouteScheduleV1 routeSchedule = response.as(RouteScheduleSHPResponse.class).getRouteSchedule();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(routeScheduleId, routeSchedule.getId(), "ИД расписания водителя не совпадает");
        softAssert.assertEquals(routeScheduleStatus, routeSchedule.getStatus(), "Статус расписания подителя не совпадает");
        softAssert.assertAll();
    }

    @TmsLink("37")
    @Test(description = "Получение всех доступных ролей",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getRoles200() {
        Response response = ShopperAdminRequest.Roles.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RolesSHPResponse.class);
    }

    @TmsLink("86")
    @Test(description = "Создание тарифа",
            groups = {API_SHOPPER_REGRESS})
    public void postTariffs200() {
        Response response = ShopperAdminRequest.Tariffs.POST();
        checkStatusCode200(response);

        TariffV1 tariff = response.as(TariffSHPResponse.class).getTariff();
        tariffId = tariff.getId();
    }

    @TmsLink("88")
    @Test(description = "Создание смены",
            groups = {API_SHOPPER_REGRESS},
            dependsOnMethods = "postTariffs200")
    public void postShifts200() {
        Response response = ShopperAdminRequest.Shifts.POST(today, 0, sid, tariffId);
        checkStatusCode200(response);

        ShiftV1 shift = response.as(ShiftSHPResponse.class).getShift();
        shiftId = shift.getId();

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(today, shift.getDate(), "Дата смены не совпадает");
        softAssert.assertEquals(sid, shift.getStoreId(), "SID не совпадает");
        softAssert.assertEquals(tariffId, shift.getTariffId(), "Тариф смены не верен");
        softAssert.assertAll();
    }

    @TmsLink("33")
    @Test(description = "Создание назначения в смену",
            groups = {API_SHOPPER_REGRESS},
            dependsOnMethods = "postShifts200")
    public void postShiftAssignments200() {
        String startsAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
        String endsAt = LocalDateTime.now().plusHours(8).truncatedTo(ChronoUnit.SECONDS).toString();

        Response response = ShopperAdminRequest.ShiftAssignments.POST(
                shiftId,
                shopperId,
                startsAt,
                endsAt);

        checkStatusCode200(response);

        ShiftAssignmentV1 shiftAssignment = response.as(ShiftAssignmentSHPResponse.class).getShiftAssignment();
        shiftAssignmentId = shiftAssignment.getId();

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(shiftId, shiftAssignment.getShiftId(), "shiftId не совпадает");
        softAssert.assertEquals(shopperId, shiftAssignment.getShopperId(), "shopperId не совпадает");
        softAssert.assertTrue(shiftAssignment.getStartsAt().startsWith(startsAt), "startAt не совпадает");
        softAssert.assertTrue(shiftAssignment.getEndsAt().startsWith(endsAt), "endAt не совпадает");
        softAssert.assertAll();
    }

    @TmsLink("34")
    @Test(description = "Изменение назначения в смену",
            groups = {API_SHOPPER_REGRESS},
            dependsOnMethods = "postShiftAssignments200")
    public void putShiftAssignments200() {
        String startsAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
        String endsAt = LocalDateTime.now().plusHours(1).truncatedTo(ChronoUnit.SECONDS).toString();

        Response response = ShopperAdminRequest.ShiftAssignments.PUT(shiftAssignmentId, shiftId, shopperId, startsAt, endsAt);

        checkStatusCode200(response);

        ShiftAssignmentV1 shiftAssignment = response.as(ShiftAssignmentSHPResponse.class).getShiftAssignment();

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(shiftAssignmentId, shiftAssignment.getId(), "shiftAssignmentId not equals");
        softAssert.assertEquals(shiftId, shiftAssignment.getShiftId(), "shiftId не совпадает");
        softAssert.assertEquals(shopperId, shiftAssignment.getShopperId(), "shopperId не совпадает");
        softAssert.assertTrue(shiftAssignment.getStartsAt().startsWith(startsAt), "startAt не совпадает");
        softAssert.assertTrue(shiftAssignment.getEndsAt().startsWith(endsAt), "endsAt не совпадает");
        softAssert.assertAll();
    }

    @TmsLink("35")
    @Test(description = "Изменение маршрута",
            groups = {API_SHOPPER_REGRESS},
            dependsOnMethods = "postRoutes200")
    public void putRoutes200() {
        Response response = ShopperAdminRequest.Routes.PUT(routeId, routeScheduleId, driverId);
        checkStatusCode200(response);

        RouteV1 route = response.as(RouteSHPResponse.class).getRoute();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(routeId, route.getId(), "id маршрута не совпадает");
        softAssert.assertEquals(driverId, route.getDriver().getId(), "id водителя не совпадает");
        softAssert.assertAll();
    }

    @TmsLink("36")
    @Test(description = "Создание блокировки для маршрута",
            groups = {API_SHOPPER_REGRESS},
            dependsOnMethods = "postRoutes200")
    public void postRoutesLock200() {
        Response response = ShopperAdminRequest.Routes.Lock.POST(routeId);
        checkStatusCode200(response);
    }

    @TmsLink("41")
    @Test(description = "Удаление блокировки для маршрута",
            groups = {API_SHOPPER_REGRESS},
            dependsOnMethods = {"postRoutesLock200"})
    public void deleteRoutesLock200() {
        Response response = ShopperAdminRequest.Routes.Lock.DELETE(routeId);
        checkStatusCode200(response);
    }

    @TmsLink("38")
    @Test(description = "Удаление маршрута",
            groups = {API_SHOPPER_REGRESS},
            dependsOnMethods = {"postRoutes200", "getRoute200", "postRoutesLock200", "putRoutes200"})
    public void deleteRoute200() {
        Response response = ShopperAdminRequest.Routes.DELETE(routeId);
        checkStatusCode200(response);
    }

    @TmsLink("40")
    @Test(description = "Изменения` шоппера",
            groups = {API_SHOPPER_REGRESS})
    public void patchShopper200() {
        Response response = ShopperAdminRequest.Shoppers.PATCH(shopperId, shopperStatus);
        checkStatusCode200(response);

        ShopperV1 shopper = response.as(ShopperAdminSHPResponse.class).getShopper();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(shopperId, shopper.getId(), "shopperId не совпадает");
        softAssert.assertEquals(shopperStatus, shopper.getStatus(), "status не совпадает");
        softAssert.assertAll();
    }

    @TmsLink("42")
    @Test(description = "Создание видимости для маршрута",
            groups = {API_SHOPPER_REGRESS},
            dependsOnMethods = {"postRoutes200"})
    public void postRoutesVisibility200() {
        Response response = ShopperAdminRequest.Routes.Visibility.POST(routeId);
        checkStatusCode200(response);
    }

    @TmsLink("89")
    @Test(description = "Создание видимости для маршрута",
            groups = {API_SHOPPER_REGRESS},
            dependsOnMethods = {"postRoutesVisibility200"})
    public void deleteRoutesVisibility200() {
        Response response = ShopperAdminRequest.Routes.Visibility.DELETE(routeId);
        checkStatusCode200(response);
    }

    @Story("Dispatch settings")
    @Test(description = "Получение настроек ретейлера",
            groups = {API_SHOPPER_REGRESS})
    public void getOrderServiceSettings() {
        String storeUuid = "599ba7b7-0d2f-4e54-8b8e-ca5ed7c6ff8a";
        final Response response = ShopperAdminRequest.OrderServiceSettings.GET(storeUuid);
        RouteOrderServiceSettings parameters = response.as(RouteOrderServiceSettings.class);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, RouteOrderServiceSettings.class);
        assertEquals(parameters.getStoreOrderServiceSetting().getStoreUuid(), storeUuid, "Вернулся неверный UUID");
    }

    @Story("Dispatch settings")
    @TmsLink("159")
    @Test(description = "Получение настроек сервиса RES",
            groups = {API_SHOPPER_REGRESS})
    public void getEstimatorSettings() {
        String retailerUUID = "4872ead0-274b-49a2-955e-a5101a7de9cb";
        final Response response = ShopperAdminRequest.Stores.GET(retailerUUID);

        RouteEstimatorResponse parameters = response.as(RouteEstimatorResponse.class);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RouteEstimatorResponse.class);
        assertEquals(parameters.getStoreEstimatorSetting().getStoreUuid(), retailerUUID, "Вернулся неверный UUID");
    }

    @Story("Dispatch settings")
    @TmsLink("189")
    @Test(description = "Изменение настроек сервиса RES",
            groups = {API_SHOPPER_REGRESS})
    public void putEstimatorSettings() {
        String retailerUUID = "4872ead0-274b-49a2-955e-a5101a7de9cb";
        var parameters = PutEstimatorSettings.builder()
                .settingsRes(ShopperAdminRequest.SettingsRes.builder()
                        .estimatorParameters(ShopperAdminRequest.EstimatorParameters.builder()
                                .storeUuid("4872ead0-274b-49a2-955e-a5101a7de9cb")
                                .avgParkingMinVehicle(RandomUtils.nextInt(10, 100))
                                .averageSpeedForStraightDistanceToClientMin(RandomUtils.nextInt(10, 100))
                                .additionalFactorForStraightDistanceToClientMin(RandomUtils.nextInt(10, 100))
                                .orderReceiveTimeFromAssemblyToDeliveryMin(RandomUtils.nextInt(10, 100))
                                .avgToPlaceMinExternal(RandomUtils.nextInt(10, 100))
                                .orderTransferTimeFromAssemblyToDeliveryMin(RandomUtils.nextInt(10, 100))
                                .orderTransferTimeFromDeliveryToClientMin(RandomUtils.nextInt(10, 100))
                                .build())
                        .build())
                .build();

        Response response = ShopperAdminRequest.Stores.PUT(retailerUUID, parameters);
        checkStatusCode200(response);

        Response responseGet = ShopperAdminRequest.Stores.GET(retailerUUID);

        StoreEstimatorSettingV1 routeEstimator = responseGet.as(RouteEstimatorResponse.class).getStoreEstimatorSetting();

        BaseApiCheckpoints.compareTwoObjects(routeEstimator.getAvgParkingMinVehicle(),
                parameters.getSettingsRes().getEstimatorParameters().getAvgParkingMinVehicle());
        BaseApiCheckpoints.compareTwoObjects(routeEstimator.getAverageSpeedForStraightDistanceToClientMin(),
                parameters.getSettingsRes().getEstimatorParameters().getAverageSpeedForStraightDistanceToClientMin());
        BaseApiCheckpoints.compareTwoObjects(routeEstimator.getAdditionalFactorForStraightDistanceToClientMin(),
                parameters.getSettingsRes().getEstimatorParameters().getAdditionalFactorForStraightDistanceToClientMin());
        BaseApiCheckpoints.compareTwoObjects(routeEstimator.getAvgToPlaceMinExternal(),
                parameters.getSettingsRes().getEstimatorParameters().getAvgToPlaceMinExternal());
        BaseApiCheckpoints.compareTwoObjects(routeEstimator.getOrderTransferTimeFromDeliveryToClientMin(),
                parameters.getSettingsRes().getEstimatorParameters().getOrderTransferTimeFromDeliveryToClientMin());
        BaseApiCheckpoints.compareTwoObjects(routeEstimator.getOrderTransferTimeFromAssemblyToDeliveryMin(),
                parameters.getSettingsRes().getEstimatorParameters().getOrderTransferTimeFromAssemblyToDeliveryMin());
        BaseApiCheckpoints.compareTwoObjects(routeEstimator.getOrderReceiveTimeFromAssemblyToDeliveryMin(),
                parameters.getSettingsRes().getEstimatorParameters().getOrderReceiveTimeFromAssemblyToDeliveryMin());
        BaseApiCheckpoints.compareTwoObjects(routeEstimator.getStoreUuid(),
                parameters.getSettingsRes().getEstimatorParameters().getStoreUuid());
    }


    @Story("Routing settings")
    @Test(description = "Установка маршрутизатизации на диспатч",
            groups = {API_SHOPPER_REGRESS})
    public void patchRoutingSettingsDispatch() {

        int storeId = 16;
        String schedule_type = "dispatch";

        final Response response = ShopperAdminRequest.RoutingSettings.PATCH(storeId, schedule_type);
        RouteRoutingSettings parameters = response.as(RouteRoutingSettings.class);

        String place_uuid = parameters.getStore().getUuid();

        PlaceSettingsEntity placeSettingsEntity = PlaceSettingsDao.INSTANCE.getScheduleType(place_uuid);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, RouteRoutingSettings.class);
        Allure.step("", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(parameters.getStore().getId(), storeId, "Вернулся неверный ID магазина");
            softAssert.assertEquals(parameters.getStore().getScheduleType(), schedule_type, "Вернулись не верные настройки маршрутизации");
            compareTwoObjects(parameters.getStore().getScheduleType(), placeSettingsEntity.getScheduleType(), softAssert);
            softAssert.assertAll();
        });
    }

    @Story("Routing settings")
    @Test(description = "Установка маршрутизатизации на револьверную",
            groups = {API_SHOPPER_REGRESS})
    public void patchRoutingSettingsList() {
        int storeId = 16;
        String schedule_type = "list";

        final Response response = ShopperAdminRequest.RoutingSettings.PATCH(storeId, schedule_type);
        RouteRoutingSettings parameters = response.as(RouteRoutingSettings.class);

        String place_uuid = parameters.getStore().getUuid();

        PlaceSettingsEntity placeSettingsEntity = PlaceSettingsDao.INSTANCE.getScheduleType(place_uuid);

        checkStatusCode200(response);

        checkResponseJsonSchema(response, RouteRoutingSettings.class);
        Allure.step("", () -> {
            final SoftAssert softAssert = new SoftAssert();
            assertEquals(parameters.getStore().getId(), storeId, "Вернулся неверный ID магазина");
            assertEquals(parameters.getStore().getScheduleType(), schedule_type, "Вернулись не верные настройки маршрутизации");
            compareTwoObjects(parameters.getStore().getScheduleType(), placeSettingsEntity.getScheduleType());
            softAssert.assertAll();
        });
    }

}
