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
import ru.instamart.api.model.workflows.Error;
import ru.instamart.api.model.workflows.Shift;
import ru.instamart.api.model.workflows.ShopperAssignment;
import ru.instamart.api.request.workflows.AssignmentsRequest;
import ru.instamart.api.request.workflows.SegmentsRequest;
import ru.instamart.api.request.workflows.WorkflowsRequest;
import ru.instamart.api.response.workflows.AssignmentResponse;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.jdbc.dao.workflow.AssignmentsDao;
import ru.instamart.jdbc.dao.workflow.SegmentsDao;
import ru.instamart.jdbc.entity.workflow.AssignmentsEntity;
import ru.instamart.jdbc.entity.workflow.SegmentsEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.common.Mapper;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import shipment_pricing.OrderPricing;
import shipment_pricing.ShipmentPriceServiceGrpc;
import workflow.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.DispatchCheckpoints.checkSegments;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.api.helper.DispatchHelper.*;
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
    private String workflowUuid;
    private String assignmentId;
    private List<SegmentsEntity> segments;

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
        var firstRequest = getWorkflowsRequestWithDifferentParams(order, shipmentUuid, secondOrder, secondShipmentUuid, "");
        var firstResponse = clientWorkflow.createWorkflows(firstRequest);
        compareTwoObjects(firstResponse.getResultsMap().get(firstResponse.getResultsMap().keySet().toArray()[0].toString()).toString(), "");
        var secondRequest = getWorkflowsRequest(order, shipmentUuid, Timestamps.MAX_VALUE, WorkflowEnums.DeliveryType.DEFAULT);
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
        var requestForCreation = getWorkflowsRequest(order, shipmentUuid, Timestamps.MAX_VALUE, WorkflowEnums.DeliveryType.DEFAULT);

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
        var request = getWorkflowsRequest(order, shipmentUuid, Timestamps.MAX_VALUE, WorkflowEnums.DeliveryType.DEFAULT);

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
        var request = getWorkflowsRequest(order, shipmentUuid, Timestamps.MAX_VALUE, WorkflowEnums.DeliveryType.TAXI);

        var response = clientWorkflow.createWorkflows(request);

        compareTwoObjects(response.getResultsMap().get(response.getResultsMap().keySet().toArray()[0].toString()).toString(), "");
        cancelWorkflow(clientWorkflow, shipmentUuid);
        String workflowUuid = response.getResultsMap().keySet().toArray()[0].toString();
        checkFieldIsNotEmpty(workflowUuid, "uuid workflow");

        List<AssignmentChangedOuterClass.AssignmentChanged> assignments = kafka.waitDataInKafkaTopicWorkflowAssignment(workflowUuid);
        long workflowId = assignments.get(0).getWorkflowId();
        List<ExternalDeliveryOuterClass.ExternalDelivery> externalDeliveries = kafka.waitDataInKafkaTopicWorkflowExternalDelivery(workflowUuid);
        List<WorkflowChangedOuterClass.WorkflowChanged> workflows = kafka.waitDataInKafkaTopicWorkflow(workflowId);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(workflows.get(0).getStatus(), WorkflowChangedOuterClass.WorkflowChanged.Status.NEW, softAssert);
        compareTwoObjects(assignments.get(0).getStatus(), AssignmentChangedOuterClass.AssignmentChanged.Status.OFFERED, softAssert);
        compareTwoObjects(externalDeliveries.get(0).getDeliveryType(), WorkflowEnums.DeliveryType.TAXI, softAssert);
        softAssert.assertAll();
    }

    @CaseId(82)
    @Test(description = "Обогащение идентификатором смены и видом транспорта",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "createWorkflowForTaxi")
    public void createWorkflowWithTransport() {
        var request = getWorkflowsRequestWithTransport(order, shipmentUuid, 1, WorkflowOuterClass.Shift.Transport.CAR);

        var response = clientWorkflow.createWorkflows(request);

        compareTwoObjects(response.getResultsMap().get(response.getResultsMap().keySet().toArray()[0].toString()).toString(), "");
        String workflowUuid = response.getResultsMap().keySet().toArray()[0].toString();
        checkFieldIsNotEmpty(workflowUuid, "Не вернулся uuid workflow");
        cancelWorkflow(clientWorkflow, shipmentUuid);
        AssignmentsEntity assignmentsEntity = AssignmentsDao.INSTANCE.getAssignmentByWorkflowUuid(workflowUuid);
        Shift shift = Mapper.INSTANCE.jsonToObject(assignmentsEntity.getShift(), Shift.class);
        compareTwoObjects(shift.getTransport(), WorkflowOuterClass.Shift.Transport.CAR_VALUE);
    }

    @CaseId(90)
    @Test(description = "Обогащение названием и адресом магазина нескольких сегментов с разными магазинами",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "createWorkflowWithTransport")
    public void createWorkflowWithDifferentStores() {
        var request = getWorkflowsRequestWithDifferentStores(order, shipmentUuid);

        var response = clientWorkflow.createWorkflows(request);

        compareTwoObjects(response.getResultsMap().get(response.getResultsMap().keySet().toArray()[0].toString()).toString(), "");
        workflowUuid = response.getResultsMap().keySet().toArray()[0].toString();
        checkFieldIsNotEmpty(workflowUuid, "Не вернулся uuid workflow");
        cancelWorkflow(clientWorkflow, shipmentUuid);
        List<SegmentsEntity> segments = SegmentsDao.INSTANCE.getSegmentsByWorkflowUuid(workflowUuid).stream()
                .sorted(Comparator.comparing(SegmentsEntity::getType)).collect(Collectors.toList());

        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(segments.get(0).getStoreName(), "METRO, Дорожная", softAssert);
        compareTwoObjects(segments.get(0).getStoreAddress(), "Москва, Дорожная,  д. 1, корп. 1", softAssert);
        compareTwoObjects(segments.get(1).getStoreName(), "METRO, Нижний Новгород Нартова", softAssert);
        compareTwoObjects(segments.get(1).getStoreAddress(), "ул. Степная, д. 75", softAssert);
        compareTwoObjects(segments.get(2).getStoreName(), "METRO, просп. Мира", softAssert);
        compareTwoObjects(segments.get(2).getStoreAddress(), "Москва, просп. Мира, 211, стр. 1", softAssert);
        softAssert.assertAll();
    }

    @CaseId(120)
    @Test(description = "Создание отложенного назначения отдельно от родительского",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "createWorkflowWithDifferentStores")
    public void createWorkflowWithNotAvailableParentWorkflow() {
        var request = getWorkflowsRequestWithDifferentParams(order, shipmentUuid, order, shipmentUuid, workflowUuid);

        var response = clientWorkflow.createWorkflows(request);

        final SoftAssert softAssert = new SoftAssert();
        for (WorkflowOuterClass.CreateWorkflowsResponse.Result result : response.getResultsMap().values()) {
            compareTwoObjects(result.getStatus().toString(), "FAILURE", softAssert);
            compareTwoObjects(result.getErrorText(), "Parent assignment not available", softAssert);
            compareTwoObjects(result.getErrorKind().toString(), "PARENT_NOT_AVAILABLE", softAssert);
        }
        softAssert.assertAll();
    }

    @CaseId(37)
    @Test(description = "Создание маршрутного листа за 30 секунд от текущего времени",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "createWorkflowWithNotAvailableParentWorkflow")
    public void createWorkflow30SecToNow() {
        var request = getWorkflowsRequest(order, shipmentUuid, Timestamps.fromDate(getDateWithSec(30)), WorkflowEnums.DeliveryType.DEFAULT);

        var response = clientWorkflow.createWorkflows(request);

        compareTwoObjects(response.getResultsMap().get(response.getResultsMap().keySet().toArray()[0].toString()).toString(), "");
        workflowUuid = response.getResultsMap().keySet().toArray()[0].toString();
        checkFieldIsNotEmpty(workflowUuid, "Не вернулся uuid workflow");
        List<AssignmentChangedOuterClass.AssignmentChanged> assignments = kafka.waitDataInKafkaTopicWorkflowAssignment(workflowUuid);
        workflowId = assignments.get(0).getWorkflowId();
        List<WorkflowChangedOuterClass.WorkflowChanged> workflows = kafka.waitDataInKafkaTopicWorkflow(workflowId);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(workflows.get(0).getStatus(), WorkflowChangedOuterClass.WorkflowChanged.Status.NEW, softAssert);
        compareTwoObjects(assignments.get(0).getStatus(), AssignmentChangedOuterClass.AssignmentChanged.Status.OFFERED, softAssert);
        softAssert.assertAll();
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
        assignmentId = shopperAssignments.get(0).getId();
        compareTwoObjects(shopperAssignments.size(), 1);
        compareTwoObjects(shopperAssignments.get(0).getWorkflowId(), String.valueOf(workflowId));
    }

    @CaseId(118)
    @Test(description = "Создание отложенного назначения отдельно от родительского",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "getShopperAssignments")
    public void createWorkflowWithParentWorkflow() {
        var request = getWorkflowsRequestWithDifferentParams(order, shipmentUuid, secondOrder, secondShipmentUuid, workflowUuid);

        var response = clientWorkflow.createWorkflows(request);

        compareTwoObjects(response.getResultsMap().get(response.getResultsMap().keySet().toArray()[0].toString()).toString(), "");
        String childWorkflowUuid = response.getResultsMap().keySet().toArray()[0].toString();
        compareTwoObjects(AssignmentsDao.INSTANCE.getAssignmentByWorkflowUuid(childWorkflowUuid).getPostponedParentUuid(), workflowUuid);
    }

    @CaseId(20)
    @Test(description = "Пометка назначения как просмотренного",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "createWorkflowWithParentWorkflow")
    public void markWorkflowAsSeen() {
        final Response response = AssignmentsRequest.Seen.PATCH(assignmentId);
        checkStatusCode(response, 204);
        List<AssignmentChangedOuterClass.AssignmentChanged> assignments = kafka.waitDataInKafkaTopicWorkflowAssignment(workflowUuid);
        compareTwoObjects(assignments.get(assignments.size() - 1).getStatus(), AssignmentChangedOuterClass.AssignmentChanged.Status.SEEN);
    }

    @CaseId(21)
    @Test(description = "Пометка назначения как просмотренного",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "markWorkflowAsSeen")
    public void markWorkflowAsAccepted() {
        final Response response = AssignmentsRequest.Accept.PATCH(assignmentId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AssignmentResponse.class);
        List<AssignmentChangedOuterClass.AssignmentChanged> assignments = kafka.waitDataInKafkaTopicWorkflowAssignment(workflowUuid);
        compareTwoObjects(assignments.get(assignments.size() - 1).getStatus(), AssignmentChangedOuterClass.AssignmentChanged.Status.ACCEPTED);
    }

    @CaseId(65)
    @Test(description = "Получение активных маршрутных листов в статусах queued/in progress",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "markWorkflowAsAccepted")
    public void getWorkflows() {
        final Response response = WorkflowsRequest.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AssignmentResponse[].class);
        List<AssignmentResponse> assignments = Arrays.asList(response.as(AssignmentResponse[].class));
        compareTwoObjects(assignments.size(), 1);
        compareTwoObjects(assignments.get(0).getSegment().getWorkflowId(), workflowId);
        cancelWorkflow(clientWorkflow, shipmentUuid);
    }

    @CaseId(129)
    @Test(description = "Автостарт маршрутного листа при принятии назначения с отправкой геопозиции",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "getWorkflows")
    public void acceptWorkflowWithCoordinates() {
        var request = getWorkflowsRequest(order, shipmentUuid, Timestamps.fromDate(getDateWithSec(30)), WorkflowEnums.DeliveryType.DEFAULT);
        var createResponse = clientWorkflow.createWorkflows(request);
        workflowUuid = createResponse.getResultsMap().keySet().toArray()[0].toString();
        AssignmentsEntity assignmentsEntity = AssignmentsDao.INSTANCE.getAssignmentByWorkflowUuid(workflowUuid);

        final Response response = AssignmentsRequest.Accept.PATCH(assignmentsEntity.getId().toString(), 37.62097454092305, 55.60166514312233);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AssignmentResponse.class);
        segments = SegmentsDao.INSTANCE.getSegmentsByWorkflowUuid(workflowUuid).stream()
                .sorted(Comparator.comparing(SegmentsEntity::getPosition)).collect(Collectors.toList());
        checkFieldIsNotEmpty(segments.get(0).getFactStartedAt(), "время начала маршрута");
    }

    @CaseIDs(value = {@CaseId(23), @CaseId(108)})
    @Test(description = "Начало первого сегмента маршрутного листа в статусе Queued",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "acceptWorkflowWithCoordinates")
    public void passWorkflow() {
        SegmentsRequest.SegmentData segmentData = SegmentsRequest.SegmentData.builder()
                .segmentId(segments.get(0).getId())
                .lat(55.60166514312233)
                .lon(37.62097454092305)
                .build();
        final Response response = SegmentsRequest.PATCH(segments.get(0).getWorkflowId(),segmentData);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AssignmentResponse.class);

        List<AssignmentChangedOuterClass.AssignmentChanged> assignments = kafka.waitDataInKafkaTopicWorkflowAssignment(workflowUuid);
        compareTwoObjects(assignments.get(assignments.size() - 1).getStatus(), AssignmentChangedOuterClass.AssignmentChanged.Status.ACCEPTED);
        List<SegmentChangedOuterClass.SegmentChanged> segmentsFromKafka = kafka.waitDataInKafkaTopicWorkflowSegment(segments.get(1).getId());
        segments = SegmentsDao.INSTANCE.getSegmentsByWorkflowUuid(workflowUuid).stream()
                .sorted(Comparator.comparing(SegmentsEntity::getPosition)).collect(Collectors.toList());
        checkSegments(segments, segmentsFromKafka, 0, 1, WorkflowEnums.SegmentType.DELIVERY);
    }

    @CaseId(111)
    @Test(description = "Проверка геолокации при окончании arrive/delivery сегментов",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "passWorkflow")
    public void passWorkflowWithLongDistance() {
        SegmentsRequest.SegmentData segmentData = SegmentsRequest.SegmentData.builder()
                .segmentId(segments.get(1).getId())
                .lat(34.60166514312233)
                .lon(17.62097454092305)
                .skipGeoWarn(false)
                .build();
        final Response response = SegmentsRequest.PATCH(segments.get(1).getWorkflowId(),segmentData);
        checkStatusCode422(response);
        Error error = response.as(Error.class);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(error.getTitle(), "Performer has long distance to segment", softAssert);
        compareTwoObjects(error.getType(), "performer-has-long-distance-to-segment", softAssert);
        softAssert.assertAll();
    }

    @CaseId(111)
    @Test(description = "Отсуствие проверки геолокации при окончании arrive/delivery сегментов",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "passWorkflowWithLongDistance")
    public void passWorkflowWithLongDistanceWithoutCheck() {
        SegmentsRequest.SegmentData segmentData = SegmentsRequest.SegmentData.builder()
                .segmentId(segments.get(1).getId())
                .lat(34.60166514312233)
                .lon(17.62097454092305)
                .skipGeoWarn(true)
                .build();
        final Response response = SegmentsRequest.PATCH(segments.get(1).getWorkflowId(), segmentData);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AssignmentResponse.class);

        List<SegmentChangedOuterClass.SegmentChanged> segmentsFromKafka = kafka.waitDataInKafkaTopicWorkflowSegment(segments.get(2).getId());
        segments = SegmentsDao.INSTANCE.getSegmentsByWorkflowUuid(workflowUuid).stream()
                .sorted(Comparator.comparing(SegmentsEntity::getPosition)).collect(Collectors.toList());
        checkSegments(segments, segmentsFromKafka, 1, 2, WorkflowEnums.SegmentType.PASS_TO_CLIENT);
        cancelWorkflow(clientWorkflow, shipmentUuid);
    }

    @CaseId(103)
    @Test(description = "Создание маршрутного листа с сегментом, который был пройден в отклонненом маршрутном листе",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "passWorkflowWithLongDistanceWithoutCheck")
    public void createWorkflowWithPassedSegment() {
        var request = getWorkflowsRequest(order, shipmentUuid, Timestamps.fromDate(getDateWithSec(30)), WorkflowEnums.DeliveryType.DEFAULT);
        var response = clientWorkflow.createWorkflows(request);

        compareTwoObjects(response.getResultsMap().get(response.getResultsMap().keySet().toArray()[0].toString()).toString(), "");
        workflowUuid = response.getResultsMap().keySet().toArray()[0].toString();
        checkFieldIsNotEmpty(workflowUuid, "Не вернулся uuid workflow");
        List<AssignmentChangedOuterClass.AssignmentChanged> assignments = kafka.waitDataInKafkaTopicWorkflowAssignment(workflowUuid);
        workflowId = assignments.get(0).getWorkflowId();
        compareTwoObjects(assignments.get(0).getStatus(), AssignmentChangedOuterClass.AssignmentChanged.Status.OFFERED);
    }

    @CaseId(22)
    @Test(description = "Создание маршрутного листа с сегментом, который был пройден в отклонненом маршрутном листе",
            groups = "dispatch-workflow-regress",
            dependsOnMethods = "createWorkflowWithPassedSegment")
    public void cancelOfferedWorkflow() {
        AssignmentsEntity assignmentsEntity = AssignmentsDao.INSTANCE.getAssignmentByWorkflowUuid(workflowUuid);
        final Response response = AssignmentsRequest.Decline.PATCH(assignmentsEntity.getId());
        checkStatusCode(response, 204);

        List<AssignmentChangedOuterClass.AssignmentChanged> assignments = kafka.waitDataInKafkaTopicWorkflowAssignment(workflowUuid);
        List<WorkflowChangedOuterClass.WorkflowChanged> workflows = kafka.waitDataInKafkaTopicWorkflow(workflowId);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(workflows.get(workflows.size() - 1).getStatus(), WorkflowChangedOuterClass.WorkflowChanged.Status.CANCELED, softAssert);
        compareTwoObjects(assignments.get(assignments.size() - 1).getStatus(), AssignmentChangedOuterClass.AssignmentChanged.Status.DECLINED, softAssert);
        softAssert.assertAll();
    }


    @AfterClass(alwaysRun = true)
    public void clearData() {
        cancelWorkflow(clientWorkflow, secondShipmentUuid);
        cancelWorkflow(clientWorkflow, shipmentUuid);
    }
}
