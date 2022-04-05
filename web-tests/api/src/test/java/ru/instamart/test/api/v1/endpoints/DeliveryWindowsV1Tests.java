package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.model.v1.DeliveryWindowV1;
import ru.instamart.api.request.v1.DeliveryWindowKindsV1Request;
import ru.instamart.api.request.v1.DeliveryWindowsV1Request;
import ru.instamart.api.request.v1.StoresV1Request;
import ru.instamart.api.response.v1.DeliveryWindowKindsV1Response;
import ru.instamart.api.response.v1.DeliveryWindowV1Response;
import ru.instamart.api.response.v1.DeliveryWindowsV1Response;
import ru.instamart.jdbc.dao.stf.DeliveryWindowsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.kraken.util.TimeUtil.getDbDeliveryDateFrom;
import static ru.instamart.kraken.util.TimeUtil.getDbDeliveryDateTo;

@Epic("ApiV1")
@Feature("Admin Web")
public class DeliveryWindowsV1Tests extends RestBase {

    private Long deliveryWindowId;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authApi();
    }


    @Story("Окна доставки")
    @CaseId(1145)
    @Test(description = "Получение списка окон доставки для магазина на конкретную дату",
            groups = {"api-instamart-regress"})
    public void getDeliveryWindows() {
        final Response response = StoresV1Request.DeliveryWindows.GET(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, DeliveryWindowsV1Response.class);
        List<DeliveryWindowV1> deliveryWindowsFromResponse = response.as(DeliveryWindowsV1Response.class).getDeliveryWindows();
        int deliveryWindowsFromDbCount = DeliveryWindowsDao.INSTANCE.getCount(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID, getDbDeliveryDateFrom(1L), getDbDeliveryDateTo(1L));
        compareTwoObjects(deliveryWindowsFromResponse.size(), deliveryWindowsFromDbCount);
        deliveryWindowId = deliveryWindowsFromResponse.get(0).getId();
    }

    @Story("Окна доставки")
    @CaseId(1146)
    @Test(description = "Получение списка окон доставки для несуществуюшего магазина",
            groups = {"api-instamart-regress"})
    public void getDeliveryWindowsForNonExistingStore() {
        final Response response = StoresV1Request.DeliveryWindows.GET(0);
        checkStatusCode404(response);
    }

    @Story("Окна доставки")
    @CaseId(1147)
    @Test(description = "Генерация окон доставки",
            groups = {"api-instamart-regress"})
    public void generateDeliveryWindows() {
        final Response response = StoresV1Request.DeliveryWindows.POST(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        checkStatusCode200(response);
    }

    @Story("Окна доставки")
    @CaseId(1148)
    @Test(description = "Генерация окон доставки для несуществующего магазина",
            groups = {"api-instamart-regress"})
    public void generateDeliveryWindowsForNonExistingStore() {
        final Response response = StoresV1Request.DeliveryWindows.POST(0);
        checkStatusCode404(response);
    }

    @Story("Окна доставки")
    @CaseId(1149)
    @Test(description = "Редактирование окна доставки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "getDeliveryWindows")
    public void editDeliveryWindow() {
        Integer number = RandomUtils.nextInt(1, 100);
        final Response response = DeliveryWindowsV1Request.PUT(
                deliveryWindowId,
                number,
                number,
                number,
                number,
                ShippingMethodV2.PICKUP.getMethod());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, DeliveryWindowV1Response.class);
        DeliveryWindowV1 deliveryWindowFromResponse = response.as(DeliveryWindowV1Response.class).getDeliveryWindow();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects((double) number, deliveryWindowFromResponse.getShipmentMaxKilos(), softAssert);
        compareTwoObjects((double) number, deliveryWindowFromResponse.getShipmentsExcessKilos(), softAssert);
        compareTwoObjects(number, deliveryWindowFromResponse.getShipmentsExcessItemsCount(), softAssert);
        compareTwoObjects(number, deliveryWindowFromResponse.getShipmentsLimit(), softAssert);
        compareTwoObjects(ShippingMethodV2.PICKUP.getMethod(), deliveryWindowFromResponse.getKind(), softAssert);
        softAssert.assertAll();
    }

    @Story("Окна доставки")
    @CaseId(1150)
    @Test(description = "Редактирование несуществующего окна доставки",
            groups = {"api-instamart-regress"})
    public void editNonExistingDeliveryWindow() {
        final Response response = DeliveryWindowsV1Request.PUT(
                0L,
                11,
                11,
                11,
                11,
                ShippingMethodV2.PICKUP.getMethod());
        checkStatusCode404(response);
    }

    @Story("Окна доставки")
    @CaseId(1151)
    @Test(description = "Редактирование окна доставки c невалидными значениями",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "getDeliveryWindows")
    public void editDeliveryWindowWithInvalidValues() {
        final Response response = DeliveryWindowsV1Request.PUT(
                deliveryWindowId,
                -1,
                -1,
                -1,
                -1,
                ShippingMethodV2.PICKUP.getMethod());
        checkStatusCode422(response);
        String responseAsString = response.asString();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(responseAsString.contains("\"shipments_weight_limit\":[\"может иметь лишь значение большее 0\"]"));
        softAssert.assertTrue(responseAsString.contains("\"shipments_excess_kilos\":[\"может иметь лишь значение большее или равное 0\"]"));
        softAssert.assertTrue(responseAsString.contains("\"shipment_max_weight\":[\"может иметь лишь значение большее или равное 0\"]"));
        softAssert.assertTrue(responseAsString.contains("\"shipments_items_count_limit\":[\"может иметь лишь значение большее или равное 0\"]"));
        softAssert.assertTrue(responseAsString.contains("\"shipments_limit\":[\"может иметь лишь значение большее 0\"]"));
        softAssert.assertAll();
    }

    @Story("Окна доставки")
    @CaseId(2135)
    @Test(description = "Получение списка типов окон доставки",
            groups = {"api-instamart-regress"})
    public void getDeliveryWindowKinds() {
        final Response response = DeliveryWindowKindsV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, DeliveryWindowKindsV1Response.class);
    }
}
