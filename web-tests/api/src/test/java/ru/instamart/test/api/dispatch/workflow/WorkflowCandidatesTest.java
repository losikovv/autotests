package ru.instamart.test.api.dispatch.workflow;

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
import ru.instamart.jdbc.dao.candidates.CandidatesDao;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.jdbc.dao.workflow.AssignmentsDao;
import ru.instamart.jdbc.entity.candidates.CandidatesEntity;
import ru.instamart.jdbc.entity.workflow.AssignmentsEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;
import workflow.AssignmentChangedOuterClass;
import workflow.ServiceGrpc;
import workflow.WorkflowChangedOuterClass;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.WorkflowCheckpoints.checkGrpcError;
import static ru.instamart.api.checkpoint.WorkflowCheckpoints.checkStatuses;
import static ru.instamart.api.helper.WorkflowHelper.*;
import static ru.instamart.kraken.data.StartPointsTenants.METRO_WORKFLOW_END;
import static ru.instamart.kraken.util.TimeUtil.getDatePlusSec;
import static workflow.AssignmentChangedOuterClass.AssignmentChanged.Status.ACCEPTED;
import static workflow.AssignmentChangedOuterClass.AssignmentChanged.Status.CANCELED;
import static workflow.WorkflowOuterClass.CreateWorkflowsResponse.Result.ErrorKind.CANDIDATE_IS_BUSY;

public class WorkflowCandidatesTest extends RestBase {

    private ServiceGrpc.ServiceBlockingStub clientWorkflow;
    private OrderV2 order;
    private String shipmentUuid;
    private OrderV2 secondOrder;
    private String secondShipmentUuid;
    private String workflowUuid;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientWorkflow = ServiceGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_WORKFLOW));
        SessionFactory.makeSession(SessionType.API_V2);
        order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        shipmentUuid = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber()).getUuid();
        secondOrder = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        secondShipmentUuid = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(secondOrder.getShipments().get(0).getNumber()).getUuid();
        shopperApp.authorisation(UserManager.getShp6Shopper3());
    }

    @CaseId(93)
    @Test(description = "Блокирование кандидата в статусе offered",
            groups = "dispatch-workflow-smoke")
    public void checkUnavailableCandidate() {
        workflowUuid = getWorkflowUuid(order, shipmentUuid, getDatePlusSec(300000), clientWorkflow);
        CandidatesEntity candidate = CandidatesDao.INSTANCE.getCandidateByUuid(UserManager.getShp6Shopper3().getUuid());
        compareTwoObjects(candidate.getActive(), false);
    }

    @CaseId(94)
    @Test(description = "Разблокирование кандидата в статусе accepted",
            groups = "dispatch-workflow-smoke",
            dependsOnMethods = "checkUnavailableCandidate")
    public void checkAvailableCandidateWithAcceptedWorkflow() {
        final Response response = AssignmentsRequest.Accept.PATCH(getShopperAssignments().get(0).getId());
        checkStatusCode200(response);
        CandidatesEntity candidate = CandidatesDao.INSTANCE.getCandidateByUuid(UserManager.getShp6Shopper3().getUuid());
        compareTwoObjects(candidate.getActive(), true);
    }

    @CaseId(94)
    @Test(description = "Разблокирование кандидата в статусе canceled",
            groups = "dispatch-workflow-smoke",
            dependsOnMethods = "checkAvailableCandidateWithAcceptedWorkflow")
    public void checkAvailableCandidateWithCanceledWorkflow() {
        cancelWorkflow(clientWorkflow, workflowUuid);
        CandidatesEntity candidate = CandidatesDao.INSTANCE.getCandidateByUuid(UserManager.getShp6Shopper3().getUuid());
        compareTwoObjects(candidate.getActive(), true);
    }

    @CaseId(92)
    @Test(description = "Создание маршрутного листа для заблокированного кандидата",
            groups = "dispatch-workflow-smoke",
            dependsOnMethods = "checkAvailableCandidateWithCanceledWorkflow")
    public void createWorkflowForBusyCandidate() {
        String candidateUuid = CandidatesDao.INSTANCE.getCandidateUuidByStatus(false);

        var request = getWorkflowsRequestWithDifferentStores(secondOrder, secondShipmentUuid, candidateUuid);
        var response = clientWorkflow.createWorkflows(request);

        checkGrpcError(response, "Candidate is busy", CANDIDATE_IS_BUSY.toString());
    }

    @CaseId(98)
    @Test(description = "Отмена заказа для назначения в статусе queued",
            groups = "dispatch-workflow-smoke",
            dependsOnMethods = "createWorkflowForBusyCandidate")
    public void cancelOrderWithQueuedWorkflow() {
        cancelWorkflow(clientWorkflow, shipmentUuid);
        String firstWorkflowUuid = getWorkflowUuid(order, shipmentUuid, getDatePlusSec(500000), clientWorkflow);
        AssignmentsEntity firstAssignmentsEntity = AssignmentsDao.INSTANCE.getAssignmentByWorkflowUuid(firstWorkflowUuid);
        acceptWorkflowAndStart(firstAssignmentsEntity.getId().toString(), METRO_WORKFLOW_END);

        String secondWorkflowUuid = getWorkflowUuid(secondOrder, secondShipmentUuid, getDatePlusSec(564000), clientWorkflow);
        AssignmentsEntity secondAssignmentsEntity = AssignmentsDao.INSTANCE.getAssignmentByWorkflowUuid(secondWorkflowUuid);
        acceptWorkflow(secondAssignmentsEntity.getId().toString());

        apiV2.cancelOrder(secondOrder.getNumber());

        List<AssignmentChangedOuterClass.AssignmentChanged> assignments = kafka.waitDataInKafkaTopicWorkflowAssignment(secondWorkflowUuid);
        long workflowId = assignments.get(assignments.size() - 1).getWorkflowId();
        List<WorkflowChangedOuterClass.WorkflowChanged> workflows = kafka.waitDataInKafkaTopicWorkflow(workflowId);
        checkStatuses(assignments, workflows, ACCEPTED);

        cancelWorkflow(clientWorkflow, shipmentUuid);
    }

    @CaseId(97)
    @Test(description = "Отмена заказа для назначения в статусе seen",
            groups = "dispatch-workflow-smoke",
            dependsOnMethods = "cancelOrderWithQueuedWorkflow")
    public void cancelOrderWithSeenWorkflow() {
        String workflowUuid = getWorkflowUuid(order, shipmentUuid, getDatePlusSec(700000), clientWorkflow);
        AssignmentsEntity assignmentsEntity = AssignmentsDao.INSTANCE.getAssignmentByWorkflowUuid(workflowUuid);
        final Response response = AssignmentsRequest.Seen.PATCH(assignmentsEntity.getId().toString());
        checkStatusCode(response, 204);

        apiV2.cancelOrder(order.getNumber());

        List<AssignmentChangedOuterClass.AssignmentChanged> assignments = kafka.waitDataInKafkaTopicWorkflowAssignment(workflowUuid);
        long workflowId = assignments.get(assignments.size() - 1).getWorkflowId();
        List<WorkflowChangedOuterClass.WorkflowChanged> workflows = kafka.waitDataInKafkaTopicWorkflow(workflowId);
        checkStatuses(assignments, workflows, CANCELED);
    }


    @AfterClass(alwaysRun = true)
    public void clearData() {
        cancelWorkflow(clientWorkflow, secondShipmentUuid);
        cancelWorkflow(clientWorkflow, shipmentUuid);
    }
}
