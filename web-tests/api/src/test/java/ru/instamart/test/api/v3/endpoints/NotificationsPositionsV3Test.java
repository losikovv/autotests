package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.ApiV3DataProvider;
import ru.instamart.api.enums.v3.NotificationTypesV3;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v3.NotificationsV3Request;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Нотификации")
public class NotificationsPositionsV3Test extends RestBase {

    @Story("Позиции заказа")
    @CaseId(945)
    @Test(description = "Заказ собран без изменений (все типы упаковок)",
            groups = {"api-instamart-regress"},
            dataProvider = "ordersWithDifferentPricers",
            dataProviderClass = ApiV3DataProvider.class)
    public void noChanges(OrderV2 order) {
        Long productId = order.getShipments().get(0).getLineItems().get(0).getProduct().getId();
        Integer quantity = order.getShipments().get(0).getLineItems().get(0).getQuantity();

        Response responseInWork = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypesV3.IN_WORK.getValue(),
                String.valueOf(productId),
                quantity,
                quantity);
        checkStatusCode200(responseInWork);
        Response responseReady = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypesV3.READY_FOR_DELIVERY.getValue(),
                String.valueOf(productId),
                quantity,
                quantity);
        checkStatusCode200(responseReady);

        OrderV2 readyOrder = apiV2.getOrder(order.getNumber());

        Assert.assertEquals(order.getShipments().get(0).getItemCount(), readyOrder.getShipments().get(0).getItemCount(),
                "Количество товаров отличается после сборки");
    }
}
