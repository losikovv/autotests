package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.admin.StoresAdminRequest;
import ru.instamart.api.request.v1.RetailersV1Request;
import ru.instamart.api.request.v1.StoreZonesV1Request;
import ru.instamart.api.response.v1.StoreZoneV1Response;
import ru.instamart.api.response.v1.StoreZonesV1Response;
import ru.instamart.jdbc.dao.stf.SpreeRetailersCleanDao;
import ru.instamart.jdbc.dao.stf.StoreZonesDao;
import ru.instamart.jdbc.dao.stf.StoresDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.StoreZonesCoordinates;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;
import static ru.instamart.api.request.admin.StoresAdminRequest.getStoreForRetailerTests;

@Epic("ApiV1")
@Feature("Admin Web")
public class StoreZonesV1Tests extends RestBase {

    private final String retailerName = "retailer_" + Generate.literalString(6);

    @BeforeClass(alwaysRun = true, description = "Создание ритейлера для тестов")
    public void preconditions() {
        admin.authApi();
        RetailersV1Request.Retailer retailer = RetailersV1Request.getRetailer();
        retailer.setName(retailerName);
        admin.createRetailer(retailer); //todo починить 405 на стейдже "Создание ритейлеров в данном разделе отключено. Используйте раздел Онбординг."
    }

    @AfterClass(alwaysRun = true, description = "Удаление ритейлера")
    public void deleteRetailerWithStores() {
        SpreeRetailersCleanDao.INSTANCE.deleteRetailerWithStores(retailerName);
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2795)
    @Story("Зоны магазинов")
    @Test(groups = {"api-instamart-regress", "api-v1"},
            description = "Получение зон дефолтного магазина")
    public void getStoreZonesPositive() {
        final Response response = StoreZonesV1Request.Zones.GET(EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoreZonesV1Response.class);
        compareTwoObjects(response.as(StoreZonesV1Response.class).getZones().size(),
                StoreZonesDao.INSTANCE.getStoreZonesIDsBySid(EnvironmentProperties.DEFAULT_SID).size());
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2796)
    @Story("Зоны магазинов")
    @Test(groups = {"api-instamart-regress", "api-v1"},
            description = "Получение пустого списка зон магазина (у магазина нет зон)")
    public void getStoreZonesPositiveEmptyZonesList() {
        final StoresAdminRequest.Stores store = getStoreForRetailerTests(retailerName, "Москва");
        admin.auth();
        admin.createStore(store);

        final Response response = StoreZonesV1Request.Zones.GET(
                StoresDao.INSTANCE.getStoreByCoordinates(store.getStore().getLocation().getLat(), store.getStore().getLocation().getLon()).getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoreZonesV1Response.class);
        compareTwoObjects(response.as(StoreZonesV1Response.class).getZones().size(), 0);
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2797)
    @Story("Зоны магазинов")
    @Test(groups = {"api-instamart-regress", "api-v1"},
            description = "Получение списка зон магазина, несуществующий магазин")
    public void getStoreZonesNegativeInvalidSid() {
        final Response response = StoreZonesV1Request.Zones.GET(Generate.integer(6));
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2798)
    @Story("Зоны магазинов")
    @Test(groups = {"api-instamart-regress", "api-v1"},
            description = "Добавление зон в магазин")
    public void postStoreZones() {
        final StoresAdminRequest.Stores store = getStoreForRetailerTests(retailerName, "Москва");
        admin.auth();
        admin.createStore(store);

        final Response response = StoreZonesV1Request.Zones.POST(
                StoresDao.INSTANCE.getStoreByCoordinates(store.getStore().getLocation().getLat(), store.getStore().getLocation().getLon()).getId(),
                StoreZonesCoordinates.testMoscowZoneCoordinates());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoreZoneV1Response.class);
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2799)
    @Story("Зоны магазинов")
    @Test(groups = {"api-instamart-regress", "api-v1"},
            description = "Добавление зон в магазин. Негативный. Магазин не существует")
    public void postStoreZonesNegativeInvalidSid() {
        final Response response = StoreZonesV1Request.Zones.POST(Generate.integer(6),
                StoreZonesCoordinates.testMoscowZoneCoordinates());
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2800)
    @Story("Зоны магазинов")
    @Test(groups = {"api-instamart-regress", "api-v1"},
            description = "Загрузка файла с зонами")
    public void importStoreZonesFile() {
        final StoresAdminRequest.Stores store = getStoreForRetailerTests(retailerName, "Москва");
        admin.auth();
        admin.createStore(store);

        final Response response = StoreZonesV1Request.ZoneFiles.POST(
                StoresDao.INSTANCE.getStoreByCoordinates(store.getStore().getLocation().getLat(), store.getStore().getLocation().getLon()).getId(),
                "src/test/resources/data/zone.kml");
        checkStatusCode200(response);
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2801)
    @Story("Зоны магазинов")
    @Test(groups = {"api-instamart-regress", "api-v1"},
            description = "Редактирование зон магазина")
    public void editStoreZones() {
        final StoresAdminRequest.Stores store = getStoreForRetailerTests(retailerName, "Москва");
        admin.auth();
        admin.createStore(store);
        final int sid = StoresDao.INSTANCE.getStoreByCoordinates(store.getStore().getLocation().getLat(), store.getStore().getLocation().getLon()).getId();
        final Response response = StoreZonesV1Request.Zones.POST(
                sid,
                StoreZonesCoordinates.testMoscowZoneCoordinates());

        final String newZoneName = Generate.literalString(7);
        final Response putResponse = StoreZonesV1Request.Zones.PUT(
                sid,
                response.as(StoreZoneV1Response.class).getZone().getId().intValue(),
                newZoneName,
                StoreZonesCoordinates.testMoscowZoneCoordinates());

        checkStatusCode200(putResponse);
        checkResponseJsonSchema(putResponse, StoreZoneV1Response.class);

        final Response getResponse = StoreZonesV1Request.Zones.GET(sid);
        checkStatusCode200(getResponse);
        compareTwoObjects(getResponse.as(StoreZonesV1Response.class).getZones().size(), 1);
        compareTwoObjects(getResponse.as(StoreZonesV1Response.class).getZones().get(0).getName(), newZoneName);
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2802)
    @Story("Зоны магазинов")
    @Test(groups = {"api-instamart-regress", "api-v1"},
            description = "Удаление зон магазина")
    public void deleteStoreZones() {
        final StoresAdminRequest.Stores store = getStoreForRetailerTests(retailerName, "Москва");
        admin.auth();
        admin.createStore(store);
        final int sid = StoresDao.INSTANCE.getStoreByCoordinates(store.getStore().getLocation().getLat(), store.getStore().getLocation().getLon()).getId();
        final Response response = StoreZonesV1Request.Zones.POST(
                sid,
                StoreZonesCoordinates.testMoscowZoneCoordinates());

        final Response deleteResponse = StoreZonesV1Request.Zones.DELETE(
                sid,
                response.as(StoreZoneV1Response.class).getZone().getId().intValue());
        checkStatusCode200(deleteResponse);
        checkResponseJsonSchema(deleteResponse, StoreZoneV1Response.class);

        final Response getResponse = StoreZonesV1Request.Zones.GET(sid);
        checkStatusCode200(getResponse);
        compareTwoObjects(getResponse.as(StoreZonesV1Response.class).getZones().size(), 0);
    }
}
