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
import ru.instamart.api.request.v1.StoreSchedulesV1Request;
import ru.instamart.api.response.v1.admin.StoreSchedulesV1Response;
import ru.instamart.jdbc.dao.stf.SpreeRetailersCleanDao;
import ru.instamart.jdbc.dao.stf.StoresDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkErrorText;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;
import static ru.instamart.api.request.admin.StoresAdminRequest.getStoreForRetailerTests;

@Epic("ApiV1")
@Feature("Admin Web")
public class StoreSchedulesV1Tests extends RestBase {

    private final String retailerName = "retailer_" + Generate.literalString(6);

    @BeforeClass(alwaysRun = true, description = "Создание ритейлера для тестов")
    public void preconditions() {
        admin.authApi();
        RetailersV1Request.Retailer retailer = RetailersV1Request.getRetailer();
        retailer.setName(retailerName);
        admin.createRetailer(retailer);  //todo починить 405 на стейдже "Создание ритейлеров в данном разделе отключено. Используйте раздел Онбординг."
    }

    @AfterClass(alwaysRun = true, description = "Удаление ритейлера")
    public void deleteRetailerWithStores() {
        SpreeRetailersCleanDao.INSTANCE.deleteRetailerWithStores(retailerName);
    }

    @Skip(onServer = Server.STAGING)
    @Story("Шаблоны слотов")
    @CaseId(2803)
    @Test(description = "Получение шаблонов слотов. Шаблоны присутствуют",
            groups = {"api-instamart-regress"})
    public void getStoreSchedules() {
        final Response response = StoreSchedulesV1Request.Schedules.GET(EnvironmentProperties.DEFAULT_SID);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoreSchedulesV1Response.class);
        assertTrue(response.as(StoreSchedulesV1Response.class).getStoreSchedule().getTemplate().getDeliveryTimes().size() > 0,
                "В ответе отсутствует информация о шаблонах слотов");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Шаблоны слотов")
    @CaseId(2804)
    @Test(description = "Получение шаблонов слотов. Шаблоны отсутствуют (не загружены)",
            groups = {"api-instamart-regress"})
    public void getStoreSchedulesEmpty() {
        final StoresAdminRequest.Store store = getStoreForRetailerTests(retailerName, "Москва");
        admin.auth();
        admin.createStore(store);

        final Response response = StoreSchedulesV1Request.Schedules.GET(
                StoresDao.INSTANCE.getStoreByCoordinates(store.getLat(), store.getLon()).getId());

        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoreSchedulesV1Response.class);
        assertNull(response.as(StoreSchedulesV1Response.class).getStoreSchedule(), "В ответе присутствует информация о шаблонах слотов");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Шаблоны слотов")
    @CaseId(2805)
    @Test(description = "Получение шаблонов слотов. Магазин не существует",
            groups = {"api-instamart-regress"})
    public void getStoreSchedulesIncorrectSid() {
        final Response response = StoreSchedulesV1Request.Schedules.GET(Generate.integer(6));

        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Шаблоны слотов")
    @CaseId(2806)
    @Test(description = "Добавление шаблонов слотов",
            groups = {"api-instamart-regress"})
    public void postStoreSchedules() {
        final StoresAdminRequest.Store store = getStoreForRetailerTests(retailerName, "Москва");
        admin.auth();
        admin.createStore(store);

        final Response response = StoreSchedulesV1Request.Schedules.POST(
                StoresDao.INSTANCE.getStoreByCoordinates(store.getLat(), store.getLon()).getId());

        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoreSchedulesV1Response.class);
        assertEquals(response.as(StoreSchedulesV1Response.class).getStoreSchedule().getTemplate().getDeliveryTimes().size(), 1, "Количество шаблонов слотов в ответе отличается от ожидаемого");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Шаблоны слотов")
    @CaseId(2807)
    @Test(description = "Редактирование/Удаление шаблонов слотов",
            groups = {"api-instamart-regress"})
    public void putStoreSchedules() {
        final StoresAdminRequest.Store store = getStoreForRetailerTests(retailerName, "Москва");
        admin.auth();
        admin.createStore(store);
        final var sid = StoresDao.INSTANCE.getStoreByCoordinates(store.getLat(), store.getLon()).getId();
        final Response postResponse = StoreSchedulesV1Request.Schedules.POST(sid);
        checkStatusCode200(postResponse);

        final Response putResponse = StoreSchedulesV1Request.Schedules.PUT(sid);
        checkStatusCode200(putResponse);
        checkResponseJsonSchema(putResponse, StoreSchedulesV1Response.class);
        assertEquals(putResponse.as(StoreSchedulesV1Response.class).getStoreSchedule().getTemplate().getDeliveryTimes().size(), 1, "Количество шаблонов слотов в ответе отличается от ожидаемого");
    }
}
