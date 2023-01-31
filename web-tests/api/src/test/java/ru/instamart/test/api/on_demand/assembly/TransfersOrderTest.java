package ru.instamart.test.api.on_demand.assembly;

import com.google.common.collect.Iterables;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.jdbc.dao.assembly.AssemblyDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.Group.ASSEMBLY_DISPATCH;
import static ru.instamart.kraken.util.TimeUtil.getFutureDateWithoutTime;

@Epic("On Demand")
@Feature("Assembly")
public class TransfersOrderTest extends RestBase {

    private final Integer sid = EnvironmentProperties.DEFAULT_SID;
    private final UserData universal = UserManager.getShp6ShopperASML1();
    private final UserData universal2 = UserManager.getShp6ShopperASML2();
    private final String storeUUID = "b42e2326-079c-4124-be62-ac7b3ee744e8";
    private UserData user;
    private OrderV2 order, order2;
    private String assembly;
    private Integer shiftId;

    @BeforeMethod(alwaysRun = true)
    public void auth() {
        SessionFactory.makeSession(SessionType.API_V2);
        user = SessionFactory
                .getSession(SessionType.API_V2)
                .getUserData();

        Allure.step("Сделать заказ в магазине на ближайший слот, адрес Казань, Чистопольская ул, 5", () ->
                order = apiV2.order(user, EnvironmentProperties.DEFAULT_SID)
        );
        admin.auth();
    }

    @AfterMethod(alwaysRun = true)
    public void after() {
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
    }

    @TmsLink("ASML-10")
    @Story("Переносы")
    @Test(groups = {ASSEMBLY_DISPATCH},
            description = "Перенос до начала сборки (на следующий день)")
    public void moveTheBuildToTheNextDay() {
        final var deliveryWindows = admin.getDeliveryWindows(sid, getFutureDateWithoutTime(1L));
        admin.changeDeliveryWindows(order, sid, deliveryWindows.get(0).getId());

        final var assemblyList = AssemblyDao.INSTANCE.getByShipmentNumber(order.getNumber());
        assertEquals(assemblyList.getState(), 2L, "assemblu state отличается ");
    }

    @TmsLink("ASML-10")
    @Story("Переносы")
    @Test(groups = {ASSEMBLY_DISPATCH},
            description = "Перенос до сборки в рамках текущего дня")
    public void moveTheBuildToTheCurrentDay() {
        final var assemblyList1 = AssemblyDao.INSTANCE.getByShipmentNumber(order.getNumber());
        final var deliveryWindows = admin.getDeliveryWindows(sid, getFutureDateWithoutTime(0L));
        admin.changeDeliveryWindows(order, sid, Iterables.getLast(deliveryWindows).getId());

        final var assemblyList2 = AssemblyDao.INSTANCE.getByShipmentNumber(order.getNumber());
        final var asserts = new SoftAssert();
        asserts.assertNotEquals(assemblyList1.getDeadlineAt(), assemblyList2.getDeadlineAt(), "DeadlineAt не изменилось");
        asserts.assertNotEquals(assemblyList1.getDispatchAt(), assemblyList2.getDispatchAt(), "DispatchAt не изменилось");
        asserts.assertNotEquals(assemblyList1.getStartAfter(), assemblyList2.getStartAfter(), "StartAfter не изменилось");
        asserts.assertAll();

    }
}
