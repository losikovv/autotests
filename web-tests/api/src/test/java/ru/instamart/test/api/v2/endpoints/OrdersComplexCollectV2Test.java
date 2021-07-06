package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.api.response.v2.LineItemCancellationsV2Response;
import ru.instamart.api.response.v2.LineItemReplacementsV2Response;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Заказы")
public class OrdersComplexCollectV2Test extends RestBase {

    private String orderNumber, shipmentNumber;

    @BeforeClass(alwaysRun = true, description = "Авторизация, создание комплексного заказа")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2_FB);

        OrderV2 order = apiV2.order(
                SessionFactory.getSession(SessionType.API_V2_FB).getUserData(),
                EnvironmentData.INSTANCE.getDefaultSid(),
                4
        );
        orderNumber = order.getNumber();
        shipmentNumber = order.getShipments().get(0).getNumber();
        shopperApp.complexCollect(shipmentNumber);
    }

    @CaseId(637)
    @Story("Получение списка отмененных позиций по заказу")
    @Test(enabled = false,
            groups = {"api-instamart-regress"},
            description = "Получение списка отмененных позиций по заказу для существующего id")
    public void getLineItemCancellationsWithItem200() {
        response = OrdersV2Request.LineItemCancellations.GET(orderNumber);
        checkStatusCode200(response);
        response.prettyPeek();
        assertFalse(response.as(LineItemCancellationsV2Response.class).getLineItemCancellations().isEmpty(), "Нет отмененных позиций заказа");
    }

    @CaseId(325)
    @Story("Получение списка отмененных позиций по подзаказу")
    @Test(enabled = false,
            groups = {"api-instamart-regress"},
            description = "Получение списка отмененных позиций по подзаказу для существующего id")
    public void getShipmentLineItem200() {
        response = ShipmentsV2Request.LineItemCancellations.GET(orderNumber);
        checkStatusCode200(response);
        assertFalse(response.as(LineItemCancellationsV2Response.class).getLineItemCancellations().isEmpty(), "Нет отмененных позиций для достаки");
    }

    @CaseId(327)
    @Story("Получение списка замененных позиций по заказу")
    @Test(enabled = false,
            groups = {"api-instamart-regress"},
            description = "Получение списка замененных позиций по заказу по существующему id")
    public void getOrdersLineItemReplacements200() {
        response = OrdersV2Request.LineItemReplacements.GET(orderNumber);
        checkStatusCode200(response);
        assertFalse(response.as(LineItemReplacementsV2Response.class).getLineItemReplacements().isEmpty(), "Нет отмененных позиций для достаки");
    }


    @CaseId(329)
    @Story("Получение списка замененных позиций по подзаказуу")
    @Test(enabled = false,
            groups = {"api-instamart-regress"},
            description = "Получение списка замененных позиций по подзаказу по существующему id")
    public void getShipmentLineItemReplacements200() {
        response = ShipmentsV2Request.LineItemReplacements.GET(orderNumber);
        checkStatusCode200(response);
        assertFalse(response.as(LineItemReplacementsV2Response.class).getLineItemReplacements().isEmpty(), "Нет отмененных позиций для достаки");
    }
}
