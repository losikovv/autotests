package ru.instamart.test.api.dispatch.workflow;

import com.google.protobuf.Duration;
import com.google.protobuf.util.Timestamps;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.workflows.ShopperAssignment;
import ru.instamart.api.request.workflows.AssignmentsRequest;
import ru.instamart.api.request.workflows.WorkflowsRequest;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.SpreeShipmentsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;
import shipment_pricing.OrderPricing;
import shipment_pricing.ShipmentPriceServiceGrpc;
import workflow.AssignmentChangedOuterClass;
import workflow.ServiceGrpc;
import workflow.WorkflowEnums;
import workflow.WorkflowOuterClass;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.DispatchHelper.cancelWorkflow;
import static ru.instamart.api.helper.DispatchHelper.getCreateWorkflowsRequest;
import static ru.instamart.kraken.util.TimeUtil.getDateWithSec;

@Epic("Dispatch")
@Feature("Workflow")
public class WorkflowTest extends RestBase {

    private ServiceGrpc.ServiceBlockingStub clientWorkflow;
    private ShipmentPriceServiceGrpc.ShipmentPriceServiceBlockingStub clientAnalytics;
    private OrderV2 order;
    private OrderV2 secondOrder;
    private String shipmentUuid;
    private String secondShipmentUuid;
    private long workflowId;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientWorkflow = ServiceGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_WORKFLOW));
        clientAnalytics = ShipmentPriceServiceGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_ANALYTICS_ORDER_PRICING));
        SessionFactory.makeSession(SessionType.API_V2);
        order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        secondOrder = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        secondShipmentUuid = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(secondOrder.getShipments().get(0).getNumber()).getUuid();
        shipmentUuid = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber()).getUuid();
        shopperApp.authorisation(UserManager.getDefaultShopper());
    }

    @CaseId(101)
    @Test(description = "Создание маршрутных листов с одинаковым ARRIVE сегментом",
            groups = "dispatch-workflow-regress")
    public void createWorkflowWithSameArriveSegment() {
        var firstRequest = WorkflowOuterClass.CreateWorkflowsRequest
                .newBuilder()
                .addWorkflows(WorkflowOuterClass.Workflow.newBuilder()
                        .addAssignments(WorkflowOuterClass.Assignment.newBuilder()
                                .setPerformerUuid(UserManager.getShp6Shopper2().getUuid())
                                .setSourceTypeValue(WorkflowEnums.SourceType.DISPATCH_VALUE)
                                .setPerformerVehicleValue(WorkflowEnums.PerformerVehicle.PEDESTRIAN_VALUE)
                                .setDeliveryTypeValue(WorkflowEnums.DeliveryType.DEFAULT_VALUE)
                                .build())
                        .addSegments(WorkflowOuterClass.Segment.newBuilder()
                                .setTypeValue(WorkflowEnums.SegmentType.ARRIVE_VALUE)
                                .setPosition(0)
                                .addShipments(WorkflowEnums.Shipment.newBuilder()
                                        .setNumber(order.getShipments().get(0).getNumber())
                                        .setUuid(shipmentUuid)
                                        .setItemsCount(1500)
                                        .setItemsTotalAmount(2539.5f)
                                        .setWeightKg(10f)
                                        .setStoreUuid("6bc4dc40-37a0-45fe-ac7f-d4185c29da63")
                                        .setIsHeavy(false)
                                        .setIsNew(false)
                                        .setStoreName("METRO, Дорожная")
                                        .setStoreAddress("Москва, Дорожная,  д. 1, корп. 1")
                                        .build())
                                .setLocationStart(WorkflowEnums.Location.newBuilder()
                                        .setLat(55.63533180125311)
                                        .setLon(37.62462253918757)
                                        .build())
                                .setPlanStartedAt(Timestamps.MAX_VALUE)
                                .setLocationEnd(WorkflowEnums.Location.newBuilder()
                                        .setLat(55.60166514312233)
                                        .setLon(37.62097454092305)
                                        .build())
                                .setPlanEndedAt(Timestamps.MAX_VALUE)
                                .setDuration(Duration.newBuilder().setSeconds(10).build())
                                .setDistance(25L)
                                .build())
                        .addSegments(WorkflowOuterClass.Segment.newBuilder()
                                .setTypeValue(WorkflowEnums.SegmentType.DELIVERY_VALUE)
                                .setPosition(2)
                                .addShipments(WorkflowEnums.Shipment.newBuilder()
                                        .setNumber(secondOrder.getShipments().get(0).getNumber())
                                        .setUuid(secondShipmentUuid)
                                        .setItemsCount(1520)
                                        .setItemsTotalAmount(1539.5f)
                                        .setWeightKg(12f)
                                        .setStoreUuid("6ac61a21-942b-47af-b07d-7b5fb7ffb8fa")
                                        .setIsHeavy(false)
                                        .setIsNew(false)
                                        .setStoreName("METRO, Дорожная")
                                        .setStoreAddress("Москва, Дорожная,  д. 1, корп. 1")
                                        .build())
                                .setLocationStart(WorkflowEnums.Location.newBuilder()
                                        .setLat(56.63533180125311)
                                        .setLon(39.62462253918757)
                                        .build())
                                .setPlanStartedAt(Timestamps.fromDate(new Date()))
                                .setLocationEnd(WorkflowEnums.Location.newBuilder()
                                        .setLat(57.60166514312233)
                                        .setLon(39.62097454092305)
                                        .build())
                                .setPlanEndedAt(Timestamps.fromDate(new Date()))
                                .setDuration(Duration.newBuilder().setSeconds(12).build())
                                .setDistance(24L)
                                .build())
                        .addSegments(WorkflowOuterClass.Segment.newBuilder()
                                .setTypeValue(WorkflowEnums.SegmentType.PASS_TO_CLIENT_VALUE)
                                .setPosition(3)
                                .addShipments(WorkflowEnums.Shipment.newBuilder()
                                        .setNumber(secondOrder.getShipments().get(0).getNumber())
                                        .setUuid(secondShipmentUuid)
                                        .setItemsCount(1502)
                                        .setItemsTotalAmount(2139.5f)
                                        .setWeightKg(11f)
                                        .setStoreUuid("6ac61a21-942b-47af-b07d-7b5fb7ffb8fa")
                                        .setIsHeavy(false)
                                        .setIsNew(false)
                                        .setStoreName("METRO, Дорожная")
                                        .setStoreAddress("Москва, Дорожная,  д. 1, корп. 1")
                                        .build())
                                .setLocationStart(WorkflowEnums.Location.newBuilder()
                                        .setLat(55.63533180188311)
                                        .setLon(37.62462253918857)
                                        .build())
                                .setPlanStartedAt(Timestamps.fromDate(new Date()))
                                .setLocationEnd(WorkflowEnums.Location.newBuilder()
                                        .setLat(55.60166514312283)
                                        .setLon(37.62097454092308)
                                        .build())
                                .setPlanEndedAt(Timestamps.fromDate(new Date()))
                                .setDuration(Duration.newBuilder().setSeconds(14).build())
                                .setDistance(11L)
                                .build())
                        .build())
                .build();

        var firstResponse = clientWorkflow.createWorkflows(firstRequest);
        compareTwoObjects(firstResponse.getResultsMap().get(firstResponse.getResultsMap().keySet().toArray()[0].toString()).toString(), "");
        var secondRequest = getCreateWorkflowsRequest(order, shipmentUuid, Timestamps.MAX_VALUE, WorkflowEnums.DeliveryType.DEFAULT);
        var secondResponse = clientWorkflow.createWorkflows(secondRequest);
        compareTwoObjects(secondResponse.getResultsMap().get(secondResponse.getResultsMap().keySet().toArray()[0].toString()).toString(), "");

        cancelWorkflow(clientWorkflow, secondShipmentUuid);
        cancelWorkflow(clientWorkflow, shipmentUuid);
    }

    @CaseId(44)
    @Test(description = "Получение стоимости сегментов",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "createWorkflowWithSameArriveSegment")
    public void getShipmentPrice() {
        var requestForCreation = getCreateWorkflowsRequest(order, shipmentUuid, Timestamps.MAX_VALUE, WorkflowEnums.DeliveryType.DEFAULT);

        var responseForCreation = clientWorkflow.createWorkflows(requestForCreation);

        var request = OrderPricing.GetShipmentPriceRequest
                .newBuilder()
                .setWorkassignId(responseForCreation.getResultsMap().keySet().toArray()[0].toString())
                .setWorkassignType("ASSEMBLY")
                .setAssignedShopper(OrderPricing.Shopper.newBuilder()
                        .setShopperUuid(UserManager.getDefaultShopper().getUuid())
                        .setShopperType("PEDESTRIAN")
                        .build())
                .setShipment(OrderPricing.Shipment.newBuilder()
                        .setStoreInfo(OrderPricing.Store.newBuilder()
                                .setStoreUuid("6bc4dc40-37a0-45fe-ac7f-d4185c29da63")
                                .setStoreLocation(OrderPricing.Location.newBuilder()
                                        .setLat(54.92525f)
                                        .setLon(73.481058f)
                                        .build())
                                .build())
                        .setDeliveryLocation(OrderPricing.Location.newBuilder()
                                .setLat(54.92525f)
                                .setLon(73.481058f)
                                .build())
                        .setCart(OrderPricing.UserCart.newBuilder()
                                .setWeight(1f)
                                .setItemCount(10f)
                                .build())
                        .build())
                .build();

        var response = clientAnalytics.getShipmentPrice(request);

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getBaseCost(), 100f);
        softAssert.assertEquals(response.getBonusCost(), 10f);
        softAssert.assertAll();
    }

    @CaseId(48)
    @Test(description = "Создание маршрутного листа с существующим сегментом",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "getShipmentPrice")
    public void createWorkflowWithExistingSegment() {
        var request = getCreateWorkflowsRequest(order, shipmentUuid, Timestamps.MAX_VALUE, WorkflowEnums.DeliveryType.DEFAULT);

        var responseForCreation = clientWorkflow.createWorkflows(request);

        final SoftAssert softAssert = new SoftAssert();
        for (WorkflowOuterClass.CreateWorkflowsResponse.Result result : responseForCreation.getResultsMap().values()) {
            compareTwoObjects(result.getStatus().toString(), "FAILURE", softAssert);
            compareTwoObjects(result.getErrorText(), "Has active workflow with intersection segments", softAssert);
            compareTwoObjects(result.getErrorKind().toString(), "HAS_ACTIVE_WORKFLOW_WITH_INTERSECTION_SEGMENTS", softAssert);
        }
        softAssert.assertAll();
    }

    @CaseId(141)
    @Test(description = "Отмена маршрутного листа по uuid шимпента",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "createWorkflowWithExistingSegment")
    public void cancelExistingWorkflow() {
        var request = WorkflowOuterClass.RejectWorkflowByShipmentUuidRequest
                .newBuilder()
                .setShipmentUuid(shipmentUuid)
                .build();

        var response = clientWorkflow.rejectWorkflowByShipmentUUID(request);

        Assert.assertFalse(response.getWorkflowIdsList().isEmpty(), "Маршрутные листы не удалились");
    }

    @CaseId(73)
    @Test(description = "Создание маршрутного листа для такси",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "cancelExistingWorkflow")
    public void createWorkflowForTaxi() {
        var request = getCreateWorkflowsRequest(order, shipmentUuid, Timestamps.MAX_VALUE, WorkflowEnums.DeliveryType.TAXI);

        var response = clientWorkflow.createWorkflows(request);

        compareTwoObjects(response.getResultsMap().get(response.getResultsMap().keySet().toArray()[0].toString()).toString(), "");
        cancelWorkflow(clientWorkflow, shipmentUuid);
    }

    @CaseId(37)
    @Test(description = "Создание маршрутного листа за 30 секунд от текущего времени",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "createWorkflowForTaxi")
    public void createWorkflow30SecToNow() {
        var request = getCreateWorkflowsRequest(order, shipmentUuid, Timestamps.fromDate(getDateWithSec(30)), WorkflowEnums.DeliveryType.DEFAULT);

        var response = clientWorkflow.createWorkflows(request);

        compareTwoObjects(response.getResultsMap().get(response.getResultsMap().keySet().toArray()[0].toString()).toString(), "");
        String workflowUuid = response.getResultsMap().keySet().toArray()[0].toString();
        checkFieldIsNotEmpty(workflowUuid, "Не вернулся uuid workflow");
        List<AssignmentChangedOuterClass.AssignmentChanged> assignments = kafka.waitDataInKafkaTopicWorkflowAssignment(workflowUuid);
        workflowId = assignments.get(0).getWorkflowId();
        compareTwoObjects(assignments.get(0).getStatus(), AssignmentChangedOuterClass.AssignmentChanged.Status.OFFERED);
    }

    @CaseId(57)
    @Test(description = "Получение назначений в статусе offered/seen",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "createWorkflow30SecToNow")
    public void getShopperAssignments() {
        final Response response = AssignmentsRequest.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShopperAssignment[].class);
        List<ShopperAssignment> shopperAssignments = Arrays.asList(response.as(ShopperAssignment[].class));
        compareTwoObjects(shopperAssignments.size(), 1);
        compareTwoObjects(shopperAssignments.get(0).getWorkflowId(), String.valueOf(workflowId));
    }

    @CaseId(65)
    @Test(description = "Получение активных маршрутных листов в статусах queued/in progress",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "createWorkflow30SecToNow")
    public void getWorkflows() {
        final Response response = WorkflowsRequest.GET();
        checkStatusCode200(response);
        //TODO: добавить проверки после создания нужных методов

    }

    @AfterClass(alwaysRun = true)
    public void clearData() {
        cancelWorkflow(clientWorkflow, secondShipmentUuid);
        cancelWorkflow(clientWorkflow, shipmentUuid);
    }
}
