package ru.instamart.test.api.on_demand.orderservice;

import io.qameta.allure.Allure;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.jdbc.dao.orders_service.OrdersDao;
import ru.instamart.jdbc.entity.order_service.OrdersEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.util.ThreadUtil;
import ru.sbermarket.qase.annotation.CaseId;

public class PlanningOrderTest extends RestBase {
    private OrderV2 order;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        order = apiV2.order(
                SessionFactory.getSession(SessionType.API_V2).getUserData(),
                EnvironmentProperties.DEFAULT_SID
        );
    }

    @CaseId(47)
    @Test(groups = {"dispatch-orderservice-smoke"},
            description = "Получение данных on-demand заказа")
    public void createOrder() {
        ThreadUtil.simplyAwait(10);
        OrdersEntity orderEntity = OrdersDao.INSTANCE.findByOrderUuid(order.getUuid());
        Allure.step("", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderEntity.getPlaceUuid(), "599ba7b7-0d2f-4e54-8b8e-ca5ed7c6ff8a", "placeUUID не совпадает");
            softAssert.assertEquals(orderEntity.getWeight(), order.getTotalWeight(), "weight не совпадает");
            softAssert.assertEquals(orderEntity.getType(), "ON_DEMAND", "type не совпадает");
            softAssert.assertEquals(orderEntity.getOrderStatus(), "ROUTING",  "order_status не совпадает");
            softAssert.assertEquals(orderEntity.getItemCount(), order.getItemCount(),  "order_status не совпадает");
            softAssert.assertNotNull(orderEntity.getCreatedAt(),  "create_at is NULL");
            softAssert.assertEquals(orderEntity.getOrderNumber(), order.getShipments().get(0).getNumber(),  "order number не совпадает");
        });
    }
}
