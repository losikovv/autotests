package ru.instamart.test.api.on_demand.orderservice;

import enums.Enums;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.apache.commons.lang3.EnumUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.jdbc.dao.orders_service.OrdersDao;
import ru.instamart.jdbc.dao.orders_service.PlaceSettingsDao;
import ru.instamart.jdbc.entity.order_service.OrdersEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.StartPointsTenants;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.util.ThreadUtil;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static ru.instamart.kafka.enums.StatusOrder.AUTOMATIC_ROUTING;

@Epic("On Demand")
@Feature("DISPATCH")
public class OrderTimeToThrowTest extends RestBase {
    private OrderV2 order;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        UserData user = UserManager.getShp6Shopper1();
        shopperApp.authorisation(user);
        //Удаляем все смены
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
        //
        shiftsApi.startOfShift(StartPointsTenants.METRO_9);
    }

    @CaseId(50)
    @Test(groups = {"dispatch-orderservice-smoke"},
            description = "On-demand заказ не хранится в буфере до момента time_to_throw")
    public void createOrder() {
        order = apiV2.orderOnDemand(
                SessionFactory.getSession(SessionType.API_V2).getUserData(),
                EnvironmentProperties.DEFAULT_SID
        );
        ThreadUtil.simplyAwait(10);
        System.out.println("order uuid : " + order.getUuid());
        final var orderEntity = OrdersDao.INSTANCE.findByOrderUuid(order.getUuid());
        Allure.step("Проверка orderEntity", () -> {
            assertNotNull(orderEntity, "orderEntity пришел null");
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderEntity.getPlaceUuid(), "599ba7b7-0d2f-4e54-8b8e-ca5ed7c6ff8a", "placeUUID не совпадает");
            softAssert.assertEquals(orderEntity.getWeight(), order.getTotalWeight(), "weight не совпадает");
            softAssert.assertEquals(orderEntity.getType(), "ON_DEMAND", "type не совпадает");
            softAssert.assertTrue(EnumUtils.isValidEnum(Enums.ShipmentStatus.class, orderEntity.getOrderStatus()), "order_status не совпадает");
            softAssert.assertEquals(orderEntity.getItemCount(), Integer.valueOf(1), "Item count не совпадает");
            softAssert.assertNotNull(orderEntity.getCreatedAt(), "create_at is NULL");
            softAssert.assertEquals(orderEntity.getOrderNumber(), order.getShipments().get(0).getNumber(), "order number не совпадает");
            softAssert.assertAll();
        });
    }


    @CaseId(51)
    @Test(enabled = false,
            groups = {"dispatch-orderservice-smoke"},
            description = "Проверка расчёта time_to_throw")
    //в этот топик уже не пишут и кей не актуален
    public void createOnDemandOrder() {
        order = apiV2.order(
                SessionFactory.getSession(SessionType.API_V2).getUserData(),
                EnvironmentProperties.DEFAULT_SID
        );
        ThreadUtil.simplyAwait(10);

        OrdersEntity orderEntity = OrdersDao.INSTANCE.findByOrderUuid(order.getUuid());
        final var eventOrderEnrichments = kafka.waitDataInKafkaTopicConsumeOrderEnrichment(orderEntity.getShipmentUuid(), AUTOMATIC_ROUTING);

        final var scheduleType = PlaceSettingsDao.INSTANCE.getScheduleType("599ba7b7-0d2f-4e54-8b8e-ca5ed7c6ff8a");
        assertEquals(scheduleType.getPeriodForTimeToThrowMin(), eventOrderEnrichments.get(0).getDeliveryPromiseUpperDttmEndsAt(), "Проверка времени time_to_throw");
    }
}
