package ru.instamart.test.api.on_demand.orderservice;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import order.OrderChanged;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v3.IntegrationTypeV3;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.helper.K8sHelper;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.jdbc.dao.orders_service.OrdersDao;
import ru.instamart.jdbc.dao.orders_service.PlacesDao;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.util.ThreadUtil;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.OrderServiceHelper.POST;
import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_ON_DEMAND_VIDAR_SID;
import static ru.instamart.kraken.util.TimeUtil.getTimestamp;

@Epic("On Demand")
@Feature("DISPATCH")
public class ExternalAssemblyTest extends RestBase {
    private final String placeUUID = "684609ad-6360-4bae-9556-03918c1e41c1";
    private final String externalAssembly = "EXTERNAL";
    private final String ordinaryAssembly = "SM";
    private OrderV2 order;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        K8sHelper.updateApiIntegrationType(IntegrationTypeV3.DELIVERY_BY_SBERMARKET.getValue(), DEFAULT_ON_DEMAND_VIDAR_SID);
        PlacesDao.INSTANCE.updatePlacesAssemblyTaskType(externalAssembly, placeUUID);
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditionsBeforeMethod() {
        Allure.step("Создание заказа на витрине", () -> {
            SessionFactory.makeSession(SessionType.API_V2);
            UserData userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
            order = apiV2.order(userData, DEFAULT_ON_DEMAND_VIDAR_SID);
        });
    }

    @CaseId(147)
    @Test(groups = {"dispatch-orderservice-smoke"},
            description = "Получение данных заказа с внешней сборкой")
    public void getOrderWithExternalAssembly() {
        ThreadUtil.simplyAwait(10);
        final var orderEntity = OrdersDao.INSTANCE.findByOrderNumber(order.getShipments().get(0).getNumber());
        final var orderChangedMessage = kafka.waitDataInKafkaTopicOrderStatusChangedShipmentUuid(orderEntity.getShipmentUuid());

        Allure.step("Проверка полученного сообщения о заказе с внешней сборкой в кафке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderChangedMessage.get(0).getOrderStatus(), OrderChanged.EventOrderChanged.OrderStatus.READY_TO_COLLECT, "Вернулся не статус READY_TO_COLLECT для заказа с внешней сборкой");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(0).getJobStatus(), OrderChanged.EventOrderChanged.Job.JobStatus.NEW, "У нового заказа статус джобы доставки не NEW");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(1).getJobStatus(), OrderChanged.EventOrderChanged.Job.JobStatus.QUEUED, "У нового заказа с внешней сборкой статус джобы сборки не QUEUED");
            softAssert.assertAll();
        });
    }

    @CaseId(148)
    @Test(groups = {"dispatch-orderservice-smoke"},
            description = "Смена статуса джобов для заказа с внешней сборкой после получения ивента о начале сборки заказа")
    public void checkJobsAfterStartedExternalAssembly() {
        var bodyInWork = apiV3Notifications.bodyInWork(order.getShipments().get(0).getNumber());
        var responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        long startedAssemblyTime = getTimestamp().getSeconds();
        ThreadUtil.simplyAwait(10);
        final var orderEntity = OrdersDao.INSTANCE.findByOrderNumber(order.getShipments().get(0).getNumber());
        final var orderChangedMessage = kafka.waitDataInKafkaTopicOrderStatusChangedByStatusShipment(orderEntity.getShipmentUuid(), OrderChanged.EventOrderChanged.OrderStatus.COLLECTING);
        long timeDifference = orderChangedMessage.get(0).getJobs(1).getFactStartedAt().getSeconds() - startedAssemblyTime;

        Allure.step("Проверка полученного сообщения о заказе с внешней сборкой в кафке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderChangedMessage.get(0).getOrderStatus(), OrderChanged.EventOrderChanged.OrderStatus.COLLECTING, "Вернулся не статус COLLECTING для заказа, который начали собирать");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(0).getJobStatus(), OrderChanged.EventOrderChanged.Job.JobStatus.NEW, "У нового заказа статус джобы доставки не NEW");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(1).getJobStatus(), OrderChanged.EventOrderChanged.Job.JobStatus.IN_PROGRESS, "У заказа с внешней сборкой, который начали собирать статус джобы сборки не IN_PROGRESS");
            if (timeDifference < 5) {
                softAssert.assertEquals(orderChangedMessage.get(0).getJobs(1).getFactStartedAt().getSeconds(), startedAssemblyTime + timeDifference, "Фактическое время начала джобы не совпадает с временем получения сообщения");
            } else {
                softAssert.assertEquals(orderChangedMessage.get(0).getJobs(1).getFactStartedAt().getSeconds(), startedAssemblyTime, "Фактическое время начала джобы не совпадает с временем получения сообщения");
            }
            softAssert.assertAll();
        });
    }

    @CaseId(149)
    @Test(groups = {"dispatch-orderservice-smoke"},
            description = "Смена статуса джобов для заказа с внешней сборкой после получения ивента о конце сборки заказа")
    public void checkJobsAfterFinishedExternalAssembly() {
        var bodyInWork = apiV3Notifications.bodyInWork(order.getShipments().get(0).getNumber());
        var bodyReadyForDelivery = apiV3Notifications.bodyReadyForDelivery(order.getShipments().get(0).getNumber());
        var responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        var responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        long finishedAssemblyTime = getTimestamp().getSeconds();
        ThreadUtil.simplyAwait(10);
        final var orderEntity = OrdersDao.INSTANCE.findByOrderNumber(order.getShipments().get(0).getNumber());
        final var orderChangedMessage = kafka.waitDataInKafkaTopicOrderStatusChangedByStatusShipment(orderEntity.getShipmentUuid(), OrderChanged.EventOrderChanged.OrderStatus.READY_TO_SHIP);
        long timeDifference = orderChangedMessage.get(0).getJobs(1).getFactEndedAt().getSeconds() - finishedAssemblyTime;

        Allure.step("Проверка полученного сообщения о завершённой сборке заказа с внешней сборкой в кафке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderChangedMessage.get(0).getOrderStatus(), OrderChanged.EventOrderChanged.OrderStatus.READY_TO_SHIP, "Вернулся не статус READY_TO_SHIP для заказа, который закончили собирать");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(0).getJobStatus(), OrderChanged.EventOrderChanged.Job.JobStatus.NEW, "У нового заказа статус джобы доставки не NEW");
            softAssert.assertEquals(orderChangedMessage.get(0).getJobs(1).getJobStatus(), OrderChanged.EventOrderChanged.Job.JobStatus.COMPLETED, "У нового заказа с внешней сборкой статус джобы сборки не QUEUED");
            if (timeDifference < 5) {
                softAssert.assertEquals(orderChangedMessage.get(0).getJobs(1).getFactEndedAt().getSeconds(), finishedAssemblyTime + timeDifference, "Фактическое время завершения джобы не совпадает с временем получения сообщения");
            } else {
                softAssert.assertEquals(orderChangedMessage.get(0).getJobs(1).getFactEndedAt().getSeconds(), finishedAssemblyTime, "Фактическое время завершения джобы не совпадает с временем получения сообщения");
            }
            softAssert.assertAll();
        });
    }

    @AfterMethod(alwaysRun = true)
    public void postConditionsAfterMethod() {
        apiV2.cancelShipment(order.getShipments().get(0).getNumber());
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        K8sHelper.updateApiIntegrationType(IntegrationTypeV3.SHOPPER.getValue(), DEFAULT_ON_DEMAND_VIDAR_SID);
        PlacesDao.INSTANCE.updatePlacesAssemblyTaskType(ordinaryAssembly, placeUUID);
    }
}
