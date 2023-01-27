package ru.instamart.test.api.on_demand.orderservice;

import io.qameta.allure.*;
import io.restassured.response.Response;
import order.OrderChanged;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v1.admin.OrderCancellationsAdminV1Request;
import ru.instamart.jdbc.dao.orders_service.publicScheme.JobsDao;
import ru.instamart.jdbc.dao.orders_service.publicScheme.OrdersDao;
import ru.instamart.jdbc.dao.workflow.SegmentsDao;
import ru.instamart.jdbc.entity.workflow.SegmentsEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.ThreadUtil;

import java.util.List;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.WorkflowHelper.*;
import static ru.instamart.api.helper.WorkflowHelper.stopSegments;
import static ru.instamart.kraken.data.StartPointsTenants.*;

@Epic("On Demand")
@Feature("DISPATCH")
public class JobsTest extends RestBase {

    private OrderV2 order;
    private SegmentsEntity segments;


    @BeforeMethod(alwaysRun = true)
    public void preconditionsBeforeMethod() {
        Allure.step("Создание смены у универсала в СС (сервис смен)", () -> {
            final var user = UserManager.getShp6UniversalVidar();
            shopperApp.authorisation(user);
            //Удаляем все смены
            shiftsApi.cancelAllActiveShifts();
            shiftsApi.stopAllActiveShifts();
            shiftsApi.startOfShift(METRO_9);
        });

        Allure.step("Создание заказа на витрине", () -> {
            SessionFactory.makeSession(SessionType.API_V2);
            final var userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
            order = apiV2.orderOnDemand(userData, EnvironmentProperties.DEFAULT_SID);
        });
    }

    @TmsLink("178")
    @Story("Проверка джоб плановго заказа")
    @Test(description = "Получение информации о статусе джобов для планового заказа по полномк флоу",
            groups = "dispatch-orderservice-smoke")
    public void JobsPlannedOrder() {
        final var shipmentUUID = OrdersDao.INSTANCE.findByOrderNumber(order.getShipments().get(0).getNumber());
        final var jobsNew = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        final var assignmentsResponseList = getAllShopperAssignments();
        final var jobsOffering = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        final var assignmentAccept = acceptWorkflowAndStart(assignmentsResponseList.get(0).getId().toString(), METRO_9);
        final var jobsQueued = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        final var shipment = shopperApp.getShipmentsUUID(assignmentAccept.getSegment().getShipments().get(0).getUuid());

        segments = SegmentsDao.INSTANCE.getSegmentsByPerformerUuid(assignmentAccept.getPerformerUuid(),0);
        stopSegments(segments.getWorkflowId(), segments.getId(), METRO_9);
        final var jobsAssembly = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        shopperApp.getShipmentsStocks(shipment.getData().getId());
        final var shipmentFull = shopperApp.getShipments(shipment.getData().getId());
        final var itemsForAssembly = shopperApp.getItemsId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.assemblyItemPublic(shipmentFull.getData().getRelationships().getAssembly().getData().getId(),itemsForAssembly.get(0).getId(),itemsForAssembly.get(0).getAttributes().getQty());
        shopperApp.startPaymentVerificationWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.shopperCreatesPackageSetsWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.finishAssemblingWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.packerWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.startPurchasingWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.createReceiptsWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.startPackagingWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.getPackageSetsWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.packerCreatesPackageSetsWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.finishPurchasingWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());

