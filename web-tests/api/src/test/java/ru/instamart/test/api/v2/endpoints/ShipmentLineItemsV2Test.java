package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.StateV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.LineItemV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v2.ProductV2;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.api.response.v2.MergeLineItemsV2Response;
import ru.instamart.api.response.v2.OrderV2Response;
import ru.instamart.jdbc.dao.stf.DeliveryWindowsDao;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.jdbc.dao.stf.StoreConfigsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;

@Epic("ApiV2")
@Feature("Заказы (shipments)")
public class ShipmentLineItemsV2Test extends RestBase {

    private List<ProductV2> products;
    private OrderV2 order;
    private Double newPrice;
    private Integer deliveryWindowId;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        products = apiV2.getProductsFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        deliveryWindowId = order.getShipments().get(0).getDeliveryWindow().getId();
        DeliveryWindowsDao.INSTANCE.updateDeliveryWindowSettings(deliveryWindowId, 999, 1, 999, 1);
    }

    @CaseIDs(value = {@CaseId(1003), @CaseId(1004)})
    @Story("Добавление товара в заказ")
    @Test(groups = {"api-instamart-regress"},
            description = "Добавление другого товара из этого же магазина")
    public void mergeLineItem() {
        final Response response = ShipmentsV2Request.LineItems.POST(order.getShipments().get(0).getNumber(), products.get(1).getId(), 1);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MergeLineItemsV2Response.class);
        newPrice = order.getTotal() + products.get(1).getPrice();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(newPrice, response.as(MergeLineItemsV2Response.class).getOrder().getTotal(), softAssert);

        final Response responseWithUpdatedOrder = OrdersV2Request.GET(order.getNumber());
        checkStatusCode200(responseWithUpdatedOrder);
        OrderV2 updatedOrder = responseWithUpdatedOrder.as(OrderV2Response.class).getOrder();
        compareTwoObjects(newPrice, updatedOrder.getTotal(), softAssert);
        compareTwoObjects(order.getItemCount() + 1, updatedOrder.getItemCount(), softAssert);
        softAssert.assertAll();
    }

    @CaseId(1006)
    @Story("Добавление товара в заказ")
    @Test(groups = {"api-instamart-regress"},
            description = "Добавление того же товара",
            priority = 1)
    public void mergeSameLineItem() {
        final Response response = ShipmentsV2Request.LineItems.POST(order.getShipments().get(0).getNumber(), order.getShipments().get(0).getLineItems().get(0).getProduct().getId(), 1);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MergeLineItemsV2Response.class);
        Double updatedPrice = newPrice + order.getShipments().get(0).getLineItems().get(0).getProduct().getPrice();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(updatedPrice, response.as(MergeLineItemsV2Response.class).getOrder().getTotal(), softAssert);

        final Response responseWithUpdatedOrder = OrdersV2Request.GET(order.getNumber());
        checkStatusCode200(responseWithUpdatedOrder);
        OrderV2 updatedOrder = responseWithUpdatedOrder.as(OrderV2Response.class).getOrder();
        compareTwoObjects(updatedPrice, updatedOrder.getTotal(), softAssert);
        compareTwoObjects(order.getItemCount() + 2, updatedOrder.getItemCount(), softAssert);
        Integer lineItemId = order.getShipments().get(0).getLineItems().get(0).getId();
        List<LineItemV2> lineItems = updatedOrder.getShipments().get(0).getLineItems().
                stream().filter(l -> l.getId().equals(lineItemId)).collect(Collectors.toList());
        compareTwoObjects(order.getShipments().get(0).getLineItems().get(0).getPacks() + 1, lineItems.get(0).getPacks(), softAssert);
        softAssert.assertAll();
    }

    @CaseId(1011)
    @Story("Добавление товара в заказ")
    @Test(groups = {"api-instamart-regress"},
            description = "Добавление другого товара с превышением веса",
            priority = 2)
    public void mergeLineItemWithExtraWeight() {
        final Response response = ShipmentsV2Request.LineItems.POST(order.getShipments().get(0).getNumber(), products.get(3).getId(), 1000000000);
        checkStatusCode422(response);
        checkErrorField(response, "too_heavy", "Заказ тяжелый. Можно оставить товар в корзине — если захотите добавить к следующему заказу");
    }

    @CaseIDs(value = {@CaseId(1008), @CaseId(1012), @CaseId(2045)})
    @Story("Добавление товара в заказ")
    @Test(groups = {"api-instamart-regress"},
            description = "Добавление товара c невалидным productId",
            dataProvider = "invalidProductsId",
            dataProviderClass = RestDataProvider.class,
            priority = 2)
    public void mergeLineItemWithInvalidItem(Long productId, String field, String errorMessage) {
        final Response response = ShipmentsV2Request.LineItems.POST(order.getShipments().get(0).getNumber(), productId, 1);
        checkStatusCode422(response);
        checkErrorField(response, field, errorMessage);
    }

    @CaseId(1009)
    @Story("Добавление товара в заказ")
    @Test(groups = {"api-instamart-regress"},
            description = "Добавление другого товара - время редактирования заказа истекло",
            priority = 3)
    public void mergeLineItemForExpiredEditingPeriod() {
        StoreConfigsDao.INSTANCE.updateEditingSettings(1, 48, 0);
        final Response response = ShipmentsV2Request.LineItems.POST(order.getShipments().get(0).getNumber(), products.get(4).getId(), 1);
        checkStatusCode422(response);
        checkErrorField(response, "order_not_editable", "Заказ скоро начнут собирать. Можно оставить товар в корзине — если захотите добавить к следующему заказу");
    }

    @CaseId(1152)
    @Story("Добавление товара в заказ")
    @Test(groups = {"api-instamart-regress"},
            description = "Добавление другого товара - редактирование запрещено",
            priority = 4)
    public void mergeLineItemEditingForbidden() {
        StoreConfigsDao.INSTANCE.updateEditingSettings(1, 1, 1);
        final Response response = ShipmentsV2Request.LineItems.POST(order.getShipments().get(0).getNumber(), products.get(4).getId(), 1);
        checkStatusCode422(response);
        checkErrorField(response, "not_available_in_the_store", "В магазине нет такой возможности. Можно оставить товар в корзине — если захотите добавить к следующему заказу");
        StoreConfigsDao.INSTANCE.updateEditingSettings(1, 1, 0);
    }

    @CaseId(1010)
    @Story("Добавление товара в заказ")
    @Test(groups = {"api-instamart-regress"},
            description = "Добавление другого товара с превышение количества товаров",
            priority = 5)
    public void mergeLineItemWithExtraItems() {
        deliveryWindowId = order.getShipments().get(0).getDeliveryWindow().getId();
        DeliveryWindowsDao.INSTANCE.updateDeliveryWindowSettings(deliveryWindowId, 1, 1, 1, 1);
        ShipmentsV2Request.LineItems.POST(order.getShipments().get(0).getNumber(), products.get(6).getId(), 1);
        final Response response = ShipmentsV2Request.LineItems.POST(order.getShipments().get(0).getNumber(), products.get(6).getId(), 1);
        checkStatusCode422(response);
        checkErrorField(response, "items_count_balance_reached", "В заказе много товаров. Можно оставить товар в корзине — если захотите добавить к следующему заказу");
    }

    @CaseId(1005)
    @Story("Добавление товара в заказ")
    @Test(groups = {"api-instamart-regress"},
            description = "Добавление другого товара - заказ собирается",
            priority = 6)
    public void mergeLineItemForCollectingOrder() {
        String shipmentNumber = order.getShipments().get(0).getNumber();
        SpreeOrdersDao.INSTANCE.updateShipmentState(order.getNumber(), StateV2.COLLECTING.getValue());
        final Response response = ShipmentsV2Request.LineItems.POST(shipmentNumber, products.get(4).getId(), 1);
        checkStatusCode422(response);
        checkErrorField(response, "invalid_shipment_state_collecting", "Заказ собирают. Можно оставить товар в корзине — если захотите добавить к следующему заказу");
    }

    @CaseId(1007)
    @Story("Добавление товара в заказ")
    @Test(groups = {"api-instamart-regress"},
            description = "Добавление другого товара - заказ отменен",
            priority = 7)
    public void mergeLineItemForCancelledOrder() {
        SpreeOrdersDao.INSTANCE.updateShipmentState(order.getNumber(), StateV2.CANCELED.getValue());
        final Response response = ShipmentsV2Request.LineItems.POST(order.getShipments().get(0).getNumber(), products.get(4).getId(), 1);
        checkStatusCode422(response);
        checkErrorField(response, "invalid_shipment_state_canceled", "Заказ отменён. Можно оставить товар в корзине — если захотите добавить к следующему заказу");
    }

    @AfterClass(alwaysRun = true)
    public void restoreData() {
        StoreConfigsDao.INSTANCE.updateEditingSettings(1, 1, 0);
        if(Objects.nonNull(deliveryWindowId)) DeliveryWindowsDao.INSTANCE.updateDeliveryWindowSettings(deliveryWindowId, 999, 0, 999, 0);
    }
}
