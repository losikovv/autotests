package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.OrderStatusV2;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v3.CheckoutOrderV3;
import ru.instamart.api.model.v3.ErrorV3;
import ru.instamart.api.model.v3.PaymentToolV3;
import ru.instamart.api.request.v3.CheckoutV3Request;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.api.response.v3.ErrorsV3Response;
import ru.instamart.api.response.v3.OrderV3Response;
import ru.instamart.api.response.v3.PaymentToolsV3Response;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Collections;
import java.util.List;

import static ru.instamart.api.checkpoint.ApiV3Checkpoints.checkError;
import static ru.instamart.api.checkpoint.ApiV3Checkpoints.checkOrderPaymentTools;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV3")
@Feature("Чекаут")
public class PaymentToolsV3Test extends RestBase {

    private PaymentToolV3 paymentTool;
    private UserData user;
    private MultiretailerOrderV1Response order;
    private String anotherUserOrderNumber;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        AddressV2 addressDefaultSid = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID);
        long offerDefaultSidId = apiV2.getProductFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_SID).get(0).getId();
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId, EnvironmentProperties.DEFAULT_SID);
        order = apiV1.getMultiRetailerOrder();
        anotherUserOrderNumber = SpreeOrdersDao.INSTANCE.getOrderOfAnotherUser(Long.parseLong(user.getId())).getNumber();
    }

    @CaseId(2476)
    @Story("Способы оплаты")
    @Test(description = "Получение способов оплаты по своему заказу",
            groups = "api-instamart-regress")
    public void getPaymentTools() {
        final Response response = CheckoutV3Request.PaymentTools.GET(order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PaymentToolsV3Response.class);
        paymentTool = response.as(PaymentToolsV3Response.class).getPaymentTools().get(0);
    }

    @CaseId(2477)
    @Story("Способы оплаты")
    @Test(description = "Получение способов оплаты по чужому заказу",
            groups = "api-instamart-regress")
    public void getPaymentToolsForAnotherUserOrder() {
        final Response response = CheckoutV3Request.PaymentTools.GET(anotherUserOrderNumber);
        checkStatusCode403(response);
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        compareTwoObjects(errors.get(0).getMessage(), "Пользователь не может выполнить это действие");
    }

    @CaseId(2479)
    @Story("Способы оплаты")
    @Test(description = "Получение способов оплаты по несуществующему заказу",
            groups = "api-instamart-regress")
    public void getPaymentToolsForNonExistentOrder() {
        final Response response = CheckoutV3Request.PaymentTools.GET("failedOrderNumber");
        checkStatusCode404(response);
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        compareTwoObjects(errors.get(0).getMessage(), "Заказ не существует");
    }

    @CaseId(2481)
    @Story("Способы оплаты")
    @Test(description = "Сохранение способа оплаты по своему заказу",
            groups = "api-instamart-regress",
            dependsOnMethods = "getPaymentTools")
    public void addPaymentTools() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .paymentAttributes(CheckoutV3Request.PaymentAttributes.builder()
                                .paymentToolId(paymentTool.getId())
                                .build())
                        .build())
                .shipmentNumbers(Collections.singletonList(order.getShipments().get(0).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderV3Response.class);
        CheckoutOrderV3 orderFromResponse = response.as(OrderV3Response.class).getOrder();
        checkOrderPaymentTools(orderFromResponse, paymentTool);
    }

    @CaseId(2482)
    @Story("Способы оплаты")
    @Test(description = "Сохранение способа оплаты по чужому заказу",
            groups = "api-instamart-regress",
            dependsOnMethods = "getPaymentTools")
    public void addPaymentToolsWithAnotherUserOrder() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .paymentAttributes(CheckoutV3Request.PaymentAttributes.builder()
                                .paymentToolId(paymentTool.getId())
                                .build())
                        .build())
                .shipmentNumbers(Collections.singletonList(order.getShipments().get(0).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, anotherUserOrderNumber);
        checkStatusCode403(response);
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        compareTwoObjects(errors.get(0).getMessage(), "Пользователь не может выполнить это действие");
    }

    @CaseId(2483)
    @Story("Способы оплаты")
    @Test(description = "Сохранение способа оплаты по несуществующему заказу",
            groups = "api-instamart-regress",
            dependsOnMethods = "getPaymentTools")
    public void addPaymentToolsWithNonExistentOrder() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .paymentAttributes(CheckoutV3Request.PaymentAttributes.builder()
                                .paymentToolId(paymentTool.getId())
                                .build())
                        .build())
                .shipmentNumbers(Collections.singletonList(order.getShipments().get(0).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, "failedOrderNumber");
        checkStatusCode404(response);
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        compareTwoObjects(errors.get(0).getMessage(), "Заказ не существует");
    }

    @CaseId(2486)
    @Story("Способы оплаты")
    @Test(description = "Сохранение несуществующего способа оплаты",
            groups = "api-instamart-regress")
    public void addPaymentToolsWithNonExistentPaymentTool() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .paymentAttributes(CheckoutV3Request.PaymentAttributes.builder()
                                .paymentToolId(1111110L)
                                .build())
                        .build())
                .shipmentNumbers(Collections.singletonList(order.getShipments().get(0).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode404(response);
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        compareTwoObjects(errors.get(0).getMessage(), "Платежное средство не существует");
    }

    @CaseId(2529)
    @Story("Способы оплаты")
    @Test(description = "Сохранение способа оплаты с шипментом не относящимся к заказу",
            groups = "api-instamart-regress",
            dependsOnMethods = "getPaymentTools")
    public void addPaymentToolsWithAnotherOrderShipment() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .paymentAttributes(CheckoutV3Request.PaymentAttributes.builder()
                                .paymentToolId(paymentTool.getId())
                                .build())
                        .build())
                .shipmentNumbers(Collections.singletonList(SpreeShipmentsDao.INSTANCE.getShipmentOfAnotherUser(Long.parseLong(user.getId())).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode422(response);
        checkError(response, "invalid_current_shipments", "Вы уже оформляете заказ в другом магазине");
    }

    @CaseId(2530)
    @Story("Способы оплаты")
    @Test(description = "Сохранение способа оплаты с несуществующим шипментом",
            groups = "api-instamart-regress",
            dependsOnMethods = "getPaymentTools")
    public void addPaymentToolsWithNonExistentShipment() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .paymentAttributes(CheckoutV3Request.PaymentAttributes.builder()
                                .paymentToolId(paymentTool.getId())
                                .build())
                        .build())
                .shipmentNumbers(Collections.singletonList("failedShipmentNumber"))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode422(response);
        checkError(response, "invalid_current_shipments", "Вы уже оформляете заказ в другом магазине");
    }

    @CaseId(2484)
    @Story("Способы оплаты")
    @Test(description = "Сохранение способа оплаты по просроченному заказу",
            groups = "api-instamart-regress",
            dependsOnMethods = {"addPaymentToolsWithNonExistentShipment", "getPaymentToolsForAnotherUserOrder", "getPaymentToolsForNonExistentOrder"})
    public void addPaymentToolsForCanceledOrder() {
        SpreeOrdersDao.INSTANCE.updateShipmentState(order.getNumber(), OrderStatusV2.CANCELED.getStatus());
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .paymentAttributes(CheckoutV3Request.PaymentAttributes.builder()
                                .paymentToolId(paymentTool.getId())
                                .build())
                        .build())
                .shipmentNumbers(Collections.singletonList(order.getShipments().get(0).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode422(response);
        checkError(response, "not_correct_order_state", "Заказ должен быть со статусом 'В корзине'");
    }

    @CaseId(2485)
    @Story("Способы оплаты")
    @Test(description = "Сохранение способа оплаты неавторизованным пользователем",
            groups = "api-instamart-regress",
            dependsOnMethods = "addPaymentToolsForCanceledOrder")
    public void addPaymentToolsWithoutAuth() {
        SessionFactory.clearSession(SessionType.API_V1);
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .paymentAttributes(CheckoutV3Request.PaymentAttributes.builder()
                                .paymentToolId(paymentTool.getId())
                                .build())
                        .build())
                .shipmentNumbers(Collections.singletonList(order.getShipments().get(0).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode401(response);
    }

    @CaseId(2478)
    @Story("Способы оплаты")
    @Test(description = "Получение способов оплаты неавторизованным пользователем",
            groups = "api-instamart-regress",
            dependsOnMethods = "addPaymentToolsWithoutAuth")
    public void getPaymentToolsWithoutAuth() {
        final Response response = CheckoutV3Request.PaymentTools.GET(order.getNumber());
        checkStatusCode401(response);
    }
}
