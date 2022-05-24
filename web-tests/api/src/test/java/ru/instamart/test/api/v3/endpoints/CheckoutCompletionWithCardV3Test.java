package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.v2.PaymentToolV2;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.request.v3.CheckoutV3Request;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.api.response.v3.CompletionPaymentV3Response;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Collections;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.kraken.data.PaymentCards.*;

@Epic("ApiV3")
@Feature("Чекаут")
public class CheckoutCompletionWithCardV3Test extends RestBase {

    private UserData user;
    private MultiretailerOrderV1Response order;
    private AddressV2 addressDefaultSid;
    private long offerDefaultSidId;

    @BeforeMethod(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        apiV2.authByQA(user);
        addressDefaultSid = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID);
        offerDefaultSidId = apiV2.getProductFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_SID).get(0).getId();
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        order = apiV1.getMultiRetailerOrder();
        apiV1.addReplacementPolicy();
        apiV1.addDeliveryWindow();
    }

    @CaseIDs(value = {@CaseId(2584), @CaseId(2620)})
    @Story("Завершение заказа")
    @Test(description = "Завершение заказа при выборе \"Оплата картой\"",
            groups = "api-instamart-regress")
    public void completeOrder() {
        apiV2.bindCardToUser(user, order.getNumber(), testCard());
        apiV3.addPaymentTool(order, PaymentToolV2.SBER_GATEWAY.getKey());
        final Response response = CheckoutV3Request.Completion.POST(order.getNumber(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CompletionPaymentV3Response.class);
        String orderUuid = SpreeOrdersDao.INSTANCE.getOrderByNumber(order.getNumber()).getUuid();
        Allure.step("Проверяем, что в урле корректный uuid заказа", () -> {
            Assert.assertTrue(response.as(CompletionPaymentV3Response.class).getPaymentUrl().contains(orderUuid),
                    "Пришел неверный uuid заказа");});
    }

    @CaseId(2651)
    @Story("Завершение заказа")
    @Test(description = "Завершение заказа со списанием бонусов СберСпасибо",
            groups = "api-instamart-regress")
    public void completeOrderWithSberSbasibo() {
        apiV2.bindCardToUser(user, order.getNumber(), testCardWithSpasibo());
        apiV3.addPaymentTool(order, PaymentToolV2.SBER_GATEWAY.getKey(), 1);
        final Response response = CheckoutV3Request.Completion.POST(order.getNumber(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CompletionPaymentV3Response.class);
        String orderUuid = SpreeOrdersDao.INSTANCE.getOrderByNumber(order.getNumber()).getUuid();
        Allure.step("Проверяем, что в урле корректный uuid заказа", () -> {
            Assert.assertTrue(response.as(CompletionPaymentV3Response.class).getPaymentUrl().contains(orderUuid),
                    "Пришел неверный uuid заказа");});
    }
}
