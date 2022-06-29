package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v1.OrderKindV1;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v1.MultiretailerOrderShipmentV1;
import ru.instamart.api.model.v1.PaymentToolV1;
import ru.instamart.api.model.v1.ReplacementPolicyV1;
import ru.instamart.api.model.v1.UserShipmentV1;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v2.NextDeliveryV2;
import ru.instamart.api.request.v1.CheckoutV1Request;
import ru.instamart.api.response.v1.CompleteOrderV1Response;
import ru.instamart.api.response.v1.ErrorsV1Response;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;
import java.util.stream.Collectors;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.compareWithUserShipment;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode401;
import static ru.instamart.api.request.v1.CheckoutV1Request.getOrderAttributes;

@Epic("ApiV1")
@Feature("Оформление заказа")
public class CheckoutV1Tests extends RestBase {

    private MultiretailerOrderV1Response order;
    private AddressV2 address;
    private Long offerId;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        if (EnvironmentProperties.Env.isProduction()) {
            admin.authApi();
        } else {
            apiV1.authByPhone(UserManager.getQaUser());
        }
        address = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID);
        offerId = apiV2.getProductFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_SID).get(0).getId();
        apiV1.fillCart(address, ShippingMethodV2.BY_COURIER.getMethod(),offerId);
    }

    @Story("Checkout")
    @CaseId(2034)
    @Test(description = "Добавление информации о политике замен",
            groups = {"api-instamart-smoke", "api-instamart-prod"},
            dataProvider = "replacementPolicies",
            dataProviderClass = RestDataProvider.class,
            dependsOnMethods = "addNonExistentReplacementPolicies")
    public void addReplacementPolicies(ReplacementPolicyV1 replacementPolicy) {
        final Response response = CheckoutV1Request.PUT(replacementPolicy.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MultiretailerOrderV1Response.class);
        order = response.as(MultiretailerOrderV1Response.class);
        compareTwoObjects(order.getReplacementPolicy(), replacementPolicy);
    }

    @Story("Checkout")
    @CaseId(2035)
    @Test(description = "Добавление информации о несуществующей политике замен",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void addNonExistentReplacementPolicies() {
        final Response response = CheckoutV1Request.PUT(0L);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MultiretailerOrderV1Response.class);
        compareTwoObjects(response.as(MultiretailerOrderV1Response.class).getReplacementPolicy(), null);
    }

    @Story("Checkout")
    @CaseId(2036)
    @Test(description = "Добавление несуществующего окна доставки",
            groups = {"api-instamart-smoke", "api-instamart-prod"},
            dependsOnMethods = {"addReplacementPolicies", "addNonExistentReplacementPolicies"})
    public void addNonExistentDeliveryWindow() {
        MultiretailerOrderShipmentV1 shipment = order.getShipments().get(0);
        List<NextDeliveryV2> filteredNextDeliveries = shipment.getNextDeliveries().stream().filter(d -> d.getId() >= 0).collect(Collectors.toList());
        NextDeliveryV2 nextDelivery = filteredNextDeliveries.get(0);
        nextDelivery.setId(0);
        final Response response = CheckoutV1Request.PUT(shipment, nextDelivery);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MultiretailerOrderV1Response.class);
        order = response.as(MultiretailerOrderV1Response.class);
        MultiretailerOrderShipmentV1 shipmentFromResponse = order.getShipments().get(0);
        compareTwoObjects(shipmentFromResponse.getDeliveryWindow(), null);
    }

    @Story("Checkout")
    @CaseId(2037)
    @Test(description = "Добавление окна доставки",
            groups = {"api-instamart-smoke", "api-instamart-prod"},
            dependsOnMethods = "addNonExistentDeliveryWindow")
    public void addDeliveryWindow() {
        MultiretailerOrderShipmentV1 shipment = order.getShipments().get(0);
        List<NextDeliveryV2> nextDeliveries = shipment.getNextDeliveries();
        List<NextDeliveryV2> filteredNextDeliveries = shipment.getNextDeliveries().stream().filter(d -> d.getId() >= 0).collect(Collectors.toList());
        final Response response = CheckoutV1Request.PUT(shipment, filteredNextDeliveries.get(0));
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MultiretailerOrderV1Response.class);
        MultiretailerOrderShipmentV1 shipmentFromResponse = response.as(MultiretailerOrderV1Response.class).getShipments().get(0);
        final SoftAssert softAssert = new SoftAssert();
        int windowNumber = nextDeliveries.size() - filteredNextDeliveries.size();
        compareTwoObjects(shipmentFromResponse.getDeliveryWindowId(), (long) shipment.getNextDeliveries().get(windowNumber).getId(), softAssert);
        compareTwoObjects(shipmentFromResponse.getDeliveryWindow().getId(), (long) shipment.getNextDeliveries().get(windowNumber).getId(), softAssert);
        compareTwoObjects(shipmentFromResponse.getDeliveryWindow().getKind(), shipment.getNextDeliveries().get(windowNumber).getKind(), softAssert);
        softAssert.assertAll();
    }

    @Story("Checkout")
    @CaseId(2038)
    @Test(description = "Завершение заказа для физического лица c некорректным способом оплаты",
            groups = {"api-instamart-smoke", "api-instamart-prod"},
            dependsOnMethods = "addDeliveryWindow")
    public void completeOrderForPersonWithWrongParams() {
        UserData user = SessionFactory.getSession(SessionType.API_V1).getUserData();
        CheckoutV1Request.OrderAttributes orderAttributes = getOrderAttributes(user, 0L, OrderKindV1.HOME.getValue());

        final Response response = CheckoutV1Request.PUT(orderAttributes);
        checkStatusCode401(response);
        checkErrorText(response, "Пользователь не авторизован для этого действия");
    }

    @Story("Checkout")
    @CaseId(2039)
    @Test(description = "Завершение заказа для физического лица",
            groups = {"api-instamart-smoke", "api-instamart-prod"},
            dependsOnMethods = "completeOrderForPersonWithWrongParams")
    public void completeOrderForPerson() {
        UserData user = SessionFactory.getSession(SessionType.API_V1).getUserData();
        PaymentToolV1 paymentTool = apiV1.getPaymentTools().get(0);
        CheckoutV1Request.OrderAttributes orderAttributes = getOrderAttributes(user, paymentTool.getId(), OrderKindV1.HOME.getValue());

        final Response response = CheckoutV1Request.PUT(orderAttributes);
        if (response.statusCode() == 422 && response.as(ErrorsV1Response.class).getErrors().get(0).startsWith("Выбранный интервал стал недоступен")) throw new SkipException("Слот занят");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CompleteOrderV1Response.class);

        CompleteOrderV1Response completedOrder = response.as(CompleteOrderV1Response.class);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(completedOrder.getCompletedOrderId(), order.getId(), softAssert);
        compareTwoObjects(completedOrder.getShipmentNumber(), order.getShipments().get(0).getNumber(), softAssert);
        softAssert.assertAll();

        UserShipmentV1 userShipment = apiV1.getUserShipment(user.getId(), completedOrder.getShipmentNumber());
        compareWithUserShipment(userShipment, user, paymentTool, completedOrder);
    }

    @Story("Checkout")
    @CaseId(2040)
    @Test(description = "Завершение заказа для бизнеса",
            groups = {"api-instamart-smoke", "api-instamart-prod"},
            dependsOnMethods = "completeOrderForPerson")
    public void completeOrderForBusiness() {
        UserData user = SessionFactory.getSession(SessionType.API_V1).getUserData();
        apiV1.fillCart(address, ShippingMethodV2.BY_COURIER.getMethod(),offerId);
        MultiretailerOrderShipmentV1 shipment = apiV1.addReplacementPolicy();
        apiV1.addDeliveryWindow();
        PaymentToolV1 paymentTool = apiV1.getPaymentTools().get(0);
        CheckoutV1Request.OrderAttributes orderAttributes = getOrderAttributes(user, paymentTool.getId(), OrderKindV1.OFFICE.getValue());

        final Response response = CheckoutV1Request.PUT(orderAttributes);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CompleteOrderV1Response.class);

        CompleteOrderV1Response completedOrder = response.as(CompleteOrderV1Response.class);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(completedOrder.getCompletedOrderId(), shipment.getOrderId(), softAssert);
        compareTwoObjects(completedOrder.getShipmentNumber(), shipment.getNumber(), softAssert);
        softAssert.assertAll();

        UserShipmentV1 userShipment = apiV1.getUserShipment(user.getId(), completedOrder.getShipmentNumber());
        compareWithUserShipment(userShipment, user, paymentTool, completedOrder);
    }
}