        segments = SegmentsDao.INSTANCE.getSegmentsByPerformerUuid(assignmentAccept.getPerformerUuid(),1);
        stopSegments(segments.getWorkflowId(), segments.getId(), METRO_9);
        final var jobsFinishAssembly = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        segments = SegmentsDao.INSTANCE.getSegmentsByPerformerUuid(assignmentAccept.getPerformerUuid(),2);
        stopSegments(segments.getWorkflowId(), segments.getId(), METRO_9);
        final var jobsDelivery = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        segments = SegmentsDao.INSTANCE.getSegmentsByPerformerUuid(assignmentAccept.getPerformerUuid(),3);
        stopSegments(segments.getWorkflowId(), segments.getId(), METRO_9);
        final var jobsFinishOrder = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        Allure.step("Проверка джоб на правильный статус", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(jobsNew.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У сборки нового заказ статус не NEW");
            softAssert.assertEquals(jobsNew.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У доставки нового заказ статус не NEW");
            softAssert.assertEquals(jobsOffering.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.OFFERING.name(),"У сборки предложенного заказа статус не OFFERING");
            softAssert.assertEquals(jobsOffering.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.OFFERING.name(),"У доставки предложенного заказа статус не OFFERING");
            softAssert.assertEquals(jobsQueued.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.QUEUED.name(),"У сборки заказа в очереди статус не QUEUED");
            softAssert.assertEquals(jobsQueued.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.QUEUED.name(),"У доставки заказа в очереди статус не QUEUED");
            softAssert.assertEquals(jobsAssembly.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.IN_PROGRESS.name(),"У сборки заказа в сборке статус не IN_PROGRESS");
            softAssert.assertEquals(jobsAssembly.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.QUEUED.name(),"У доставки заказа в сборке статус не QUEUED");
            softAssert.assertEquals(jobsFinishAssembly.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.COMPLETED.name(),"У сборки заказа после сборки статус не COMPLETED");
            softAssert.assertEquals(jobsFinishAssembly.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.IN_PROGRESS.name(),"У доставки заказа после сборки статус не IN_PROGRESS");
            softAssert.assertEquals(jobsDelivery.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.COMPLETED.name(),"У сборки заказа в доставке статус не COMPLETED");
            softAssert.assertEquals(jobsDelivery.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.IN_PROGRESS.name(),"У доставки заказа в доставке статус не IN_PROGRESS");
            softAssert.assertEquals(jobsFinishOrder.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.COMPLETED.name(),"У сборки выполненого заказа статус не COMPLETED");
            softAssert.assertEquals(jobsFinishOrder.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.COMPLETED.name(),"У доставки выполненого заказа статус не COMPLETED");
            softAssert.assertAll();
        });
    }

