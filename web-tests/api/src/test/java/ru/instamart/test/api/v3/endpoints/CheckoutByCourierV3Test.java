package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
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
import ru.instamart.jdbc.dao.stf.OffersDao;
import ru.instamart.jdbc.dao.stf.PhoneTokensDao;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.instamart.api.checkpoint.ApiV3Checkpoints.*;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.api.helper.ApiV3Helper.checkFlipper;
import static ru.instamart.api.helper.ApiV3Helper.checkFlipperOff;

@Epic("ApiV3")
@Feature("Чекаут")
public class CheckoutByCourierV3Test extends RestBase {
    private MultiretailerOrderV1Response order;
    private AddressV2 addressDefaultSid;
    private AddressV2 addressSecondSid;
    private UserData user;
    private long offerDefaultSidId;
    private long offerSecondSidId;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        checkFlipper("checkout_web_force_all");
        addressDefaultSid = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID);
        offerDefaultSidId = apiV2.getProductFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_SID).get(0).getId();
        addressSecondSid = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        offerSecondSidId = apiV2.getProductFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID).get(0).getId();
    }

    @BeforeMethod(alwaysRun = true)
    public void auth() {
        user = UserManager.getQaUser();
        apiV1.authByPhone(user);
    }

    @CaseIDs(value = {@CaseId(2023), @CaseId(2025), @CaseId(2027), @CaseId(2029)})
    @Story("Инициализация")
    @Test(description = "Запрос на инициализацию с заказом созданным данным пользователем, доставка курьером",
            groups = "api-instamart-regress")
    public void initializeCheckoutWithCourier() {
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        order = apiV1.getMultiRetailerOrder();

        final Response response = CheckoutV3Request.Initialization.POST(order.getNumber(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode(response, 204);
    }

    @CaseId(2006)
    @Story("Получение данных заказа")
    @Test(description = "Получение данных для несуществующего заказа",
            groups = "api-instamart-regress")
    public void getNonExistentOrder() {
        final Response response = CheckoutV3Request.GET("failedOrderNumber");
        checkStatusCode404(response);
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        compareTwoObjects(errors.get(0).getMessage(), "Заказ не существует");
    }

    @CaseId(2005)
    @Story("Получение данных заказа")
    @Test(description = "Получение данных о заказе другого пользователя",
            groups = "api-instamart-regress")
    public void getAnotherUserOrder() {
        final Response response = CheckoutV3Request.GET(SpreeOrdersDao.INSTANCE.getOrderOfAnotherUser(Long.parseLong(user.getId())).getNumber());
        checkStatusCode403(response);
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        compareTwoObjects(errors.get(0).getMessage(), "Пользователь не может выполнить это действие");
    }

    @CaseIDs(value = {@CaseId(2007), @CaseId(2008), @CaseId(2009), @CaseId(2010), @CaseId(2011), @CaseId(2013), @CaseId(2015)})
    @Story("Валидация")
    @Test(description = "Запрос на валидацию с заказом, доступным для пользователя, курьерская доставка",
            groups = "api-instamart-regress")
    public void validateCourierOrder() {
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        order = apiV1.getMultiRetailerOrder();
        final Response initializeResponse = CheckoutV3Request.Initialization.POST(order.getNumber(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode(initializeResponse, 204);

        final Response response = CheckoutV3Request.Validation.GET(order.getNumber());
        checkStatusCode(response, 204);
    }

    @CaseId(2004)
    @Test(description = "Получение полных данных при вводе реального номера заказа",
            groups = "api-instamart-regress")
    @Story("Получение данных заказа")
    public void getOrder() {
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        order = apiV1.getMultiRetailerOrder();
        final Response initializeResponse = CheckoutV3Request.Initialization.POST(order.getNumber(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode(initializeResponse, 204);

        final Response response = CheckoutV3Request.GET(order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderV3Response.class);
        checkOrder(response, order, user, ShippingMethodV2.BY_COURIER.getMethod(), false);
    }

    @CaseId(2033)
    @Story("Инициализация")
    @Test(description = "Запрос на инициализацию с указанием существующего и несуществующего шипментов",
            groups = "api-instamart-regress")
    public void initializeCheckoutWithOneNonExistentShipment() {
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        order = apiV1.getMultiRetailerOrder();

        final Response response = CheckoutV3Request.Initialization.POST(order.getNumber(), List.of(order.getShipments().get(0).getNumber(), "failedNumber"));
        checkStatusCode422(response);
        checkError(response, "not_order_shipments", "Указаны невалидные шипменты для текущего заказа");
    }

    @CaseId(2031)
    @Story("Инициализация")
    @Test(description = "Запрос на инициализацию с пустым полем shipment_numbers",
            groups = "api-instamart-regress")
    public void initializeCheckoutWithoutBody() {
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        order = apiV1.getMultiRetailerOrder();

        final Response response = CheckoutV3Request.Initialization.POST(order.getNumber(), new ArrayList<>());
        checkStatusCode(response, 400);
        checkErrors(response, "parameter_missing", "shipment_numbers", "Отсутствует обязательный параметр");
    }

    @CaseId(2032)
    @Story("Инициализация")
    @Test(description = "Запрос на инициализацию с указанием несуществующего шипмента",
            groups = "api-instamart-regress")
    public void initializeCheckoutWithNonExistentShipment() {
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        order = apiV1.getMultiRetailerOrder();

        final Response response = CheckoutV3Request.Initialization.POST(order.getNumber(), Collections.singletonList("failedNumber"));
        checkStatusCode422(response);
        checkError(response, "not_order_shipments", "Указаны невалидные шипменты для текущего заказа");
    }

    @CaseId(2030)
    @Story("Инициализация")
    @Test(description = "Запрос на инициализацию со стоимостью заказа ниже минимально допустимой",
            groups = "api-instamart-regress")
    public void initializeCheckoutWithInvalidCost() {
        long offerId = OffersDao.INSTANCE.getOfferWithSpecificPrice(EnvironmentProperties.DEFAULT_SID, 500).getId();
        apiV1.changeAddress(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod());
        apiV1.addItemToCart(offerId);
        order = apiV1.getMultiRetailerOrder();

        final Response response = CheckoutV3Request.Initialization.POST(order.getNumber(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode422(response);
        compareTwoObjects(response.as(ErrorV3Response.class).getType(), "add_products_to_gain_min_order_amount");
    }

    @CaseId(2017)
    @Story("Валидация")
    @Test(description = "Запрос на валидацию со стоимостью заказа ниже минимально допустимой",
            groups = "api-instamart-regress")
    public void validateCheckoutWithInvalidCost() {
        long offerId = OffersDao.INSTANCE.getOfferWithSpecificPrice(EnvironmentProperties.DEFAULT_SID, 500).getId();
        apiV1.changeAddress(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod());
        apiV1.addItemToCart(offerId);
        order = apiV1.getMultiRetailerOrder();

        final Response initializeResponse = CheckoutV3Request.Initialization.POST(order.getNumber(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode422(initializeResponse);
        compareTwoObjects(initializeResponse.as(ErrorV3Response.class).getType(), "add_products_to_gain_min_order_amount");

        final Response response = CheckoutV3Request.Validation.GET(order.getNumber());
        checkStatusCode422(response);
        compareTwoObjects(response.as(ErrorV3Response.class).getType(), "missing_shipments_on_checkout");
    }

    @CaseId(2018)
    @Story("Валидация")
    @Test(description = "Запрос на валидацию, когда не выбран магазин для оформления",
            groups = "api-instamart-regress")
    public void validateCheckoutWithInvalidAddress() {
        AddressV2 address = AddressV2.builder()
                .city("Норильск")
                .street("Комсомольская")
                .lat(69.345853)
                .lon(88.203044)
                .building("12")
                .fullAddress("Норильск, ул. Комсомольская, д. 12")
                .build();
        long offerId = OffersDao.INSTANCE.getOfferWithSpecificPrice(EnvironmentProperties.DEFAULT_SID, 500).getId();
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerId);
        apiV1.changeAddress(address, ShippingMethodV2.BY_COURIER.getMethod());
        order = apiV1.getMultiRetailerOrder();

        final Response response = CheckoutV3Request.Validation.GET(order.getNumber());
        checkStatusCode422(response);
        checkError(response, "missing_shipments_on_checkout", "Не выбран магазин для оформления заказа");
    }

    @CaseId(2016)
    @Story("Валидация")
    @Test(description = "Запрос на валидацию с заказом без статуса в 'В корзине'",
            groups = "api-instamart-regress")
    public void validateCheckoutWithoutCart() {
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        order = apiV1.getMultiRetailerOrder();
        SpreeOrdersDao.INSTANCE.updateShipmentState(order.getNumber(), OrderStatusV2.COMPLETE.getStatus());

        final Response response = CheckoutV3Request.Initialization.POST(order.getNumber(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode422(response);
        checkError(response, "not_correct_order_state", "Заказ должен быть со статусом 'В корзине'");
    }

    @CaseId(2057)
    @Story("Валидация")
    @Test(description = "Запрос на валидацию когда в ордере нет шипментов",
            groups = "api-instamart-regress")
    public void validateCheckoutWithoutShipment() {
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        order = apiV1.getMultiRetailerOrder();
        apiV1.deleteShipment(order.getShipments().get(0).getNumber(), order.getToken());

        final Response response = CheckoutV3Request.Validation.GET(order.getNumber());
        checkStatusCode422(response);
        checkError(response, "missing_shipments_on_checkout", "Не выбран магазин для оформления заказа");
    }

    @CaseId(2019)
    @Story("Валидация")
    @Test(description = "Запрос на валидацию когда shipment не в валидном статусе",
            groups = "api-instamart-regress")
    public void validateCheckoutWithIncorrectShipment() {
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        order = apiV1.getMultiRetailerOrder();
        final Response initializeResponse = CheckoutV3Request.Initialization.POST(order.getNumber(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode(initializeResponse, 204);

        SpreeShipmentsDao.INSTANCE.updateShipmentState(order.getShipments().get(0).getNumber(), OrderStatusV2.COLLECTING.getStatus());
        final Response response = CheckoutV3Request.Validation.GET(order.getNumber());
        checkStatusCode422(response);
        checkError(response, "unavailable_shipment_state", "Доставка не в валидном статусе");
    }

    @CaseId(2021)
    @Story("Валидация")
    @Test(description = "Запрос на валидацию, когда shipments относятся к разным магазинам",
            groups = "api-instamart-regress")
    public void validateCheckoutWithDifferentShipment() {
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        var firstShipment = apiV1.getMultiRetailerOrder().getShipments().get(0).getNumber();

        apiV1.fillCart(addressSecondSid, ShippingMethodV2.BY_COURIER.getMethod(), offerSecondSidId);
        order = apiV1.getMultiRetailerOrder();

        final Response initializeResponse = CheckoutV3Request.Initialization.POST(order.getNumber(), Arrays.asList(order.getShipments().get(0).getNumber(), firstShipment));
        checkError(initializeResponse, "not_order_shipments", "Указаны невалидные шипменты для текущего заказа");

        final Response response = CheckoutV3Request.Validation.GET(order.getNumber());
        checkStatusCode422(response);
        checkError(response, "missing_shipments_on_checkout", "Не выбран магазин для оформления заказа");
    }

    @CaseId(2022)
    @Story("Валидация")
    @Test(description = "Запрос на валидацию на старом чек-ауте",
            groups = "api-instamart-regress",
            priority = 100)
    public void validateCheckoutWithOldCheckout() {
        checkFlipperOff("checkout_web_force_all");
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        order = apiV1.getMultiRetailerOrder();

        final Response response = CheckoutV3Request.Initialization.POST(order.getNumber(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode404(response);
        compareTwoObjects(response.as(ErrorsV3.class).getErrors(), "Оформить на новом чекауте не получится, сейчас мы вернем вас в старый интерфейс");
    }

    @CaseId(2020)
    @Story("Валидация")
    @Test(description = "Запрос на валидацию без подтвержденного номера телефона",
            groups = "api-instamart-regress")
    public void validateCheckoutWithoutPhoneApproval() {
        PhoneTokensDao.INSTANCE.deletePhoneTokenByUserId(user.getId());
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId);
        order = apiV1.getMultiRetailerOrder();

        final Response response = CheckoutV3Request.Initialization.POST(order.getNumber(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode422(response);
        checkError(response, "need_confirm_phone", "Необходимо подтвердить номер телефона");
    }
}

