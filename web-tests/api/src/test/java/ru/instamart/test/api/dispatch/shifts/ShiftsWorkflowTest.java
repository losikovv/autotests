package ru.instamart.test.api.dispatch.shifts;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.shifts.ShiftsRequest;
import ru.instamart.api.response.shifts.ShiftResponse;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.StartPointsTenants;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;
import workflow.ServiceGrpc;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.WorkflowHelper.getWorkflowUuid;
import static ru.instamart.kraken.data.StartPointsTenants.METRO_3;
import static ru.instamart.kraken.util.TimeUtil.getDateMinusSec;

@Epic("Shifts")
@Feature("Endpoints")
public class ShiftsWorkflowTest extends RestBase {

    private int planningPeriodId;
    private OrderV2 order;
    private String shipmentUuid;
    private ServiceGrpc.ServiceBlockingStub clientWorkflow;

    @BeforeClass(alwaysRun = true,
            description = "Оформляем смену и маршрутный лист")
    public void preconditions() {
        clientWorkflow = ServiceGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_WORKFLOW));
        UserData user = UserManager.getShp6Shopper2();
        shopperApp.authorisation(user);
        planningPeriodId = shiftsApi.startOfShift(StartPointsTenants.METRO_3);
        order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        shipmentUuid = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber()).getUuid();
        String workflowUuid = getWorkflowUuid(order, shipmentUuid, getDateMinusSec(30), clientWorkflow);
    }

    @CaseId(168)
    @Story("Завершение смены")
    @Test(groups = {"api-shifts"},
            description = "Получение ошибки при завершении активной смены с активным маршрутным листом")
    public void startShift200() {
        final Response response = ShiftsRequest.Start.PATCH(planningPeriodId, METRO_3.getLat(), METRO_3.getLon());
        checkStatusCode200(response);
        ShiftResponse shiftResponse = response.as(ShiftResponse.class);
        Allure.step("Проверка статуса смены", ()-> {
            assertEquals(shiftResponse.getState(), "in_progress", "State смены не в статусе \"in_progress\"");
            assertEquals(shiftResponse.getId(), planningPeriodId, "Id отличается от planningPeriodId");
        });
    }
}
