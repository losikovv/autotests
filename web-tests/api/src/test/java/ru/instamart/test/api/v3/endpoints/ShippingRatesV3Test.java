package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v3.ErrorV3;
import ru.instamart.api.request.v3.CheckoutV3Request;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.api.response.v2.ShippingRatesV2Response;
import ru.instamart.api.response.v3.ErrorsV3Response;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.kraken.util.TimeUtil.getFutureDateWithoutTime;

@Epic("ApiV3")
@Feature("Чекаут")
public class ShippingRatesV3Test extends RestBase {

    private UserData user;
    private MultiretailerOrderV1Response order;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        AddressV2 addressDefaultSid = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID);
        long offerDefaultSidId = apiV2.getProductFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_SID).get(0).getId();
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId, EnvironmentProperties.DEFAULT_SID);
        order = apiV1.getMultiRetailerOrder();
    }

    @CaseId(2372)
    @Story("Слоты доставки")
    @Test(description = "Запрос на получение слотов",
            groups = "api-instamart-regress")
    public void getShippingRates() {
        final Response response = CheckoutV3Request.Shipments.GET(order.getShipments().get(0).getNumber(), getFutureDateWithoutTime(1L));
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingRatesV2Response.class);
    }

    @CaseId(2564)
    @Story("Слоты доставки")
    @Test(description = "Запрос на получение слотов с номером чужого шипмента",
            groups = "api-instamart-regress")
    public void getShippingRatesForAnotherUserShipment() {
        final Response response = CheckoutV3Request.Shipments.GET(SpreeShipmentsDao.INSTANCE.getShipmentOfAnotherUser(Long.parseLong(user.getId())).getNumber(), getFutureDateWithoutTime(1L));
        checkStatusCode403(response);
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        compareTwoObjects(errors.get(0).getMessage(), "Пользователь не может выполнить это действие");
    }

    @CaseId(2565)
    @Story("Слоты доставки")
    @Test(description = "Запрос на получение слотов с несуществующим шипментом",
            groups = "api-instamart-regress")
    public void getShippingRatesForNonExistentShipment() {
        final Response response = CheckoutV3Request.Shipments.GET("failedShipmentNumber", getFutureDateWithoutTime(1L));
        checkStatusCode404(response);
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        compareTwoObjects(errors.get(0).getMessage(), "Доставка не существует");
    }

    @CaseId(2567)
    @Story("Слоты доставки")
    @Test(description = "Запрос на получение слотов на 7 дней",
            groups = "api-instamart-regress")
    public void getShippingRatesForSevenDays() {
        final Response response = CheckoutV3Request.Shipments.GET(order.getShipments().get(0).getNumber(), null);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingRatesV2Response.class);
        compareTwoObjects(response.as(ShippingRatesV2Response.class).getMeta().getAvailableDays().get(6), getFutureDateWithoutTime(6L));
    }

    @CaseId(2566)
    @Story("Слоты доставки")
    @Test(description = "Запрос на получение слотов, указав номер шипмента неавторизованного пользователя",
            groups = "api-instamart-regress",
            dependsOnMethods = {"getShippingRatesForSevenDays", "getShippingRatesForNonExistentShipment",
                    "getShippingRatesForAnotherUserShipment", "getShippingRates"})
    public void getShippingRatesWithoutAuth() {
        SessionFactory.clearSession(SessionType.API_V1);
        final Response response = CheckoutV3Request.Shipments.GET(order.getShipments().get(0).getNumber(), getFutureDateWithoutTime(1L));
        checkStatusCode401(response);
    }
}
