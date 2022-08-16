package ru.instamart.test.api.on_demand.shifts;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.shifts.ShiftsRequest;
import ru.instamart.api.response.ErrorTypeResponse;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;
import workflow.ServiceGrpc;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;
import static ru.instamart.api.helper.WorkflowHelper.getWorkflowUuid;
import static ru.instamart.kraken.data.StartPointsTenants.METRO_9;
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
        UserData user = UserManager.getShp6Shopper1();
        shopperApp.authorisation(user);

        //Удаляем все смены
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
        //
        planningPeriodId = shiftsApi.startOfShift(METRO_9);
        SessionFactory.makeSession(SessionType.API_V2);
        UserData userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        order = apiV2.order(userData, EnvironmentProperties.DEFAULT_SID);
        shipmentUuid = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber()).getUuid();
        String workflowUuid = getWorkflowUuid(order, shipmentUuid, getDateMinusSec(30), clientWorkflow);
    }

    @AfterClass(alwaysRun = true)
    public void after() {
        //Удаляем все смены
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
    }

    @CaseId(168)
    @Story("Завершение смены")
    @Test(groups = {"api-shifts"},
            description = "Получение ошибки при завершении активной смены с активным маршрутным листом")
    public void startShift200() {
        final Response response = ShiftsRequest.Start.PATCH(planningPeriodId, METRO_9.getLat(), METRO_9.getLon());
        checkStatusCode422(response);
        ErrorTypeResponse errorTypeResponse = response.as(ErrorTypeResponse.class);
        Allure.step("Проверка ошибки при начале смены", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(errorTypeResponse.getStatus(), 422, "Error status message not valid");
            softAssert.assertEquals(errorTypeResponse.getTitle(), "Shift not ready to start", "Error title message not valid");
            softAssert.assertEquals(errorTypeResponse.getType(), "shift-no-ready-to-start", "Error type message not valid");
            softAssert.assertAll();
        });
    }
}
