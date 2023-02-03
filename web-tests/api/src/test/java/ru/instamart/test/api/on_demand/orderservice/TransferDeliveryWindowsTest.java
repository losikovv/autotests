package ru.instamart.test.api.on_demand.orderservice;

import io.qameta.allure.*;
import order.OrderChanged;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.helper.AdminHelper;
import ru.instamart.api.helper.ApiV2Helper;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v1.admin.OrderCancellationsAdminV1Request;
import ru.instamart.jdbc.dao.orders_service.publicScheme.JobsDao;
import ru.instamart.jdbc.dao.orders_service.publicScheme.OrdersDao;
import ru.instamart.jdbc.dao.workflow.WorkflowsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.ThreadUtil;
import workflow.WorkflowChangedOuterClass;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.WorkflowHelper.acceptWorkflowAndStart;
import static ru.instamart.api.helper.WorkflowHelper.getAllShopperAssignments;
import static ru.instamart.kraken.data.StartPointsTenants.*;

@Epic("On Demand")
@Feature("DISPATCH")
public class TransferDeliveryWindowsTest extends RestBase {
    private OrderV2 order;
    private final AdminHelper adminApi = new AdminHelper();
    private final ApiV2Helper apiV2 = new ApiV2Helper();

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
            order = apiV2.order(userData, EnvironmentProperties.DEFAULT_SID);
        });
    }

    @TmsLink("288")
    @Story("Проверка переноса слота заказа")
    @Test(description = "Проверка переноса слота до начала диспетрчерезации",
            groups = "dispatch-orderservice-regress")
    public void DeliveryWindowsWithoutML() {
        admin.authApi();
        final var orderInfoFirst = OrdersDao.INSTANCE.findByOrderNumber(order.getShipments().get(0).getNumber());
        final var deliveryWindows  = apiV2.getAvailableDeliveryWindowSecond(order.getShipments().get(0).getNumber(),0);
        adminApi.changeDeliveryWindows(order,EnvironmentProperties.DEFAULT_SID,deliveryWindows.get(1).getDeliveryWindow().getId().longValue());

        ThreadUtil.simplyAwait(2);
        final var orderInfoNew  = OrdersDao.INSTANCE.findByOrderNumber(order.getShipments().get(0).getNumber());
        final var jobsNew = JobsDao.INSTANCE.findByShipmentUuid(orderInfoNew.getShipmentUuid());

        final var response = OrderCancellationsAdminV1Request.POST(order.getNumber(), 1, "test");
        checkStatusCode200(response);

        Allure.step("Проверка джоб на правильный статус", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderInfoNew.getSendToDispatchCount().intValue(),0, "Счетчик количества попыток деспетчирезации не сбросился");
            softAssert.assertEquals(jobsNew.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У сборки перенесеного заказ статус не NEW");
            softAssert.assertEquals(jobsNew.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У доставки перенесеного заказ статус не NEW");
            softAssert.assertNotEquals(orderInfoNew.getTimeToThrowDttm(),orderInfoFirst.getTimeToThrowDttm(),"Время TTT одинаковое");
            softAssert.assertAll();
        });
    }

    @TmsLink("290")
    @Story("Проверка переноса слота заказа")
    @Test(description = "Проверка переноса слота доставки с офеером без его принятием",
            groups = "dispatch-orderservice-regress")
    public void DeliveryWindowsWithML() {
        final var assignmentsResponseList = getAllShopperAssignments();
        final var workflow = acceptWorkflowAndStart(assignmentsResponseList.get(0).getId().toString(), METRO_9);

        admin.authApi();
        final var orderInfoFirst = OrdersDao.INSTANCE.findByOrderNumber(order.getShipments().get(0).getNumber());
        final var deliveryWindows  = apiV2.getAvailableDeliveryWindowSecond(order.getShipments().get(0).getNumber(),1);
        adminApi.changeDeliveryWindows(order,EnvironmentProperties.DEFAULT_SID,deliveryWindows.get(1).getDeliveryWindow().getId().longValue());

        ThreadUtil.simplyAwait(2);
        final var orderInfoNew  = OrdersDao.INSTANCE.findByOrderNumber(order.getShipments().get(0).getNumber());
        final var jobsNew = JobsDao.INSTANCE.findByShipmentUuid(orderInfoNew.getShipmentUuid());
        final var workflowStatus = WorkflowsDao.INSTANCE.findById(workflow.getId());

        final var response = OrderCancellationsAdminV1Request.POST(order.getNumber(), 1, "test");
        checkStatusCode200(response);

        Allure.step("Проверка джоб на правильный статус", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderInfoNew.getSendToDispatchCount().intValue(),0, "Счетчик количества попыток деспетчирезации не сбросился");
            softAssert.assertEquals(jobsNew.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У сборки перенесеного заказ статус не NEW");
            softAssert.assertEquals(jobsNew.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У доставки перенесеного заказ статус не NEW");
            softAssert.assertNotEquals(orderInfoNew.getTimeToThrowDttm(),orderInfoFirst.getTimeToThrowDttm(),"Время TTT одинаковое");
            softAssert.assertEquals(workflowStatus.get().getStatus().intValue(), WorkflowChangedOuterClass.WorkflowChanged.Status.CANCELED.getNumber(),"Workflow не отменен");
            softAssert.assertAll();
        });
    }

    @TmsLink("289")
    @Story("Проверка переноса слота заказа")
    @Test(description = "Проверка переноса слота доставки с офеером без его принятия",
            groups = "dispatch-orderservice-regress")
    public void DeliveryWindowsOffered() {
        getAllShopperAssignments();

        admin.authApi();
        final var orderInfoFirst = OrdersDao.INSTANCE.findByOrderNumber(order.getShipments().get(0).getNumber());
        final var deliveryWindows  = apiV2.getAvailableDeliveryWindowSecond(order.getShipments().get(0).getNumber(),1);
        adminApi.changeDeliveryWindows(order,EnvironmentProperties.DEFAULT_SID,deliveryWindows.get(1).getDeliveryWindow().getId().longValue());

        Allure.step("Ожидание протухание оффера", () -> ThreadUtil.simplyAwait(60));

        final var orderInfoNew  = OrdersDao.INSTANCE.findByOrderNumber(order.getShipments().get(0).getNumber());
        final var jobsNew = JobsDao.INSTANCE.findByShipmentUuid(orderInfoNew.getShipmentUuid());

        final var response = OrderCancellationsAdminV1Request.POST(order.getNumber(), 1, "test");
        checkStatusCode200(response);

        Allure.step("Проверка джоб на правильный статус", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(orderInfoNew.getSendToDispatchCount().intValue(),0, "Счетчик количества попыток деспетчирезации не сбросился");
            softAssert.assertEquals(jobsNew.get(0).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У сборки перенесеного заказ статус не NEW");
            softAssert.assertEquals(jobsNew.get(1).status, OrderChanged.EventOrderChanged.Job.JobStatus.NEW.name(), "У доставки перенесеного заказ статус не NEW");
            softAssert.assertNotEquals(orderInfoNew.getTimeToThrowDttm(),orderInfoFirst.getTimeToThrowDttm(),"Время TTT одинаковое");
            softAssert.assertAll();
        });
    }
}
