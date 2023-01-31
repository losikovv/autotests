package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.enums.v2.StateV2;
import ru.instamart.api.helper.K8sHelper;
import ru.instamart.api.model.v1.ActiveShipmentV1;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v3.PaymentToolV3;
import ru.instamart.api.request.v1.ShipmentsV1Request;
import ru.instamart.api.request.v3.CheckoutV3Request;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.jdbc.dao.stf.*;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.TimeUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID;
import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_SID;

@Epic("ApiV1")
@Feature("Заказы")
public class UsersShipmentsV1ActiveShipmentTests extends RestBase {
    private final int testSid = DEFAULT_SID;

    @TmsLink("2586")
    @Story("Дозаказ")
    @Test(description = "Получение активного заказа пользователя", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getActivePositive() {
        UserData user = UserManager.getQaUser();
        apiV2.authByQA(user);
        apiV2.dropAndFillCart(user, testSid);
        apiV2.createOrder();
        apiV2.setDefaultAttributesAndCompleteOrder();
        apiV2.dropAndFillCart(user, testSid);
        OrderV2 secondOrderV2 = apiV2.createOrder();
        apiV2.setDefaultAttributesAndCompleteOrder();

        apiV1.authByPhone(user);

        final Response response = ShipmentsV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ActiveShipmentV1[].class);

        List<ActiveShipmentV1> activeShipments = Arrays.asList(response.as(ActiveShipmentV1[].class));
        compareTwoObjects(activeShipments.size(), 1);
        compareTwoObjects(activeShipments.get(0).getNumber(), secondOrderV2.getShipments().get(0).getNumber());
    }

