package ru.instamart.test.api.dispatch.workflow;

import com.google.protobuf.util.Timestamps;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.workflows.AssignmentsRequest;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.jdbc.dao.workflow.AssignmentsDao;
import ru.instamart.jdbc.dao.workflow.SegmentsDao;
import ru.instamart.jdbc.dao.workflow.WorkflowsDao;
import ru.instamart.jdbc.entity.workflow.AssignmentsEntity;
import ru.instamart.jdbc.entity.workflow.SegmentsEntity;
import ru.instamart.jdbc.entity.workflow.WorkflowsEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.ThreadUtil;
import ru.sbermarket.qase.annotation.CaseId;
import shipment_pricing.ShipmentPriceServiceGrpc;
import workflow.AssignmentChangedOuterClass;
import workflow.ServiceGrpc;
import workflow.WorkflowChangedOuterClass;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.WorkflowCheckpoints.checkStatuses;
import static ru.instamart.api.checkpoint.WorkflowCheckpoints.checkWorkflowChanges;
import static ru.instamart.api.helper.WorkflowHelper.*;
import static ru.instamart.kraken.data.StartPointsTenants.METRO_WORKFLOW_END;
import static ru.instamart.kraken.util.TimeUtil.getDateMinusSec;
import static ru.instamart.kraken.util.TimeUtil.getDatePlusSec;
import static workflow.AssignmentChangedOuterClass.AssignmentChanged.Status.*;

@Epic("On Demand")
@Feature("Workflow")
public class TimeoutWorkflowTest extends RestBase {

