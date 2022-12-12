package ru.instamart.test.api.on_demand.orderservice;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.jdbc.dao.workflow.SegmentsDao;
import ru.instamart.jdbc.entity.workflow.SegmentsEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.instamart.api.helper.WorkflowHelper.*;
import static ru.instamart.kraken.data.StartPointsTenants.METRO_WORKFLOW_END;
import static ru.instamart.kraken.data.StartPointsTenants.METRO_WORKFLOW_START;
import static ru.instamart.kraken.util.TimeUtil.getZonedDate;
import static ru.instamart.kraken.util.TimeUtil.getZonedFutureDate;


@Epic("On Demand")
@Feature("DISPATCH")
public class FullOrderPathAllEnpointsTest extends RestBase {

    private OrderV2 orderOnDemand;
    private List<SegmentsEntity> segments;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        Allure.step("Создание смены у сборщика без сервиса смен", () -> {
            UserData user = UserManager.getShp6Shopper1();
            shopperApp.authorisation(user);
            shopperApp.createShopperShift(1, getZonedDate(), getZonedFutureDate(1L), METRO_WORKFLOW_START.getLat(), METRO_WORKFLOW_START.getLon());
        });

        Allure.step("Создание смены у универсала в СС (сервис смен)", () -> {
            UserData user = UserManager.getShp6Universal1();
            shopperApp.authorisation(user);
            //Удаляем все смены
            shiftsApi.cancelAllActiveShifts();
            shiftsApi.stopAllActiveShifts();
            //
            shiftsApi.createShift();
        });

        Allure.step("Создание заказа на витрине", () -> {
            SessionFactory.makeSession(SessionType.API_V2);
            UserData userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
            orderOnDemand = apiV2.orderOnDemand(userData, EnvironmentProperties.DEFAULT_SID);
        });

    }

    @Test(description = "Полный путь заказа (+ все эндпоинты)",
            groups = "dispatch-orderservice-smoke")
    @Skip
    public void allFlouTest() {
        final var assignmentsResponseList = getAllShopperAssignments();
        final var assignmentAccept = acceptWorkflowAndStart(assignmentsResponseList.get(0).getId().toString(), METRO_WORKFLOW_START);
        shopperApp.getShipments(assignmentAccept.getPerformerUuid());
        segments = SegmentsDao.INSTANCE.getSegmentsByWorkflowUuid(assignmentAccept.getPerformerUuid()).stream()
                .sorted(Comparator.comparing(SegmentsEntity::getPosition)).collect(Collectors.toList());
        stopSegments(segments.get(0).getWorkflowId(), segments.get(0).getId(), METRO_WORKFLOW_START);
        shopperApp.assemblyItemsWithOriginalQty();
        stopSegments(segments.get(0).getWorkflowId(), segments.get(0).getId(), METRO_WORKFLOW_END);
    }

}
