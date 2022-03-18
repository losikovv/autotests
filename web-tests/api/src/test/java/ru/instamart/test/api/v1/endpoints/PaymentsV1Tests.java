package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v1.PaymentToolV1;
import ru.instamart.api.model.v1.PaymentV1;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.request.v1.OrdersV1Request;
import ru.instamart.api.response.v1.*;
import ru.instamart.jdbc.dao.SpreePaymentsDao;
import ru.instamart.jdbc.entity.SpreePaymentsEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;
import java.util.stream.Collectors;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkPayment;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;
import static ru.instamart.api.helper.K8sHelper.changeToShip;

@Epic("ApiV1")
@Feature("Оплата")
public class PaymentsV1Tests extends RestBase {

    private MultiretailerOrderV1Response order;
    private Long paymentToolId;
    private Long paymentId;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authApi();
        AddressV2 address = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID);
        Long offerId = apiV2.getProductFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_SID).get(0).getId();
        order = apiV1.order(address, offerId, SessionFactory.getSession(SessionType.API_V1).getUserData());
    }

    @Skip(onServer = Server.STAGING) // Проблема с добавлением карт
    @CaseId(2280)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о способах платежа для заказа")
    public void getPaymentToolsForOrder() {
        final Response response = OrdersV1Request.PaymentTools.GET(order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PaymentToolsV1Response.class);
        List<PaymentToolV1> filteredPaymentTools = response.as(PaymentToolsV1Response.class).getPaymentTools().stream().filter(p -> p.getPaymentMethod().getName().equals("Картой онлайн")).collect(Collectors.toList());
        paymentToolId = filteredPaymentTools.get(0).getId();
    }

    @CaseId(2281)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о способах платежа для несуществующего заказа")
    public void getPaymentToolsForNonExistentOrder() {
        final Response response = OrdersV1Request.PaymentTools.GET("failedOrderNumber");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2282)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание платежа для заказа",
            dependsOnMethods = "getPaymentToolsForOrder")
    public void createPayment() {
        final Response response = OrdersV1Request.Payments.POST(order.getNumber(), paymentToolId);
        checkStatusCode200(response);
        SpreePaymentsEntity paymentFromDb = SpreePaymentsDao.INSTANCE.getPaymentByOrderId(order.getId());
        checkFieldIsNotEmpty(paymentFromDb, "платеж в БД");
        paymentId = paymentFromDb.getId();
    }

    @CaseId(2283)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание платежа для заказа c несуществующим способом оплаты")
    public void createPaymentWithNonExistentTool() {
        final Response response = OrdersV1Request.Payments.POST(order.getNumber(), 0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2284)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание платежа для несуществующего заказа",
            dependsOnMethods = "getPaymentToolsForOrder")
    public void createPaymentForNonExistentOrder() {
        final Response response = OrdersV1Request.Payments.POST("failedOrderNumber", paymentToolId);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2285)
    @Test(groups = {"api-instamart-regress"},
            description = "Оплата заказа",
            dependsOnMethods = "createPayment")
    public void purchaseOrder() {
        changeToShip(order.getShipments().get(0).getNumber());
        final Response response = OrdersV1Request.Payments.PUT(order.getNumber(), paymentId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderPaymentV1Response.class);
        PaymentV1 paymentFromResponse = response.as(OrderPaymentV1Response.class).getOrderPayment();
        checkPayment(paymentFromResponse, order);
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2286)
    @Test(groups = {"api-instamart-regress"},
            description = "Оплата несуществующего заказа",
            dependsOnMethods = "purchaseOrder")
    public void purchaseNonExistentOrder() {
        final Response response = OrdersV1Request.Payments.PUT("failedOrderNumber", paymentId);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2287)
    @Test(groups = {"api-instamart-regress"},
            description = "Оплата несуществующего счета",
            dependsOnMethods = "purchaseOrder")
    public void purchaseNonExistentOPayment() {
        final Response response = OrdersV1Request.Payments.PUT(order.getNumber(), 0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2288)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о платеже",
            dependsOnMethods = "purchaseOrder")
    public void getPayment() {
        final Response response = OrdersV1Request.Payments.GET(order.getNumber(), paymentId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PaymentV1Response.class);
        PaymentV1 paymentFromResponse = response.as(PaymentV1Response.class).getPayment();
        checkPayment(paymentFromResponse, order);
    }

    @CaseId(2289)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о несуществующем платеже")
    public void getNonExistentPayment() {
        final Response response = OrdersV1Request.Payments.GET(order.getNumber(), 0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2290)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о платеже для несуществующего заказа",
            dependsOnMethods = "createPayment")
    public void getPaymentForNonExistentOrder() {
        final Response response = OrdersV1Request.Payments.GET("failedOrderNumber", paymentId);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2291)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение всех платежей по заказу",
            dependsOnMethods = "purchaseOrder")
    public void getPayments() {
        final Response response = OrdersV1Request.Payments.GET(order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PaymentsV1Response.class);
        List<PaymentV1> paymentsFromResponse = response.as(PaymentsV1Response.class).getPayments();
        int paymentsFromDbCount = SpreePaymentsDao.INSTANCE.getCountByOrderId(order.getId());
        compareTwoObjects(paymentsFromResponse.size(), paymentsFromDbCount);
    }

    @CaseId(2292)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение всех платежей по несуществующему заказу")
    public void getPaymentsForNonExistentOrder() {
        final Response response = OrdersV1Request.Payments.GET("failedOrderNumber");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }
}
