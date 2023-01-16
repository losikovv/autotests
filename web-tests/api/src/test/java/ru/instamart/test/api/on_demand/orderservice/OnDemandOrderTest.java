package ru.instamart.test.api.on_demand.orderservice;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.jdbc.dao.orders_service.publicScheme.OrdersDao;
import ru.instamart.jdbc.entity.order_service.publicScheme.OrdersEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.util.ThreadUtil;
import io.qameta.allure.TmsLink;

@Epic("On Demand")
@Feature("DISPATCH")
public class OnDemandOrderTest extends RestBase {

    private OrderV2 order;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        order = apiV2.orderOnDemand(
                SessionFactory.getSession(SessionType.API_V2).getUserData(),
                EnvironmentProperties.DEFAULT_SID
        );
    }

    @TmsLink("47")
    @Test(groups = {"dispatch-orderservice-smoke"},
            description = "Получение данных on-demand заказа")
    public void getOnDemandOrder() {
        ThreadUtil.simplyAwait(10);
        OrdersEntity orderEntity = OrdersDao.INSTANCE.findByOrderUuid(order.getUuid());
        Allure.step("Проверка данных on-demand заказа", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderEntity.getPlaceUuid(), "599ba7b7-0d2f-4e54-8b8e-ca5ed7c6ff8a", "placeUUID не совпадает");
            softAssert.assertEquals(orderEntity.getWeight(), order.getTotalWeight(), "weight не совпадает");
            softAssert.assertEquals(orderEntity.getType(), "ON_DEMAND", "type не совпадает");
            softAssert.assertNotNull(orderEntity.getCreatedAt(), "create_at is NULL");
            softAssert.assertAll();
        });
    }
}
