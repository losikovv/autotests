package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.v2.OrderStatusV2;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v3.ErrorV3;
import ru.instamart.api.model.v3.ErrorsV3;
import ru.instamart.api.request.v3.CheckoutV3Request;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.api.response.v3.ErrorV3Response;
import ru.instamart.api.response.v3.ErrorsV3Response;
import ru.instamart.api.response.v3.OrderV3Response;
import ru.instamart.jdbc.dao.stf.*;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.instamart.api.checkpoint.ApiV3Checkpoints.checkError;
import static ru.instamart.api.checkpoint.ApiV3Checkpoints.checkOrder;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.api.helper.ApiV3Helper.*;

@Epic("ApiV3")
@Feature("Чекаут")
public class CheckoutByCourierV3Test extends RestBase {

    private MultiretailerOrderV1Response order;
    private AddressV2 addressDefaultSid;
    private long offerDefaultSidId;
    private AddressV2 addressDefaultMoscowSid;
    private long offerDefaultMoscowSidId;
    private UserData user;
    private String shipmentNumber;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        checkFlipper("checkout_web_force_all");
        user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        apiV2.authByQA(user);
        addressDefaultSid = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID);
        offerDefaultSidId = apiV2.getProductFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_SID).get(0).getId();
        addressDefaultMoscowSid = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        offerDefaultMoscowSidId = apiV2.getProductFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID).get(0).getId();
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        order = apiV1.getMultiRetailerOrder();
    }


    @CaseIDs(value = {@CaseId(2023), @CaseId(2025), @CaseId(2027), @CaseId(2029)})
    @Test(description = "Запрос на инициализацию с заказом созданным данным пользователем, доставка курьером",
            groups = "api-instamart-regress")
    public void initializeCheckoutWithCourier() {
        final Response response = CheckoutV3Request.POST(order.getNumber(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode(response, 204);
    }

    @CaseId(2006)
    @Test(description = "Получение данных для несуществующего заказа",
            groups = "api-instamart-regress")
    public void getNonExistentOrder() {
        final Response response = CheckoutV3Request.GET("failedOrderNumber");
        checkStatusCode404(response);
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        compareTwoObjects(errors.get(0).getMessage(), "Заказ не существует");
    }

    @CaseId(2005)
    @Test(description = "Получение данных о заказе другого пользователя",
            groups = "api-instamart-regress")
    public void getAnotherUserOrder() {
        final Response response = CheckoutV3Request.GET(SpreeOrdersDao.INSTANCE.getOrder(Long.parseLong(user.getId())).getNumber());
        checkStatusCode403(response);
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        compareTwoObjects(errors.get(0).getMessage(), "Пользователь не может выполнить это действие");
    }

    @CaseIDs(value = {@CaseId(2007), @CaseId(2008), @CaseId(2009), @CaseId(2010), @CaseId(2011), @CaseId(2013), @CaseId(2015)})
    @Test(description = "Запрос на валидацию с заказом, доступным для пользователя, курьерская доставка",
            groups = "api-instamart-regress",
            priority = 1)
    public void validateCourierOrder() {
        final Response response = CheckoutV3Request.Validation.GET(order.getNumber());
        checkStatusCode(response, 204);
    }

    @CaseId(2004)
    @Test(description = "Получение полных данных при вводе реального номера заказа",
            groups = "api-instamart-regress",
            priority = 1)
    public void getOrder() {
        final Response response = CheckoutV3Request.GET(order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderV3Response.class);
        checkOrder(response, order, user, ShippingMethodV2.BY_COURIER.getMethod(), false);
    }

    @CaseId(2033)
    @Test(description = "Запрос на инициализацию с указанием существующего и несуществующего шипментов",
            groups = "api-instamart-regress",
            priority = 2)
    public void initializeCheckoutWithOneNonExistentShipment() {
        final Response response = CheckoutV3Request.POST(order.getNumber(), List.of(order.getShipments().get(0).getNumber(), "failedNumber"));
        checkStatusCode422(response);
        checkError(response, "not_order_shipments", "Указаны невалидные шипменты для текущего заказа");
    }

    @CaseId(2031)
    @Test(description = "Запрос на инициализацию с пустым полем shipment_numbers",
            groups = "api-instamart-regress",
            priority = 2)
    public void initializeCheckoutWithoutBody() {
        final Response response = CheckoutV3Request.POST(order.getNumber(), new ArrayList<>());
        checkStatusCode(response, 400);
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(errors.get(0).getType(), "parameter_missing", softAssert);
        compareTwoObjects(errors.get(0).getField(), "shipment_numbers", softAssert);
        compareTwoObjects(errors.get(0).getMessage(), "Отсутствует обязательный параметр", softAssert);
        softAssert.assertAll();
    }

    @CaseId(2032)
    @Test(description = "Запрос на инициализацию с указанием несуществующего шипмента",
            groups = "api-instamart-regress",
            priority = 2)
    public void initializeCheckoutWithNonExistentShipment() {
        final Response response = CheckoutV3Request.POST(order.getNumber(), Collections.singletonList("failedNumber"));
        checkStatusCode422(response);
        checkError(response, "not_order_shipments", "Указаны невалидные шипменты для текущего заказа");
    }

    @CaseId(2030)
    @Test(description = "Запрос на инициализацию со стоимостью заказа ниже минимально допустимой",
            groups = "api-instamart-regress",
            priority = 3)
    public void initializeCheckoutWithInvalidCost() {
        apiV1.deleteShipment(order.getShipments().get(0).getNumber(), order.getToken());
        long offerId = OffersDao.INSTANCE.getOfferWithSpecificPrice(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID, 500).getId();
        apiV1.fillCart(addressDefaultMoscowSid, ShippingMethodV2.BY_COURIER.getMethod(), offerId);
        order = apiV1.getMultiRetailerOrder();
        final Response response = CheckoutV3Request.POST(order.getNumber(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode422(response);
        compareTwoObjects(response.as(ErrorV3Response.class).getType(), "add_products_to_gain_min_order_amount");
    }

    @CaseId(2017)
    @Test(description = "Запрос на валидацию со стоимостью заказа ниже минимально допустимой",
            groups = "api-instamart-regress",
            priority = 4)
    public void validateCheckoutWithInvalidCost() {
        apiV1.deleteShipment(order.getShipments().get(0).getNumber(), order.getToken());
        long offerId = OffersDao.INSTANCE.getOfferWithSpecificPrice(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID, 500).getId();
        apiV1.fillCart(addressDefaultMoscowSid, ShippingMethodV2.BY_COURIER.getMethod(), offerId);
        order = apiV1.getMultiRetailerOrder();
        final Response response = CheckoutV3Request.Validation.GET(order.getNumber());
        checkStatusCode422(response);
        compareTwoObjects(response.as(ErrorV3Response.class).getType(), "add_products_to_gain_min_order_amount");
    }

    @CaseId(2018)
    @Test(description = "Запрос на валидацию, когда не выбран магазин для оформления",
            groups = "api-instamart-regress",
            priority = 5)
    public void validateCheckoutWithInvalidAddress() {
        AddressV2 address = AddressV2.builder()
                .city("Норильск")
                .street("Комсомольская")
                .lat(69.345853)
                .lon(88.203044)
                .building("12")
                .fullAddress("Норильск, ул. Комсомольская, д. 12")
                .build();
        apiV1.deleteShipment(order.getShipments().get(0).getNumber(), order.getToken());
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        apiV1.changeAddress(address, ShippingMethodV2.BY_COURIER.getMethod());
        order = apiV1.getMultiRetailerOrder();
        final Response response = CheckoutV3Request.Validation.GET(order.getNumber());
        checkStatusCode422(response);
        checkError(response, "missing_shipments_on_checkout", "Не выбран магазин для оформления заказа");
    }

    @CaseId(2016)
    @Test(description = "Запрос на валидацию с заказом без статуса в \"В корзине\"",
            groups = "api-instamart-regress",
            priority = 6)
    public void validateCheckoutWithoutCart() {
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        order = apiV1.getMultiRetailerOrder();
        shipmentNumber = order.getShipments().get(0).getNumber();
        SpreeOrdersDao.INSTANCE.updateShipmentState(order.getNumber(), OrderStatusV2.COMPLETE.getStatus());
        final Response response = CheckoutV3Request.Validation.GET(order.getNumber());
        checkStatusCode422(response);
        checkError(response, "not_correct_order_state", "Заказ должен быть со статусом 'В корзине'");
    }

    @CaseId(2057)
    @Test(description = "Запрос на валидацию когда в ордере нет шипментов",
            groups = "api-instamart-regress",
            priority = 7)
    public void validateCheckoutWithoutShipment() {
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        order = apiV1.getMultiRetailerOrder();
        apiV1.deleteShipment(order.getShipments().get(0).getNumber(), order.getToken());
        final Response response = CheckoutV3Request.Validation.GET(order.getNumber());
        checkStatusCode422(response);
        checkError(response, "missing_shipments_on_checkout", "Не выбран магазин для оформления заказа");
    }

    @CaseId(2019)
    @Test(description = "Запрос на валидацию когда shipment не в валидном статусе",
            groups = "api-instamart-regress",
            priority = 8)
    public void validateCheckoutWithIncorrectShipment() {
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        order = apiV1.getMultiRetailerOrder();
        SpreeShipmentsDao.INSTANCE.updateShipmentState(order.getShipments().get(0).getNumber(), OrderStatusV2.COLLECTING.getStatus());
        final Response response = CheckoutV3Request.Validation.GET(order.getNumber());
        checkStatusCode422(response);
        checkError(response, "unavailable_shipment_state", "Доставка не в валидном статусе");
    }

    @CaseId(2021)
    @Test(description = "Запрос на валидацию, когда shipments относятся к разным магазинам",
            groups = "api-instamart-regress",
            priority = 9)
    public void validateCheckoutWithDifferentShipment() {
        apiV1.fillCart(addressDefaultMoscowSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultMoscowSidId);
        order = apiV1.getMultiRetailerOrder();
        SpreeShipmentsDao.INSTANCE.updateShipmentOrderByNumber(order.getId(), shipmentNumber);
        final Response response = CheckoutV3Request.Validation.GET(order.getNumber());
        checkStatusCode422(response);
        checkError(response, "shipments_have_other_stores", "Выбранные доставки относятся к разным магазинам");
    }

    @CaseId(2022)
    @Test(description = "Запрос на валидацию на старом чек-ауте",
            groups = "api-instamart-regress",
            priority = 10)
    public void validateCheckoutWithOldCheckout() {
        FlipperGatesDao.INSTANCE.deleteFlipper("checkout_web_force_all");
        apiV1.deleteShipment(order.getShipments().get(0).getNumber(), order.getToken());
        apiV1.fillCart(addressDefaultMoscowSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultMoscowSidId);
        order = apiV1.getMultiRetailerOrder();
        final Response response = CheckoutV3Request.Validation.GET(order.getNumber());
        checkStatusCode404(response);
        compareTwoObjects(response.as(ErrorsV3.class).getErrors(), "Оформить на новом чекауте не получится, сейчас мы вернем вас в старый интерфейс");
    }

    @CaseId(2020)
    @Test(description = "Запрос на валидацию без подтвержденного номера телефона",
            groups = "api-instamart-regress",
            priority = 11)
    public void validateCheckoutWithoutPhoneApproval() {
        checkFlipper("checkout_web_force_all");
        PhoneTokensDao.INSTANCE.deletePhoneTokenByUserId(user.getId());
        apiV1.deleteShipment(order.getShipments().get(0).getNumber(), order.getToken());
        apiV1.fillCart(addressDefaultMoscowSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultMoscowSidId);
        order = apiV1.getMultiRetailerOrder();
        final Response response = CheckoutV3Request.Validation.GET(order.getNumber());
        checkStatusCode422(response);
        checkError(response, "need_confirm_phone", "Необходимо подтвердить номер телефона");
    }
}
