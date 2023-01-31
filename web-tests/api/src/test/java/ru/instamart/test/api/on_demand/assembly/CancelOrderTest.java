package ru.instamart.test.api.on_demand.assembly;

import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.shopper.RoleSHP;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.helper.K8sHelper;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.jdbc.dao.assembly.AssemblyDao;
import ru.instamart.jdbc.dao.assembly.PickersDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

import static org.testng.Assert.*;
import static ru.instamart.api.Group.ASSEMBLY_DISPATCH;
import static ru.instamart.kraken.data.StartPointsTenants.ASSEMBLY_START;
@Epic("On Demand")
@Feature("Assembly")
public class CancelOrderTest extends RestBase {

    private final UserData universal = UserManager.getShp6ShopperASML1();
    private final UserData universal2 = UserManager.getShp6ShopperASML2();
    private final String storeUUID = "b42e2326-079c-4124-be62-ac7b3ee744e8";
    private UserData user;
    private OrderV2 order, order2;
    private String assembly;
    private Integer shiftId;

    @BeforeMethod(alwaysRun = true)
    public void auth() {
        shopperApp.authorisation(universal);

        final var userInfo = shiftsApi.getShopperInfo();
        K8sHelper.shiftParamsCreate(userInfo.getData().getId());
        //Удаляем все смены
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
        shiftId = shiftsApi.startOfShift(ASSEMBLY_START, RoleSHP.SHOPPER);

        final var pickersEntity = PickersDao.INSTANCE.getByPhone(universal.getPhone());
        assertNotNull(pickersEntity, "Данных в БД нет");
        //assertEquals(shiftId, pickersEntity.getShiftId(), "ID shift не совпадает");

        SessionFactory.makeSession(SessionType.API_V2);
        user = SessionFactory
                .getSession(SessionType.API_V2)
                .getUserData();

        Allure.step("Сделать заказ в магазине на ближайший слот, адрес Казань, Чистопольская ул, 5", () ->
                order = apiV2.order(user, EnvironmentProperties.DEFAULT_SID)
        );
    }

    @AfterMethod(alwaysRun = true)
    public void after() {
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
    }

    @TmsLink("ASML-5")
    @Story("Отмены")
    @Test(groups = {ASSEMBLY_DISPATCH},
            description = "Отмена до начала сборки")
    public void canselOrderInStatusComplete() {
        Allure.step("Отмена заказы", () -> {
            admin.auth();
            admin.canselOrder(order);
        });

        final var assemblyList2 = AssemblyDao.INSTANCE.getByShipmentNumber(order.getNumber());
        assertEquals(assemblyList2.getState(), 2L, "assemblu state отличается ");
    }

    @TmsLink("ASML-6")
    @Story("Отмены")
    @Test(groups = {ASSEMBLY_DISPATCH},
            description = "Отмена после начала сборки")
    public void canselOrderInStatusCollecting() {
        Allure.step("Принять оффер", () ->
                shopperApp.startAssemblyOnDemand()
        );

        final var assemblyList = AssemblyDao.INSTANCE.getByShipmentNumber(order.getNumber());
        assertEquals(assemblyList.getState(), 4L, "assemblu state отличается ");

        Allure.step("Отмена заказы", () -> {
            admin.auth();
            admin.canselOrder(order);
        });

        final var assemblyList2 = AssemblyDao.INSTANCE.getByShipmentNumber(order.getNumber());
        assertEquals(assemblyList2.getState(), 4L, "assemblu state отличается ");
    }

    @TmsLink("ASML-7")
    @Story("Отмены")
    @Test(groups = {ASSEMBLY_DISPATCH},
            description = "Отмена заказа в статусе paused")
    public void canselOrderInStatusPaused() {
        Allure.step("Принять оффер", () -> {
                    final var assembly = shopperApp.startAssemblyOnDemand();
                    shopperApp.pauseAssembly(assembly);
                }
        );

        final var assemblyList = AssemblyDao.INSTANCE.getByShipmentNumber(order.getNumber());
        assertEquals(assemblyList.getState(), 4L, "assemblu state отличается ");

        Allure.step("Отмена заказы", () -> {
            admin.auth();
            admin.canselOrder(order);
        });

        final var assemblyList2 = AssemblyDao.INSTANCE.getByShipmentNumber(order.getNumber());
        assertEquals(assemblyList2.getState(), 4L, "assemblu state отличается ");
    }


    @TmsLink("ASML-8")
    @Story("Отмены")
    @Test(groups = {ASSEMBLY_DISPATCH},
            description = "Отмена двух заказов в статусе paused")
    public void canselTwoOrderInStatusPaused() {

        Allure.step("Принять оффер", () -> {
                    final var assembly = shopperApp.startAssemblyOnDemand();
                    shopperApp.pauseAssembly(assembly);
                }
        );

        SessionFactory.makeSession(SessionType.API_V2);
        user = SessionFactory
                .getSession(SessionType.API_V2)
                .getUserData();

        Allure.step("Сделать заказ 2 в магазине на ближайший слот, адрес Казань, Чистопольская ул, 5", () ->
                order2 = apiV2.order(user, EnvironmentProperties.DEFAULT_SID)
        );

        Allure.step("Принять оффер", () -> {
                    final var assembly2 = shopperApp.startAssemblyOnDemand();
                    shopperApp.pauseAssembly(assembly2);
                }
        );

        final var assemblyList = AssemblyDao.INSTANCE.getByShipmentNumber(order.getNumber());
        assertEquals(assemblyList.getState(), 4L, "assemblu state отличается ");

        Allure.step("Отмена заказы", () -> {
            admin.auth();
            admin.canselOrder(order);
            final var pickersEntity = PickersDao.INSTANCE.getByPhone(universal.getPhone());
            assertFalse(pickersEntity.getHasPausedShipments(), "has_paused_shipments is true");
            admin.canselOrder(order2);
            assertFalse(pickersEntity.getHasPausedShipments(), "has_paused_shipments is true");
        });
    }
}
