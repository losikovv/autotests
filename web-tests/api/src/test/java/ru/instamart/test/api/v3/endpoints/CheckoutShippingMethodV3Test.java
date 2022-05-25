package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v3.CheckoutDeliveryWindowV3;
import ru.instamart.api.model.v3.ErrorV3;
import ru.instamart.api.request.v3.CheckoutV3Request;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.api.response.v3.ErrorsV3Response;
import ru.instamart.api.response.v3.OrderV3Response;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Collections;
import java.util.List;

import static ru.instamart.api.checkpoint.ApiV3Checkpoints.checkError;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.api.helper.ApiV3Helper.checkFlipper;

@Epic("ApiV3")
@Feature("Чекаут")
public class CheckoutShippingMethodV3Test extends RestBase {

    private MultiretailerOrderV1Response order;
    private UserData user;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        AddressV2 addressDefaultMoscowSid = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        long offerDefaultMoscowSidId = apiV2.getProductFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID).get(0).getId();
        apiV1.changeAddress(addressDefaultMoscowSid, ShippingMethodV2.BY_COURIER.getMethod());
        apiV1.fillCart(addressDefaultMoscowSid, ShippingMethodV2.PICKUP.getMethod(), offerDefaultMoscowSidId, EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        order = apiV1.getMultiRetailerOrder();
    }

    @CaseId(2670)
    @Story("Изменение метода доставки")
    @Test(description = "Запрос на переключение способа получения с доставки на самовывоз",
            groups = "api-instamart-regress")
    public void changeShippingMethodToPickup() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .shippingMethod(CheckoutV3Request.ShippingMethod.builder()
                                .kind(ShippingMethodV2.PICKUP.getMethod())
                                .pickupStoreId(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID)
                                .build())
                        .build())
                .shipmentNumbers(Collections.singletonList(order.getShipments().get(0).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderV3Response.class);
        compareTwoObjects(response.as(OrderV3Response.class).getOrder().getShippingMethodKind(), ShippingMethodV2.PICKUP.getMethod());
    }

    @CaseId(2671)
    @Story("Изменение метода доставки")
    @Test(description = "Запрос на переключение способа получения с самовывоза на доставку",
            groups = "api-instamart-regress",
            dependsOnMethods = "changeShippingMethodToPickup")
    public void changeShippingMethodToCourier() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .shippingMethod(CheckoutV3Request.ShippingMethod.builder()
                                .kind(ShippingMethodV2.BY_COURIER.getMethod())
                                .shipAddressId(order.getShipAddress().getId())
                                .build())
                        .build())
                .shipmentNumbers(Collections.singletonList(order.getShipments().get(0).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderV3Response.class);
        compareTwoObjects(response.as(OrderV3Response.class).getOrder().getShippingMethodKind(), ShippingMethodV2.BY_COURIER.getMethod());
    }

    @CaseId(2673)
    @Story("Изменение метода доставки")
    @Test(description = "Запрос на переключение способа получения в body указав только \"kind\": \"by_courier\"",
            groups = "api-instamart-regress")
    public void changeShippingMethodToCourierWithoutAddress() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .shippingMethod(CheckoutV3Request.ShippingMethod.builder()
                                .kind(ShippingMethodV2.BY_COURIER.getMethod())
                                .build())
                        .build())
                .shipmentNumbers(Collections.singletonList(order.getShipments().get(0).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode422(response);
        checkError(response, "ship_address_id", "Для способа доставки курьером, требуется указать ship_address_id");
    }

    @CaseId(2674)
    @Story("Изменение метода доставки")
    @Test(description = "Запрос на переключение способа получения в body указав только \"kind\": \"pickup\"",
            groups = "api-instamart-regress")
    public void changeShippingMethodToPickupWithoutStore() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .shippingMethod(CheckoutV3Request.ShippingMethod.builder()
                                .kind(ShippingMethodV2.PICKUP.getMethod())
                                .build())
                        .build())
                .shipmentNumbers(Collections.singletonList(order.getShipments().get(0).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode422(response);
        checkError(response, "pickup_store_id", "Для способа доставки самовывоз, требуется указать pickup_store_id");
    }

    @CaseId(2675)
    @Story("Изменение метода доставки")
    @Test(description = "Запрос на переключение способа получения для чужого заказа",
            groups = "api-instamart-regress")
    public void changeShippingMethodForAnotherUserOrder() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .shippingMethod(CheckoutV3Request.ShippingMethod.builder()
                                .kind(ShippingMethodV2.PICKUP.getMethod())
                                .pickupStoreId(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID)
                                .build())
                        .build())
                .shipmentNumbers(Collections.singletonList(order.getShipments().get(0).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, SpreeOrdersDao.INSTANCE.getOrderOfAnotherUser(Long.parseLong(user.getId())).getNumber());
        checkStatusCode403(response);
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        compareTwoObjects(errors.get(0).getMessage(), "Пользователь не может выполнить это действие");
    }

    @CaseId(2676)
    @Story("Изменение метода доставки")
    @Test(description = "Запрос на переключение способа получения для чужого шипмента",
            groups = "api-instamart-regress")
    public void changeShippingMethodForAnotherUserShipment() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .shippingMethod(CheckoutV3Request.ShippingMethod.builder()
                                .kind(ShippingMethodV2.PICKUP.getMethod())
                                .pickupStoreId(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID)
                                .build())
                        .build())
                .shipmentNumbers(Collections.singletonList(SpreeShipmentsDao.INSTANCE.getShipmentOfAnotherUser(Long.parseLong(user.getId())).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode422(response);
        checkError(response, "invalid_current_shipments", "Вы уже оформляете заказ в другом магазине");
    }
}