    private ServiceGrpc.ServiceBlockingStub clientWorkflow;
    private ShipmentPriceServiceGrpc.ShipmentPriceServiceBlockingStub clientAnalytics;
    private OrderV2 order;
    private OrderV2 secondOrder;
    private String shipmentUuid;
    private String secondShipmentUuid;


    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientWorkflow = ServiceGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_WORKFLOW));
        clientAnalytics = ShipmentPriceServiceGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_ANALYTICS_ORDER_PRICING));
        SessionFactory.makeSession(SessionType.API_V2);
        order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        secondOrder = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        secondShipmentUuid = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(secondOrder.getShipments().get(0).getNumber()).getUuid();
        shipmentUuid = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber()).getUuid();
        shopperApp.authorisation(UserManager.getShp6Shopper4());
    }

    @CaseId(35)
    @Test(description = "Таймаут назначения", enabled = false, //TODO: посмотреть, как стабилизировать тесты, кроме увеличения времени
            groups = "dispatch-workflow-smoke")
    public void checkStatusAfterTimeout() {
        String workflowUuid = getWorkflowUuid(order, shipmentUuid, getDateMinusSec(30), clientWorkflow);

        ThreadUtil.simplyAwait(85);

        List<AssignmentChangedOuterClass.AssignmentChanged> assignments = kafka.waitDataInKafkaTopicWorkflowAssignment(workflowUuid);
        long workflowId = assignments.get(assignments.size() - 1).getWorkflowId();
        List<WorkflowChangedOuterClass.WorkflowChanged> workflows = kafka.waitDataInKafkaTopicWorkflow(workflowId);
        checkStatuses(assignments, workflows, TIMEOUT);
    }

    @CaseId(125)
    @Test(description = "Таймаут отложенного назначения", enabled = false, //TODO: посмотреть, как стабилизировать тесты, кроме увеличения времени
            groups = "dispatch-workflow-smoke",
            dependsOnMethods = "checkStatusAfterTimeout")
    public void checkChildWorkflowStatusAfterTimeout() {
        String workflowUuid = getWorkflowUuid(order, shipmentUuid, getDateMinusSec(30), clientWorkflow);

        var childRequest = getWorkflowsRequestWithDifferentParams(order, shipmentUuid, secondOrder, secondShipmentUuid, workflowUuid);
        var childResponse = clientWorkflow.createWorkflows(childRequest);
        String childWorkflowUuid = childResponse.getResultsMap().keySet().toArray()[0].toString();

        AssignmentsEntity assignmentsEntity = AssignmentsDao.INSTANCE.getAssignmentByWorkflowUuid(workflowUuid);
        acceptWorkflow(assignmentsEntity.getId().toString());

        ThreadUtil.simplyAwait(85);

        List<AssignmentChangedOuterClass.AssignmentChanged> assignments = kafka.waitDataInKafkaTopicWorkflowAssignment(childWorkflowUuid);
        long workflowId = assignments.get(assignments.size() - 1).getWorkflowId();
        List<WorkflowChangedOuterClass.WorkflowChanged> workflows = kafka.waitDataInKafkaTopicWorkflow(workflowId);
        checkStatuses(assignments, workflows, TIMEOUT);
    }

    @CaseId(126)
    @Test(description = "Отмена отложенного назначения при timeout родительского назначения", enabled = false,  //TODO: посмотреть, как стабилизировать тесты, кроме увеличения времени
            groups = "dispatch-workflow-smoke",
            dependsOnMethods = "checkChildWorkflowStatusAfterTimeout")
    public void checkChildWorkflowStatusAfterParentTimeout() {
        cancelWorkflow(clientWorkflow, shipmentUuid);
        String workflowUuid = getWorkflowUuid(order, shipmentUuid, getDateMinusSec(30), clientWorkflow);

        var childRequest = getWorkflowsRequestWithDifferentParams(order, shipmentUuid, secondOrder, secondShipmentUuid, workflowUuid);
        var childResponse = clientWorkflow.createWorkflows(childRequest);
        String childWorkflowUuid = childResponse.getResultsMap().keySet().toArray()[0].toString();

        ThreadUtil.simplyAwait(85);

        List<AssignmentChangedOuterClass.AssignmentChanged> assignments = kafka.waitDataInKafkaTopicWorkflowAssignment(childWorkflowUuid);
        long workflowId = assignments.get(assignments.size() - 1).getWorkflowId();
        List<WorkflowChangedOuterClass.WorkflowChanged> workflows = kafka.waitDataInKafkaTopicWorkflow(workflowId);
        checkStatuses(assignments, workflows, CANCELED);
    }

    @CaseId(144)
    @Test(description = "Обновление таймингов in_progress маршрутного листа с текущим сегментом arrive/delivery", enabled = false,
            groups = "dispatch-workflow-smoke")
    public void updateTimingsForWorkflowInProgress() {
        String workflowUuid = getWorkflowUuid(order, shipmentUuid, Timestamps.MAX_VALUE, clientWorkflow);
        AssignmentsEntity assignmentsEntity = AssignmentsDao.INSTANCE.getAssignmentByWorkflowUuid(workflowUuid);
        WorkflowsEntity workflowsEntity = WorkflowsDao.INSTANCE.findById(assignmentsEntity.getWorkflowId()).get();
        List<SegmentsEntity> segmentsEntities = SegmentsDao.INSTANCE.getSegmentsByWorkflowUuid(workflowUuid).stream()
                .sorted(Comparator.comparing(SegmentsEntity::getPosition)).collect(Collectors.toList());
        acceptWorkflowAndStart(assignmentsEntity.getId().toString(), METRO_WORKFLOW_END);

        ThreadUtil.simplyAwait(305);

        checkWorkflowChanges(workflowUuid, assignmentsEntity, workflowsEntity, segmentsEntities, false);

        cancelWorkflow(clientWorkflow, shipmentUuid);
    }

    @CaseId(148)
    @Test(description = "Обновление таймингов маршрутного листа в очереди", enabled = false, // Требует уточнения условий
            groups = "dispatch-workflow-smoke")
            //dependsOnMethods = "updateTimingsForWorkflowInProgress")
    public void updateTimingsForQueuedWorkflow() {
        String firstWorkflowUuid = getWorkflowUuid(order, shipmentUuid, getDateMinusSec(5), clientWorkflow);
        AssignmentsEntity firstAssignmentsEntity = AssignmentsDao.INSTANCE.getAssignmentByWorkflowUuid(firstWorkflowUuid);
        acceptWorkflowAndStart(firstAssignmentsEntity.getId().toString(), METRO_WORKFLOW_END);

        String secondWorkflowUuid = getWorkflowUuid(secondOrder, secondShipmentUuid, getDatePlusSec(2640), clientWorkflow);
        AssignmentsEntity secondAssignmentsEntity = AssignmentsDao.INSTANCE.getAssignmentByWorkflowUuid(secondWorkflowUuid);
        WorkflowsEntity secondWorkflowsEntity = WorkflowsDao.INSTANCE.findById(secondAssignmentsEntity.getWorkflowId()).get();
        List<SegmentsEntity> secondSegmentsEntities = SegmentsDao.INSTANCE.getSegmentsByWorkflowUuid(secondWorkflowUuid).stream()
                .sorted(Comparator.comparing(SegmentsEntity::getPosition)).collect(Collectors.toList());
        acceptWorkflow(secondAssignmentsEntity.getId().toString());

        ThreadUtil.simplyAwait(315);

        checkWorkflowChanges(secondWorkflowUuid, secondAssignmentsEntity, secondWorkflowsEntity, secondSegmentsEntities, true);
    }
}
