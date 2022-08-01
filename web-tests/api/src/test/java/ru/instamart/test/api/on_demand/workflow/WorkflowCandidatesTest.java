package ru.instamart.test.api.on_demand.workflow;

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
import ru.instamart.jdbc.entity.candidates.CandidatesEntity;
import ru.instamart.k8s.K8sPortForward;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;
import workflow.ServiceGrpc;

import java.util.Objects;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.WorkflowCheckpoints.checkGrpcError;
import static ru.instamart.api.helper.WorkflowHelper.*;
import static ru.instamart.kraken.util.TimeUtil.getDatePlusSec;
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


    @AfterClass(alwaysRun = true)
    public void clearData() {
        if (Objects.nonNull(secondShipmentUuid)) {
            cancelWorkflow(clientWorkflow, secondShipmentUuid);
            cancelWorkflow(clientWorkflow, shipmentUuid);
        }
    }
}
