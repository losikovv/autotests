package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.NextDeliveryV2;
import ru.instamart.api.request.v1.ShipmentsV1Request;
import ru.instamart.api.request.v1.StoresV1Request;
import ru.instamart.api.response.v1.ShippingRatesV1Response;
import ru.instamart.api.response.v2.NextDeliveriesV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;
import static ru.instamart.kraken.helper.DateTimeHelper.getDateFromMSK;
import static ru.instamart.kraken.util.TimeUtil.getFutureDateWithoutTime;

@Epic("ApiV1")
@Feature("Подзаказы")
public class ShipmentsV1Tests extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        if (!EnvironmentProperties.SERVER.equals("production")) {
            SessionFactory.createSessionToken(SessionType.API_V1, UserManager.getDefaultAdminAllRoles());
        }
    }


    @CaseId(1390)
    @Story("Получить окно доставки для подзаказа для указанного дня")
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Получить окно доставки для подзаказа для указанного дня с существующим id")
    public void getShippingRates() {
        final Response response = ShipmentsV1Request.ShippingRates.GET(apiV2.getShipmentsNumber(), getFutureDateWithoutTime(1L));
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingRatesV1Response.class);
    }

    @CaseId(1391)
    @Story("Получить окно доставки для подзаказа для указанного дня")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получить окно доставки для несуществующего подзаказа для указанного дня")
    public void getShippingRatesForNonExistentShipment() {
        final Response response = ShipmentsV1Request.ShippingRates.GET("failedShippingNumber", getDateFromMSK());
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseIDs(value = {@CaseId(1392), @CaseId(1393), @CaseId(1394), @CaseId(1395), @CaseId(1396), @CaseId(1397), @CaseId(1400), @CaseId(1398), @CaseId(1399)})
    @Story("Получить окно доставки для подзаказа для указанного дня")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProvider = "dateFormats",
            dataProviderClass = RestDataProvider.class,
            description = "Получить окно доставки для подзаказа для указанного дня с неверным форматом даты")
    public void getShippingRatesWithOtherDate(DateTimeFormatter formatter) {
        ZonedDateTime localDate = ZonedDateTime.now().plusDays(1);
        String formattedDate = localDate.format(formatter);
        final Response response = ShipmentsV1Request.ShippingRates.GET(apiV2.getShipmentsNumber(), formattedDate);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingRatesV1Response.class);
    }

    @CaseId(1402)
    @Story("Получить ближайшие окна доставки")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получить ближайшие окна доставки для несуществующего магазина")
    public void getNextDeliveriesForNonexistentStore() {
        final Response response = StoresV1Request.NextDeliveries.GET(0, new StoresV1Request.NextDeliveriesParams());
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseIDs(value = {@CaseId(1403), @CaseId(1404)})
    @Story("Получить ближайшие окна доставки")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получить ближайшие окна доставки с корректными данными",
            dataProvider = "nextDeliveriesParams",
            dataProviderClass = RestDataProvider.class)
    public void getNextDeliverWithAllData(StoresV1Request.NextDeliveriesParams params) {
        final Response response = StoresV1Request.NextDeliveries.GET(EnvironmentProperties.DEFAULT_SID, params);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, NextDeliveriesV2Response.class);
    }

    @CaseId(1405)
    @Story("Получить ближайшие окна доставки")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить ближайшие окна доставки без параметров")
    public void getNextDeliverWithoutParams() {
        final Response response = StoresV1Request.NextDeliveries.GET(EnvironmentProperties.DEFAULT_SID, new StoresV1Request.NextDeliveriesParams());
        checkStatusCode200(response);
        List<NextDeliveryV2> nextDeliveries = response.as(NextDeliveriesV2Response.class).getNextDeliveries();
        compareTwoObjects(0, nextDeliveries.size());
    }
}
