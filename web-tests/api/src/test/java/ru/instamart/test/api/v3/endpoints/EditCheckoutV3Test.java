package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.ApiV3DataProvider;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v3.CheckoutOrderV3;
import ru.instamart.api.model.v3.ErrorV3;
import ru.instamart.api.request.v3.CheckoutV3Request;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.api.response.v3.ErrorsV3Response;
import ru.instamart.api.response.v3.OrderV3Response;
import ru.instamart.jdbc.dao.stf.SpreeAddressesDao;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Collections;
import java.util.List;

import static ru.instamart.api.checkpoint.ApiV3Checkpoints.checkError;
import static ru.instamart.api.checkpoint.ApiV3Checkpoints.checkErrors;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV3")
@Feature("Чекаут")
public class EditCheckoutV3Test extends RestBase {

    private MultiretailerOrderV1Response order;
    private AddressV2 addressDefaultSid;
    private long offerDefaultSidId;
    private UserData user;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        addressDefaultSid = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID);
        offerDefaultSidId = apiV2.getProductFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_SID).get(0).getId();
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId, EnvironmentProperties.DEFAULT_SID);
        order = apiV1.getMultiRetailerOrder();
    }

    @CaseIDs(value = {@CaseId(2080), @CaseId(2083)})
    @Story("Редактирование контактных данных в заказе")
    @Test(description = "Запрос с вводом валидных данных",
            groups = "api-instamart-regress")
    public void editUserCredentialsInOrder() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .email(Generate.email())
                        .phone("7" + Generate.phoneNumber())
                        .build())
                .shipmentNumbers(Collections.singletonList(order.getShipments().get(0).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderV3Response.class);
        CheckoutOrderV3 orderFromResponse = response.as(OrderV3Response.class).getOrder();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(orderFromResponse.getContacts().getEmail(), orderRequest.getOrder().getEmail(), softAssert);
        compareTwoObjects(orderFromResponse.getContacts().getPhone(), orderRequest.getOrder().getPhone(), softAssert);
        softAssert.assertAll();
    }

    @CaseId(2084)
    @Story("Редактирование контактных данных в заказе")
    @Test(description = "Запрос с несуществующим заказом",
            groups = "api-instamart-regress")
    public void editNonExistentOrder() {
        final Response response = CheckoutV3Request.PUT(CheckoutV3Request.OrderRequest.builder().build(), "failedOrderNumber");
        checkStatusCode404(response);
        checkErrors(response, "not_found", null, "Заказ не существует");
    }

    @CaseId(2085)
    @Story("Редактирование контактных данных в заказе")
    @Test(description = "Запрос с несуществующим shipments",
            groups = "api-instamart-regress")
    public void editOrderWithNonExistentShipments() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .email(Generate.email())
                        .phone("7" + Generate.phoneNumber())
                        .build())
                .shipmentNumbers(Collections.singletonList("failedShipmentNumber"))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode422(response);
        checkError(response, "invalid_current_shipments", "Вы уже оформляете заказ в другом магазине");
    }

    @CaseId(2086)
    @Story("Редактирование контактных данных в заказе")
    @Test(description = "Запрос с пустым order",
            groups = "api-instamart-regress")
    public void editOrderWithEmptyData() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .email(Generate.email())
                        .phone("7" + Generate.phoneNumber())
                        .build())
                .shipmentNumbers(Collections.singletonList(order.getShipments().get(0).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, "");
        checkStatusCode404(response);
    }

    @CaseId(2087)
    @Story("Редактирование контактных данных в заказе")
    @Test(description = "Запрос с пустым shipments",
            groups = "api-instamart-regress")
    public void editOrderWithEmptyShipments() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .email(Generate.email())
                        .phone("7" + Generate.phoneNumber())
                        .build())
                .shipmentNumbers(Collections.singletonList(""))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode422(response);
        checkError(response, "invalid_current_shipments", "Вы уже оформляете заказ в другом магазине");
    }

    @CaseId(2088)
    @Story("Редактирование контактных данных в заказе")
    @Test(description = "Запрос с существующим order другого пользователя",
            groups = "api-instamart-regress")
    public void editOrderWithAnotherUserOrder() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .email(Generate.email())
                        .phone("7" + Generate.phoneNumber())
                        .build())
                .shipmentNumbers(Collections.singletonList(order.getShipments().get(0).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, SpreeOrdersDao.INSTANCE.getOrderOfAnotherUser(Long.parseLong(user.getId())).getNumber());
        checkStatusCode403(response);
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        compareTwoObjects(errors.get(0).getMessage(), "Пользователь не может выполнить это действие");
    }

    @CaseId(2089)
    @Story("Редактирование контактных данных в заказе")
    @Test(description = "Запрос с существующим shipments другого пользователя",
            groups = "api-instamart-regress")
    public void editOrderWithAnotherUserShipments() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .email(Generate.email())
                        .phone("7" + Generate.phoneNumber())
                        .build())
                .shipmentNumbers(Collections.singletonList(SpreeShipmentsDao.INSTANCE.getShipmentOfAnotherUser(Long.parseLong(user.getId())).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode422(response);
        checkError(response, "invalid_current_shipments", "Вы уже оформляете заказ в другом магазине");
    }

    @CaseId(2090)
    @Story("Редактирование контактных данных в заказе")
    @Test(description = "Запрос с вводом невалидных данных",
            groups = "api-instamart-regress",
            dataProvider = "orderWithInvalidData",
            dataProviderClass = ApiV3DataProvider.class)
    public void editOrderWithInvalidCredentials(String email, String phone, String errorType) {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .email(email)
                        .phone(phone)
                        .build())
                .shipmentNumbers(Collections.singletonList(order.getShipments().get(0).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode422(response);
        checkError(response, errorType, "[\"имеет неправильный формат\"]");
    }

    @CaseId(2091)
    @Story("Редактирование контактных данных в заказе")
    @Test(description = "Запрос с вводом пустых данных",
            groups = "api-instamart-regress",
            dataProvider = "orderWithEmptyData",
            dataProviderClass = ApiV3DataProvider.class)
    public void editOrderWithEmptyCredentials(String email, String phone, String errorType) {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .email(email)
                        .phone(phone)
                        .build())
                .shipmentNumbers(Collections.singletonList(order.getShipments().get(0).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode422(response);
        checkError(response, errorType, "[\"не может быть пустым\"]");
    }

    @CaseId(2244)
    @Story("Редактирование адреса в заказе")
    @Test(description = "Запрос на обновление адреса доставки",
            groups = "api-instamart-regress")
    public void editAddressOrder() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .addressAttributes(CheckoutV3Request.AddressAttributes.builder()
                                .id(SpreeAddressesDao.INSTANCE.getAddressByUserPhone(user.getPhone()).getId())
                                .floor("1")
                                .apartment("32")
                                .deliveryToDoor(false)
                                .entrance("3")
                                .comments("Позвоните " + RandomUtils.nextInt(1, 100))
                                .instructions("Не звоните" + RandomUtils.nextInt(1, 100))
                                .build())
                        .build())
                .shipmentNumbers(Collections.singletonList(order.getShipments().get(0).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderV3Response.class);
        CheckoutOrderV3 orderFromResponse = response.as(OrderV3Response.class).getOrder();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(orderFromResponse.getAddress().getEntrance(), orderRequest.getOrder().getAddressAttributes().getEntrance(), softAssert);
        compareTwoObjects(orderFromResponse.getAddress().getFloor(), orderRequest.getOrder().getAddressAttributes().getFloor(), softAssert);
        compareTwoObjects(orderFromResponse.getAddress().getApartment(), orderRequest.getOrder().getAddressAttributes().getApartment(), softAssert);
        compareTwoObjects(orderFromResponse.getAddress().getComments(), orderRequest.getOrder().getAddressAttributes().getComments(), softAssert);
        compareTwoObjects(orderFromResponse.getAddress().getDeliveryToDoor(), orderRequest.getOrder().getAddressAttributes().getDeliveryToDoor(), softAssert);
        softAssert.assertAll();
    }
}