    @TmsLink("2588")
    @Story("Дозаказ")
    @Test(description = "Получение активного заказа | у пользователя нет активных заказов", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getActiveWithoutActiveShipments() {
        UserData user = UserManager.getQaUser();
        apiV2.authByQA(user);
        apiV2.dropAndFillCart(user, testSid);
        OrderV2 orderV2 = apiV2.createOrder();
        apiV2.setDefaultAttributesAndCompleteOrder();
        SpreeOrdersDao.INSTANCE.updateShipmentState(orderV2.getNumber(), StateV2.CANCELED.getValue());

        apiV1.authByPhone(user);
        final Response response = ShipmentsV1Request.GET();
        checkStatusCode200(response);
        List<ActiveShipmentV1> activeShipments = Arrays.asList(response.as(ActiveShipmentV1[].class));
        compareTwoObjects(activeShipments.size(), 0);
    }

    @TmsLink("2598")
    @Story("Дозаказ")
    @Test(enabled = false, description = "Получение активного заказа пользователя | информация об опоздании", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getActiveWithDelay() {
        //TODO Разобраться как сделать заказ с ОПОЗДАНИЕМ. Сейчас идёт АБ-тест, возможно, влияет он. delivery_forecast проставляется
        // https://wiki.sbmt.io/pages/viewpage.action?pageId=3056316123 , но has_delay - нет
        UserData user = UserManager.getQaUser();
        apiV2.authByQA(user);
        apiV2.dropAndFillCart(user, DEFAULT_METRO_MOSCOW_SID);
        apiV2.createOrder();
        OrderV2 orderV2 = apiV2.setDefaultAttributesAndCompleteOrder();

        SpreeShipmentsDao.INSTANCE.updateShipmentState(orderV2.getShipments().get(0).getNumber(), StateV2.COLLECTING.getValue());
        var forecast = 24;
        K8sHelper.createDeliveryForecast(orderV2.getShipments().get(0).getNumber(), String.valueOf(forecast));
        apiV1.authByPhone(user);
        final Response response = ShipmentsV1Request.GET();
        checkStatusCode200(response);

        List<ActiveShipmentV1> activeShipments = Arrays.asList(response.as(ActiveShipmentV1[].class));
        compareTwoObjects(activeShipments.get(0).getDeliveryForecast().getWillDeliverAt().substring(0, 16), TimeUtil.getDateTimePlusHours(forecast).substring(0, 16)); //Сравниваем до секунд
        Assert.assertTrue(activeShipments.get(0).getDeliveryForecast().isHasDelay(), "В блоке 'delivery_forecast' передано '\"has_delay\" : false'");
    }

    @TmsLink("2589")
    @Story("Дозаказ")
    @Test(description = "Проверка возможности редактирования заказа | дозаказ возможен", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getActiveMergeAvailable() {
        UserData user = UserManager.getQaUser();
        apiV2.authByQA(user);
        apiV2.dropAndFillCart(user, testSid);
        apiV2.createOrder();
        apiV2.setDefaultAttributesAndCompleteOrder();

        apiV1.authByPhone(user);

        final Response response = ShipmentsV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ActiveShipmentV1[].class);

        List<ActiveShipmentV1> activeShipments = Arrays.asList(response.as(ActiveShipmentV1[].class));
        compareTwoObjects(activeShipments.size(), 1);
        Assert.assertTrue(activeShipments.get(0).getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : false'");
        Assert.assertNull(activeShipments.get(0).getMerge().getImpossibilityReason(), "В ответе не пустой блок 'impossibility_reason'");
    }

    @TmsLink("2590")
    @Story("Дозаказ")
    @Test(description = "Проверка возможности редактирования заказа | заказ с перевесом", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getActiveMergeUnavailableOverWeight() {
        UserData user = UserManager.getQaUser();
        apiV2.authByQA(user);
        apiV2.dropAndFillCart(user, testSid);
        apiV2.createOrder();
        OrderV2 orderV2 = apiV2.setDefaultAttributesAndCompleteOrder();
        var deliveryWindowId = orderV2.getShipments().get(0).getDeliveryWindow().getId();

        apiV1.authByPhone(user);
        DeliveryWindowsDao.INSTANCE.updateWeightLimits(deliveryWindowId, 0, 0, 0);
        final Response response = ShipmentsV1Request.GET();

        DeliveryWindowsDao.INSTANCE.updateWeightLimits(deliveryWindowId, 3000, 20000, 9999000);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, ActiveShipmentV1[].class);

        List<ActiveShipmentV1> activeShipments = Arrays.asList(response.as(ActiveShipmentV1[].class));
        compareTwoObjects(activeShipments.size(), 1);
        Assert.assertFalse(activeShipments.get(0).getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(activeShipments.get(0).getMerge().getImpossibilityReason().getCode(), "too_heavy");
    }

    @TmsLink("2591")
    @Story("Дозаказ")
    @Test(description = "Проверка возможности редактирования заказа | максимальное кол-во товаров в заказе", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getActiveMergeUnavailableMaxCount() {
        UserData user = UserManager.getQaUser();
        apiV2.authByQA(user);
        apiV2.dropAndFillCart(user, testSid);
        apiV2.createOrder();
        OrderV2 orderV2 = apiV2.setDefaultAttributesAndCompleteOrder();
        var deliveryWindowId = orderV2.getShipments().get(0).getDeliveryWindow().getId();

        apiV1.authByPhone(user);
        DeliveryWindowsDao.INSTANCE.updateItemsCountLimits(deliveryWindowId, 999, 0, 0);

        final Response response = ShipmentsV1Request.GET();
        DeliveryWindowsDao.INSTANCE.updateItemsCountLimits(deliveryWindowId, 999, 20, 9999);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, ActiveShipmentV1[].class);

        List<ActiveShipmentV1> activeShipments = Arrays.asList(response.as(ActiveShipmentV1[].class));
        compareTwoObjects(activeShipments.size(), 1);
        Assert.assertFalse(activeShipments.get(0).getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(activeShipments.get(0).getMerge().getImpossibilityReason().getCode(), "items_count_balance_reached");
    }

    @TmsLink("2592")
    @Story("Дозаказ")
    @Test(description = "Проверка возможности редактирования заказа | заказ в статусе Собирается", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getActiveMergeUnavailableIncorrectStateCollecting() {
        UserData user = UserManager.getQaUser();
        apiV2.authByQA(user);
        apiV2.dropAndFillCart(user, testSid);
        apiV2.createOrder();
        OrderV2 orderV2 = apiV2.setDefaultAttributesAndCompleteOrder();
        SpreeShipmentsDao.INSTANCE.updateShipmentState(orderV2.getShipments().get(0).getNumber(), StateV2.COLLECTING.getValue());

        apiV1.authByPhone(user);
        final Response response = ShipmentsV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ActiveShipmentV1[].class);

        List<ActiveShipmentV1> activeShipments = Arrays.asList(response.as(ActiveShipmentV1[].class));
        compareTwoObjects(activeShipments.size(), 1);
        Assert.assertFalse(activeShipments.get(0).getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(activeShipments.get(0).getMerge().getImpossibilityReason().getCode(), "invalid_shipment_state_collecting");
    }

    @TmsLink("2600")
    @Story("Дозаказ")
    @Test(description = "Проверка возможности редактирования заказа | заказ в статусе Собран", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getActiveMergeUnavailableIncorrectStateReadyToShip() {
        UserData user = UserManager.getQaUser();
        apiV2.authByQA(user);
        apiV2.dropAndFillCart(user, testSid);
        apiV2.createOrder();
        OrderV2 orderV2 = apiV2.setDefaultAttributesAndCompleteOrder();
        SpreeShipmentsDao.INSTANCE.updateShipmentState(orderV2.getShipments().get(0).getNumber(), StateV2.READY_TO_SHIP.getValue());

        apiV1.authByPhone(user);
        final Response response = ShipmentsV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ActiveShipmentV1[].class);

        List<ActiveShipmentV1> activeShipments = Arrays.asList(response.as(ActiveShipmentV1[].class));
        compareTwoObjects(activeShipments.size(), 1);
        Assert.assertFalse(activeShipments.get(0).getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(activeShipments.get(0).getMerge().getImpossibilityReason().getCode(), "invalid_shipment_state_ready_to_ship");
    }

    @TmsLink("2593")
    @Story("Дозаказ")
    @Test(description = "Проверка возможности редактирования заказа | заказ в статусе В пути", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getActiveMergeUnavailableIncorrectStateShipping() {
        UserData user = UserManager.getQaUser();
        apiV2.authByQA(user);
        apiV2.dropAndFillCart(user, testSid);
        apiV2.createOrder();
        OrderV2 orderV2 = apiV2.setDefaultAttributesAndCompleteOrder();
        SpreeShipmentsDao.INSTANCE.updateShipmentState(orderV2.getShipments().get(0).getNumber(), StateV2.SHIPPING.getValue());

        apiV1.authByPhone(user);
        final Response response = ShipmentsV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ActiveShipmentV1[].class);

        List<ActiveShipmentV1> activeShipments = Arrays.asList(response.as(ActiveShipmentV1[].class));
        compareTwoObjects(activeShipments.size(), 1);
        Assert.assertFalse(activeShipments.get(0).getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(activeShipments.get(0).getMerge().getImpossibilityReason().getCode(), "invalid_shipment_state_shipping");
    }

    @TmsLink("2594")
    @Story("Дозаказ")
    @Test(description = "Проверка возможности редактирования | заказ из Аптеки", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getActiveMergeUnavailablePharmacy() {
        UserData user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        apiV1.changeAddress(apiV2.getAddressBySidMy(DEFAULT_METRO_MOSCOW_SID), ShippingMethodV2.BY_COURIER.getMethod());
        long offerId = SpreeProductsDao.INSTANCE.getOfferIdForPharma(DEFAULT_METRO_MOSCOW_SID);
        apiV1.fillCart(apiV2.getAddressBySidMy(DEFAULT_METRO_MOSCOW_SID), ShippingMethodV2.PICKUP.getMethod(), offerId);
        apiV1.addReplacementPolicy();
        apiV1.addDeliveryWindow();
        MultiretailerOrderV1Response multiRetailerOrder = apiV1.getMultiRetailerOrder();

        final Response initializeResponse = CheckoutV3Request.Initialization.POST(
                multiRetailerOrder.getNumber(),
                Collections.singletonList(multiRetailerOrder.getShipments().get(0).getNumber()));
        checkStatusCode(initializeResponse, 204);

        PaymentToolV3 paymentTool = apiV3.getPaymentTools(multiRetailerOrder.getNumber()).get(0);
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .paymentAttributes(CheckoutV3Request.PaymentAttributes.builder()
                                .paymentToolId(paymentTool.getId())
                                .build())
                        .build())
                .shipmentNumber(multiRetailerOrder.getShipments().get(0).getNumber())
                .build();
        final Response paymentToolResponse = CheckoutV3Request.PUT(orderRequest, multiRetailerOrder.getNumber());
        checkStatusCode200(paymentToolResponse);

        final Response completionResponse = CheckoutV3Request.Completion.POST(multiRetailerOrder.getNumber(), Collections.singletonList(multiRetailerOrder.getShipments().get(0).getNumber()));
        checkStatusCode200(completionResponse);
        final Response response = ShipmentsV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ActiveShipmentV1[].class);

        List<ActiveShipmentV1> activeShipments = Arrays.asList(response.as(ActiveShipmentV1[].class));
        compareTwoObjects(activeShipments.size(), 1);
        Assert.assertFalse(activeShipments.get(0).getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(activeShipments.get(0).getMerge().getImpossibilityReason().getCode(), "not_available_for_prescription_drugs");
    }

    @TmsLink("2599")
    @Story("Дозаказ")
    @Test(description = "Проверка возможности редактирования | заказ с алкоголем", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getActiveMergeUnavailableAlcohol() {
        UserData user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        apiV1.changeAddress(apiV2.getAddressBySidMy(DEFAULT_METRO_MOSCOW_SID), ShippingMethodV2.BY_COURIER.getMethod());
        long offerId = SpreeProductsDao.INSTANCE.getOfferIdForAlcohol(DEFAULT_METRO_MOSCOW_SID);
        apiV1.fillCart(apiV2.getAddressBySidMy(DEFAULT_METRO_MOSCOW_SID), ShippingMethodV2.PICKUP.getMethod(), offerId);
        apiV1.addReplacementPolicy();
        apiV1.addDeliveryWindow();
        MultiretailerOrderV1Response multiRetailerOrder = apiV1.getMultiRetailerOrder();

        final Response initializeResponse = CheckoutV3Request.Initialization.POST(
                multiRetailerOrder.getNumber(),
                Collections.singletonList(multiRetailerOrder.getShipments().get(0).getNumber()));
        checkStatusCode(initializeResponse, 204);

        PaymentToolV3 paymentTool = apiV3.getPaymentTools(multiRetailerOrder.getNumber()).get(0);
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .paymentAttributes(CheckoutV3Request.PaymentAttributes.builder()
                                .paymentToolId(paymentTool.getId())
                                .build())
                        .build())
                .shipmentNumber(multiRetailerOrder.getShipments().get(0).getNumber())
                .build();
        final Response paymentToolResponse = CheckoutV3Request.PUT(orderRequest, multiRetailerOrder.getNumber());
        checkStatusCode200(paymentToolResponse);

        final Response completionResponse = CheckoutV3Request.Completion.POST(multiRetailerOrder.getNumber(), Collections.singletonList(multiRetailerOrder.getShipments().get(0).getNumber()));
        checkStatusCode200(completionResponse);
        final Response response = ShipmentsV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ActiveShipmentV1[].class);

        List<ActiveShipmentV1> activeShipments = Arrays.asList(response.as(ActiveShipmentV1[].class));
        compareTwoObjects(activeShipments.size(), 1);
        Assert.assertFalse(activeShipments.get(0).getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(activeShipments.get(0).getMerge().getImpossibilityReason().getCode(), "not_available_for_alcohol");
    }

    @TmsLink("2595")
    @Story("Дозаказ")
    @Test(description = "Проверка возможности редактирования заказа | время редактирования закончилось", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getActiveMergeUnavailableOrderNotEditable() {
        UserData user = UserManager.getQaUser();
        apiV2.authByQA(user);
        apiV2.dropAndFillCart(user, testSid);
        apiV2.createOrder();
        apiV2.setDefaultAttributesAndCompleteOrder();

        apiV1.authByPhone(user);
        StoreConfigsDao.INSTANCE.updateEditingSettings(testSid, 48, 0);
        final Response response = ShipmentsV1Request.GET();
        StoreConfigsDao.INSTANCE.updateEditingSettings(testSid, 1, 0);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, ActiveShipmentV1[].class);

        List<ActiveShipmentV1> activeShipments = Arrays.asList(response.as(ActiveShipmentV1[].class));
        compareTwoObjects(activeShipments.size(), 1);
        Assert.assertFalse(activeShipments.get(0).getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(activeShipments.get(0).getMerge().getImpossibilityReason().getCode(), "order_not_editable");
    }

    @TmsLink("2596")
    @Story("Дозаказ")
    @Test(description = "Проверка возможности редактирования заказа | редактирование в магазине запрещено", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getActiveMergeNotAvailableInStore() {
        UserData user = UserManager.getQaUser();
        apiV2.authByQA(user);
        apiV2.dropAndFillCart(user, testSid);
        apiV2.createOrder();
        apiV2.setDefaultAttributesAndCompleteOrder();

        apiV1.authByPhone(user);
        StoreConfigsDao.INSTANCE.updateEditingSettings(testSid, 0, 1);
        final Response response = ShipmentsV1Request.GET();
        StoreConfigsDao.INSTANCE.updateEditingSettings(testSid, 1, 0);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, ActiveShipmentV1[].class);

        List<ActiveShipmentV1> activeShipments = Arrays.asList(response.as(ActiveShipmentV1[].class));
        compareTwoObjects(activeShipments.size(), 1);
        Assert.assertFalse(activeShipments.get(0).getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(activeShipments.get(0).getMerge().getImpossibilityReason().getCode(), "not_available_in_the_store");
    }

    @TmsLink("2597")
    @Story("Дозаказ")
    @Test(description = "Проверка возможности редактирования заказа | on_demand заказ", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getActiveMergeNotAvailableOnDemand() {
        UserData user = UserManager.getQaUser();
        apiV2.authByQA(user);
        apiV2.dropAndFillCart(user, DEFAULT_METRO_MOSCOW_SID);
        apiV2.createOrder();
        apiV2.setDefaultAttributesAndCompleteOrder();

        apiV1.authByPhone(user);
        final Response response = ShipmentsV1Request.GET();

        checkStatusCode200(response);
        checkResponseJsonSchema(response, ActiveShipmentV1[].class);

        List<ActiveShipmentV1> activeShipments = Arrays.asList(response.as(ActiveShipmentV1[].class));
        compareTwoObjects(activeShipments.size(), 1);
        Assert.assertFalse(activeShipments.get(0).getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(activeShipments.get(0).getMerge().getImpossibilityReason().getCode(), "not_acceptable_for_on_demand");
    }

    @TmsLink("2587")
    @Story("Дозаказ")
    @Test(description = "Получение активных заказов без авторизации", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getActiveUnauthorized() {
        final Response response = ShipmentsV1Request.GET();
        checkStatusCode401(response);
        checkErrorText(response, "Должен быть указан ключ API");
    }
}
