package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.PaymentToolV2;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v3.ErrorV3;
import ru.instamart.api.request.v3.CheckoutV3Request;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.api.response.v3.CompletionShipmentV3Response;
import ru.instamart.api.response.v3.ErrorV3Response;
import ru.instamart.api.response.v3.ErrorsV3Response;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.ApiV3Checkpoints.checkError;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV3")
@Feature("Чекаут")
@Test(singleThreaded = true)
public class CheckoutCompletionV3Test extends RestBase {

    private UserData user;
    private MultiretailerOrderV1Response order;
    private AddressV2 addressDefaultSid;
    private long offerDefaultSidId;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        addressDefaultSid = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID);
        offerDefaultSidId = apiV2.getProductFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_SID).get(0).getId();
        order = apiV1.order(addressDefaultSid, offerDefaultSidId, user);
    }

    @CaseId(2575)
    @Story("Завершение заказа")
    @Test(description = "Завершение заказа",
            groups = "api-instamart-regress")
    public void completeOrder() {
        final Response response = CheckoutV3Request.Completion.POST(order.getNumber(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CompletionShipmentV3Response.class);
        Allure.step("Проверяем, что в урле корректный номер заказа", () -> {
            Assert.assertTrue(response.as(CompletionShipmentV3Response.class).getShipmentUrl().contains(order.getShipments().get(0).getNumber()),
                    "Пришел неверный номер заказа");
        });
    }

    @CaseId(2576)
    @Story("Завершение заказа")
    @Test(description = "Завершение чужого заказа",
            groups = "api-instamart-regress")
    public void completeAnotherUserOrder() {
        String anotherOrderNumber = SpreeOrdersDao.INSTANCE.getOrderOfAnotherUser(Long.parseLong(user.getId())).getNumber();
        final Response response = CheckoutV3Request.Completion.POST(anotherOrderNumber, Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode403(response);
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        compareTwoObjects(errors.get(0).getMessage(), "Пользователь не может выполнить это действие");
    }

    @CaseId(2577)
    @Story("Завершение заказа")
    @Test(description = "Завершение заказа c чужим шипментом",
            groups = "api-instamart-regress")
    public void completeOrderWithAnotherUserShipment() {
        String anotherUserShipment = SpreeShipmentsDao.INSTANCE.getShipmentOfAnotherUser(Long.parseLong(user.getId())).getNumber();
        final Response response = CheckoutV3Request.Completion.POST(order.getNumber(), Collections.singletonList(anotherUserShipment));
        checkStatusCode422(response);
        checkError(response, "invalid_current_shipments", "Вы уже оформляете заказ в другом магазине");
    }

    @CaseId(2578)
    @Story("Завершение заказа")
    @Test(description = "Завершение заказа без выбранного способа оплаты",
            groups = "api-instamart-regress",
            dependsOnMethods = "completeOrder")
    public void completeOrderWithoutPaymentTool() {
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        apiV1.addReplacementPolicy();
        apiV1.addDeliveryWindow();
        order = apiV1.getMultiRetailerOrder();
        final Response response = CheckoutV3Request.Completion.POST(order.getNumber(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode422(response);
        checkStatusCode422(response);
        assertEquals(response.as(ErrorV3Response.class).getType(), "payments", "'type' в сообщении об ощибке отличается от ожидаемого");
        assertEquals(response.as(ErrorV3Response.class).getTitle(), "Не выбран способ оплаты", "'title' в сообщении об ощибке отличается от ожидаемого");
    }

    @CaseId(2579)
    @Story("Завершение заказа")
    @Test(description = "Завершение заказа без выбранного окна доставки",
            groups = "api-instamart-regress",
            dependsOnMethods = "completeOrderWithoutPaymentTool")
    public void completeOrderWithoutDeliveryWindow() {
        apiV1.deleteShipment(order.getShipments().get(0).getNumber(), order.getToken());
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        apiV1.addReplacementPolicy();
        order = apiV1.getMultiRetailerOrder();
        apiV3.addPaymentTool(order, PaymentToolV2.LIFEPAY.getKey());
        final Response response = CheckoutV3Request.Completion.POST(order.getNumber(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode422(response);
        checkStatusCode422(response);
        assertEquals(response.as(ErrorV3Response.class).getType(), "shipments", "'type' в сообщении об ощибке отличается от ожидаемого");
        assertEquals(response.as(ErrorV3Response.class).getTitle(), "Выберите время доставки из METRO.", "'title' в сообщении об ощибке отличается от ожидаемого");
    }

    @CaseId(2580)
    @Story("Завершение заказа")
    @Test(description = "Завершение заказа неавторизованным пользователем",
            groups = "api-instamart-regress",
            dependsOnMethods = "completeOrderWithoutDeliveryWindow")
    public void completeOrderWithoutAuth() {
        SessionFactory.clearSession(SessionType.API_V1);
        final Response response = CheckoutV3Request.Completion.POST(order.getNumber(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode401(response);
    }
}
