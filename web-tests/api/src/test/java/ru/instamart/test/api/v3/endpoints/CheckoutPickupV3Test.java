package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v3.CheckoutOrderV3;
import ru.instamart.api.model.v3.PaymentToolV3;
import ru.instamart.api.request.v3.CheckoutV3Request;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.api.response.v3.OrderV3Response;
import ru.instamart.jdbc.dao.stf.SpreeProductsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Collections;

import static ru.instamart.api.checkpoint.ApiV3Checkpoints.checkOrder;
import static ru.instamart.api.checkpoint.ApiV3Checkpoints.checkOrderPaymentTools;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.ApiV3Helper.checkFlipper;


@Epic("ApiV3")
@Feature("Чекаут")
public class CheckoutPickupV3Test extends RestBase {

    private MultiretailerOrderV1Response order;
    private AddressV2 addressDefaultMoscowSid;
    private long offerDefaultMoscowSidId;
    private UserData user;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        addressDefaultMoscowSid = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        offerDefaultMoscowSidId = apiV2.getProductFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID).get(0).getId();
        checkFlipper("checkout_web_force_all");
        apiV1.changeAddress(addressDefaultMoscowSid, ShippingMethodV2.BY_COURIER.getMethod());
        apiV1.fillCart(addressDefaultMoscowSid, ShippingMethodV2.PICKUP.getMethod(), offerDefaultMoscowSidId, EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        order = apiV1.getMultiRetailerOrder();
    }

    @CaseIDs(value = {@CaseId(2026), @CaseId(2028)})
    @Story("Инициализация")
    @Test(description = "Запрос на инициализацию с заказом, созданным данным пользователем, самовывоз",
            groups = "api-instamart-regress")
    public void initializeCheckoutWithPickup() {
        final Response response = CheckoutV3Request.POST(order.getNumber(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode(response, 204);
    }

    @CaseIDs(value = {@CaseId(2012), @CaseId(2014)})
    @Story("Валидация")
    @Test(description = "Запрос на валидацию с заказом, доступным для пользователя, самовывоз",
            groups = "api-instamart-regress",
            dependsOnMethods = "initializeCheckoutWithPickup")
    public void validatePickupOrder() {
        final Response response = CheckoutV3Request.Validation.GET(order.getNumber());
        checkStatusCode(response, 204);
    }

    @CaseId(2047)
    @Story("Получение данных заказа")
    @Test(description = "Получение данных о заказе, содержащем алкоголь",
            groups = "api-instamart-regress",
            dependsOnMethods = "validatePickupOrder")
    public void getOrderWithAlcohol() {
        apiV1.deleteShipment(order.getShipments().get(0).getNumber(), order.getToken());
        long offerId = SpreeProductsDao.INSTANCE.getOfferIdForAlcohol(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        apiV1.fillCart(addressDefaultMoscowSid, ShippingMethodV2.PICKUP.getMethod(), offerId);
        order = apiV1.getMultiRetailerOrder();
        final Response response = CheckoutV3Request.GET(order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderV3Response.class);
        checkOrder(response, order, user, ShippingMethodV2.PICKUP.getMethod(), true);
    }

    @CaseId(2481)
    @Story("Способы оплаты")
    @Test(description = "Сохранение способа оплаты по заказу c алкоголем",
            groups = "api-instamart-regress",
            dependsOnMethods = "getOrderWithAlcohol")
    public void addPaymentToolsForOrderWithAlcohol() {
        PaymentToolV3 paymentTool = apiV3.getPaymentTools(order.getNumber()).get(0);
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
        compareTwoObjects(orderFromResponse.getPaymentTool().getName(), "На кассе");
        checkOrderPaymentTools(orderFromResponse, paymentTool);
    }
}