    @TmsLink("181")
    @Story("Проверка джоб плановго заказа")
    @Test(description = "Получение информации о статусе джобов у отмененного заказа до начала распределения",
            groups = "dispatch-orderservice-smoke")
    public void JobsPlannedOrderCancelledNew() {

        final var shipmentUUID = OrdersDao.INSTANCE.findByOrderNumber(order.getShipments().get(0).getNumber());
        final var jobsNew = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        admin.authApi();
        final var response = OrderCancellationsAdminV1Request.POST(order.getNumber(), 1, "test");
        checkStatusCode200(response);

        ThreadUtil.simplyAwait(2);
        final var jobsCancelled = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        Allure.step("Проверка джоб на правильный статус", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(jobsNew.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У сборки нового заказ статус не NEW");
            softAssert.assertEquals(jobsNew.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У доставки нового заказ статус не NEW");
            softAssert.assertEquals(jobsCancelled.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.CANCELED.name(),"У сборки предложенного заказа статус не CANCELED");
            softAssert.assertEquals(jobsCancelled.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.CANCELED.name(),"У доставки предложенного заказа статус не CANCELED");
            softAssert.assertAll();
        });
    }

    @TmsLink("181")
    @Story("Проверка джоб плановго заказа")
    @Test(description = "Получение информации о статусе джобов у отмененного заказа в статусе предложенного",
            groups = "dispatch-orderservice-smoke")
    public void JobsPlannedOrderCancelledOffering() {
        final var shipmentUUID = OrdersDao.INSTANCE.findByOrderNumber(order.getShipments().get(0).getNumber());
        final var jobsNew = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        getAllShopperAssignments();
        final var jobsOffering = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        admin.authApi();
        final var response = OrderCancellationsAdminV1Request.POST(order.getNumber(), 1, "test");
        checkStatusCode200(response);

        ThreadUtil.simplyAwait(2);
        final var jobsCancelled = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        Allure.step("Проверка джоб на правильный статус", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(jobsNew.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У сборки нового заказ статус не NEW");
            softAssert.assertEquals(jobsNew.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У доставки нового заказ статус не NEW");
            softAssert.assertEquals(jobsOffering.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.OFFERING.name(),"У сборки предложенного заказа статус не OFFERING");
            softAssert.assertEquals(jobsOffering.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.OFFERING.name(),"У доставки предложенного заказа статус не OFFERING");
            softAssert.assertEquals(jobsCancelled.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.CANCELED.name(),"У сборки предложенного заказа статус не CANCELED");
            softAssert.assertEquals(jobsCancelled.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.CANCELED.name(),"У доставки предложенного заказа статус не CANCELED");
            softAssert.assertAll();
        });
    }

    @TmsLink("181")
    @Story("Проверка джоб плановго заказа")
    @Test(description = "Получение информации о статусе джобов у отмененного заказа до начала работы с ним",
            groups = "dispatch-orderservice-smoke")
    public void JobsPlannedOrderCancelledQueued() {
        final var shipmentUUID = OrdersDao.INSTANCE.findByOrderNumber(order.getShipments().get(0).getNumber());
        final var jobsNew = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        final var assignmentsResponseList = getAllShopperAssignments();
        final var jobsOffering = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        acceptWorkflowAndStart(assignmentsResponseList.get(0).getId().toString(), METRO_9);
        final var jobsQueued = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        admin.authApi();
        final var response = OrderCancellationsAdminV1Request.POST(order.getNumber(), 1, "test");
        checkStatusCode200(response);

        ThreadUtil.simplyAwait(2);
        final var jobsCancelled = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        Allure.step("Проверка джоб на правильный статус", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(jobsNew.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У сборки нового заказ статус не NEW");
            softAssert.assertEquals(jobsNew.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У доставки нового заказ статус не NEW");
            softAssert.assertEquals(jobsOffering.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.OFFERING.name(),"У сборки предложенного заказа статус не OFFERING");
            softAssert.assertEquals(jobsOffering.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.OFFERING.name(),"У доставки предложенного заказа статус не OFFERING");
            softAssert.assertEquals(jobsQueued.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.QUEUED.name(),"У сборки заказа в очереди статус не QUEUED");
            softAssert.assertEquals(jobsQueued.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.QUEUED.name(),"У доставки заказа в очереди статус не QUEUED");
            softAssert.assertEquals(jobsCancelled.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.CANCELED.name(),"У сборки предложенного заказа статус не CANCELED");
            softAssert.assertEquals(jobsCancelled.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.CANCELED.name(),"У доставки предложенного заказа статус не CANCELED");
            softAssert.assertAll();
        });
    }

    @TmsLink("181")
    @Story("Проверка джоб плановго заказа")
    @Test(description = "Получение информации о статусе джобов у отмененного заказа у которого начата сборка",
            groups = "dispatch-orderservice-smoke")
    public void JobsPlannedOrderCancelledAssembly() {
        final var shipmentUUID = OrdersDao.INSTANCE.findByOrderNumber(order.getShipments().get(0).getNumber());
        final var jobsNew = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        final var assignmentsResponseList = getAllShopperAssignments();
        final var jobsOffering = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        final var assignmentAccept = acceptWorkflowAndStart(assignmentsResponseList.get(0).getId().toString(), METRO_9);
        final var jobsQueued = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        shopperApp.getShipmentsUUID(assignmentAccept.getSegment().getShipments().get(0).getUuid());

        segments = SegmentsDao.INSTANCE.getSegmentsByPerformerUuid(assignmentAccept.getPerformerUuid(),0);
        stopSegments(segments.getWorkflowId(), segments.getId(), METRO_9);
        final var jobsAssembly = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        admin.authApi();
        final var response = OrderCancellationsAdminV1Request.POST(order.getNumber(), 1, "test");
        checkStatusCode200(response);

        ThreadUtil.simplyAwait(2);
        final var jobsCancelled = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        Allure.step("Проверка джоб на правильный статус", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(jobsNew.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У сборки нового заказ статус не NEW");
            softAssert.assertEquals(jobsNew.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У доставки нового заказ статус не NEW");
            softAssert.assertEquals(jobsOffering.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.OFFERING.name(),"У сборки предложенного заказа статус не OFFERING");
            softAssert.assertEquals(jobsOffering.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.OFFERING.name(),"У доставки предложенного заказа статус не OFFERING");
            softAssert.assertEquals(jobsQueued.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.QUEUED.name(),"У сборки заказа в очереди статус не QUEUED");
            softAssert.assertEquals(jobsQueued.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.QUEUED.name(),"У доставки заказа в очереди статус не QUEUED");
            softAssert.assertEquals(jobsAssembly.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.IN_PROGRESS.name(),"У сборки заказа в сборке статус не IN_PROGRESS");
            softAssert.assertEquals(jobsAssembly.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.QUEUED.name(),"У доставки заказа в сборке статус не QUEUED");
            softAssert.assertEquals(jobsCancelled.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.CANCELED.name(),"У сборки предложенного заказа статус не CANCELED");
            softAssert.assertEquals(jobsCancelled.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.CANCELED.name(),"У доставки предложенного заказа статус не CANCELED");
            softAssert.assertAll();
        });
    }

    @TmsLink("181")
    @Story("Проверка джоб плановго заказа")
    @Test(description = "Получение информации о статусе джобов у отмененного заказа у которого закончена сборка",
            groups = "dispatch-orderservice-smoke")
    public void JobsPlannedOrderCancelledFinishAssembly() {
        final var shipmentUUID = OrdersDao.INSTANCE.findByOrderNumber(order.getShipments().get(0).getNumber());
        final var jobsNew = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        final var assignmentsResponseList = getAllShopperAssignments();
        final var jobsOffering = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        final var assignmentAccept = acceptWorkflowAndStart(assignmentsResponseList.get(0).getId().toString(), METRO_9);
        final var jobsQueued = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        final var shipment = shopperApp.getShipmentsUUID(assignmentAccept.getSegment().getShipments().get(0).getUuid());

        segments = SegmentsDao.INSTANCE.getSegmentsByPerformerUuid(assignmentAccept.getPerformerUuid(),0);
        stopSegments(segments.getWorkflowId(), segments.getId(), METRO_9);
        final var jobsAssembly = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        shopperApp.getShipmentsStocks(shipment.getData().getId());
        final var shipmentFull = shopperApp.getShipments(shipment.getData().getId());
        final var itemsForAssembly = shopperApp.getItemsId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.assemblyItemPublic(shipmentFull.getData().getRelationships().getAssembly().getData().getId(),itemsForAssembly.get(0).getId(),itemsForAssembly.get(0).getAttributes().getQty());
        shopperApp.startPaymentVerificationWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.shopperCreatesPackageSetsWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.finishAssemblingWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.packerWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.startPurchasingWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.createReceiptsWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.startPackagingWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.getPackageSetsWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.packerCreatesPackageSetsWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.finishPurchasingWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());

        segments = SegmentsDao.INSTANCE.getSegmentsByPerformerUuid(assignmentAccept.getPerformerUuid(),1);
        stopSegments(segments.getWorkflowId(), segments.getId(), METRO_9);
        final var jobsFinishAssembly = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        admin.authApi();
        final var response = OrderCancellationsAdminV1Request.POST(order.getNumber(), 1, "test");
        checkStatusCode200(response);

        ThreadUtil.simplyAwait(2);
        final var jobsCancelled = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        Allure.step("Проверка джоб на правильный статус", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(jobsNew.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У сборки нового заказ статус не NEW");
            softAssert.assertEquals(jobsNew.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У доставки нового заказ статус не NEW");
            softAssert.assertEquals(jobsOffering.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.OFFERING.name(),"У сборки предложенного заказа статус не OFFERING");
            softAssert.assertEquals(jobsOffering.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.OFFERING.name(),"У доставки предложенного заказа статус не OFFERING");
            softAssert.assertEquals(jobsQueued.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.QUEUED.name(),"У сборки заказа в очереди статус не QUEUED");
            softAssert.assertEquals(jobsQueued.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.QUEUED.name(),"У доставки заказа в очереди статус не QUEUED");
            softAssert.assertEquals(jobsAssembly.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.IN_PROGRESS.name(),"У сборки заказа в сборке статус не IN_PROGRESS");
            softAssert.assertEquals(jobsAssembly.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.QUEUED.name(),"У доставки заказа в сборке статус не QUEUED");
            softAssert.assertEquals(jobsFinishAssembly.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.COMPLETED.name(),"У сборки заказа после сборки статус не COMPLETED");
            softAssert.assertEquals(jobsFinishAssembly.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.IN_PROGRESS.name(),"У доставки заказа после сборки статус не IN_PROGRESS");
            softAssert.assertEquals(jobsCancelled.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.CANCELED.name(),"У сборки предложенного заказа статус не CANCELED");
            softAssert.assertEquals(jobsCancelled.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.CANCELED.name(),"У доставки предложенного заказа статус не CANCELED");
            softAssert.assertAll();
        });
    }

    @TmsLink("181")
    @Story("Проверка джоб плановго заказа")
    @Test(description = "Получение информации о статусе джобов у отмененного заказа с начатой доставкой",
            groups = "dispatch-orderservice-smoke")
    public void JobsPlannedOrderDelivery() {
        final var shipmentUUID = OrdersDao.INSTANCE.findByOrderNumber(order.getShipments().get(0).getNumber());
        final var jobsNew = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        final var assignmentsResponseList = getAllShopperAssignments();
        final var jobsOffering = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        final var assignmentAccept = acceptWorkflowAndStart(assignmentsResponseList.get(0).getId().toString(), METRO_9);
        final var jobsQueued = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        final var shipment = shopperApp.getShipmentsUUID(assignmentAccept.getSegment().getShipments().get(0).getUuid());

        segments = SegmentsDao.INSTANCE.getSegmentsByPerformerUuid(assignmentAccept.getPerformerUuid(),0);
        stopSegments(segments.getWorkflowId(), segments.getId(), METRO_9);
        final var jobsAssembly = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        shopperApp.getShipmentsStocks(shipment.getData().getId());
        final var shipmentFull = shopperApp.getShipments(shipment.getData().getId());
        final var itemsForAssembly = shopperApp.getItemsId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.assemblyItemPublic(shipmentFull.getData().getRelationships().getAssembly().getData().getId(),itemsForAssembly.get(0).getId(),itemsForAssembly.get(0).getAttributes().getQty());
        shopperApp.startPaymentVerificationWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.shopperCreatesPackageSetsWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.finishAssemblingWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.packerWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.startPurchasingWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.createReceiptsWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.startPackagingWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.getPackageSetsWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.packerCreatesPackageSetsWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());
        shopperApp.finishPurchasingWithAssemblyId(shipmentFull.getData().getRelationships().getAssembly().getData().getId());

        segments = SegmentsDao.INSTANCE.getSegmentsByPerformerUuid(assignmentAccept.getPerformerUuid(),1);
        stopSegments(segments.getWorkflowId(), segments.getId(), METRO_9);
        final var jobsFinishAssembly = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        segments = SegmentsDao.INSTANCE.getSegmentsByPerformerUuid(assignmentAccept.getPerformerUuid(),2);
        stopSegments(segments.getWorkflowId(), segments.getId(), METRO_9);
        final var jobsDelivery = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        admin.authApi();
        final var response = OrderCancellationsAdminV1Request.POST(order.getNumber(), 1, "test");
        checkStatusCode200(response);

        ThreadUtil.simplyAwait(2);
        final var jobsCancelled = JobsDao.INSTANCE.findByShipmentUuid(shipmentUUID.getShipmentUuid());

        Allure.step("Проверка джоб на правильный статус", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(jobsNew.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У сборки нового заказ статус не NEW");
            softAssert.assertEquals(jobsNew.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У доставки нового заказ статус не NEW");
            softAssert.assertEquals(jobsOffering.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.OFFERING.name(),"У сборки предложенного заказа статус не OFFERING");
            softAssert.assertEquals(jobsOffering.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.OFFERING.name(),"У доставки предложенного заказа статус не OFFERING");
            softAssert.assertEquals(jobsQueued.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.QUEUED.name(),"У сборки заказа в очереди статус не QUEUED");
            softAssert.assertEquals(jobsQueued.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.QUEUED.name(),"У доставки заказа в очереди статус не QUEUED");
            softAssert.assertEquals(jobsAssembly.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.IN_PROGRESS.name(),"У сборки заказа в сборке статус не IN_PROGRESS");
            softAssert.assertEquals(jobsAssembly.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.QUEUED.name(),"У доставки заказа в сборке статус не QUEUED");
            softAssert.assertEquals(jobsFinishAssembly.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.COMPLETED.name(),"У сборки заказа после сборки статус не COMPLETED");
            softAssert.assertEquals(jobsFinishAssembly.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.IN_PROGRESS.name(),"У доставки заказа после сборки статус не IN_PROGRESS");
            softAssert.assertEquals(jobsDelivery.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.COMPLETED.name(),"У сборки заказа в доставке статус не COMPLETED");
            softAssert.assertEquals(jobsDelivery.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.IN_PROGRESS.name(),"У доставки заказа в доставке статус не IN_PROGRESS");
            softAssert.assertEquals(jobsCancelled.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.CANCELED.name(),"У сборки предложенного заказа статус не CANCELED");
            softAssert.assertEquals(jobsCancelled.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.CANCELED.name(),"У доставки предложенного заказа статус не CANCELED");
            softAssert.assertAll();
        });
    }

}