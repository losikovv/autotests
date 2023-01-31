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
import ru.instamart.api.model.shopper.app.ShipmentSHP;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.jdbc.dao.assembly.PickersDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

import static org.testng.Assert.*;
import static ru.instamart.api.Group.ASSEMBLY_DISPATCH;
import static ru.instamart.api.helper.K8sHelper.changeToCollecting;
import static ru.instamart.kraken.data.StartPointsTenants.ASSEMBLY_START;
import static ru.instamart.kraken.util.ThreadUtil.simplyAwait;

@Epic("On Demand")
@Feature("Assembly")
public class AssemblyTest extends RestBase {

    private final UserData universal = UserManager.getShp6ShopperASML1();
    private final UserData universal2 = UserManager.getShp6ShopperASML2();
    private final String storeUUID = "b42e2326-079c-4124-be62-ac7b3ee744e8";
    private UserData user;
    private OrderV2 order, order2;
    private String assembly, assembly2;
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
        assertEquals(shiftId, pickersEntity.getShiftId(), "ID shift не совпадает");

        SessionFactory.makeSession(SessionType.API_V2);
        user = SessionFactory
                .getSession(SessionType.API_V2)
                .getUserData();

        Allure.step("Сделать заказ в магазине на ближайший слот, адрес Казань, Чистопольская ул, 5", () ->
                order = apiV2.order(user, EnvironmentProperties.DEFAULT_SID)
        );

        Allure.step("Сделать заказ в магазине на ближайший слот, адрес Казань, Чистопольская ул, 5", () ->
                order2 = apiV2.order(user, EnvironmentProperties.DEFAULT_SID)
        );
    }

    @AfterMethod(alwaysRun = true)
    public void after() {
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
    }

    @TmsLink("ASML-2")
    @Story("Паузы")
    @Test(groups = {ASSEMBLY_DISPATCH},
            description = "Пауза в статусе collecting")
    public void pauseInStatusCollecting() {
        shopperApp.deleteCurrentAssembly();
        Allure.step("Принять оффер", () -> {
            assembly = shopperApp.startAssemblyOnDemand();
            shopperApp.assemblyItems();
        });
        simplyAwait(3);
        Allure.step("Нажать приостановить сборку в приложении", () -> {
            shopperApp.pauseAssembly(assembly);
            final var pickersEntity = PickersDao.INSTANCE.getByPhone(universal.getPhone());
            assertNotNull(pickersEntity, "Данных в БД нет");
            assertTrue(pickersEntity.getHasPausedShipments(), "has_paused_shipments не равен true");
        });


        ShipmentSHP.Data shipment2 = shopperApp.getShipment(order2.getShipments().get(0).getNumber());
        Allure.step("Принять заказ2 и собирать заказ 2", () -> {
            shopperApp.assemblyItemsWithOriginalQty();
            final var pickersEntity = PickersDao.INSTANCE.getByPhone(universal.getPhone());
            assertNotNull(pickersEntity, "Данных в БД нет");
            assertTrue(pickersEntity.getHasPausedShipments(), "has_paused_shipments не равен true");
        });

        Allure.step("Собирать заказ 1", () -> {
            shopperApp.stocksAssembly(assembly);
            shopperApp.assemblyItemsWithOriginalQty();
            final var pickersEntity = PickersDao.INSTANCE.getByPhone(universal.getPhone());
            assertNotNull(pickersEntity, "Данных в БД нет");
            assertFalse(pickersEntity.getHasPausedShipments(), "has_paused_shipments не равен true");
        });
    }

    @TmsLink("ASML-4")
    @Story("Переназначение")
    @Test(groups = {ASSEMBLY_DISPATCH},
            description = "Happy Path переназначения")
    public void happyPathReassignment() {
        Allure.step("Принять оффер", () -> {
            assembly = shopperApp.startAssemblyOnDemand();
            shopperApp.assemblyItems();
        });
        simplyAwait(3);
        Allure.step("Поставить заказ на паузу", () -> {
            shopperApp.pauseAssembly(assembly);
            final var pickersEntity = PickersDao.INSTANCE.getByPhone(universal.getPhone());
            assertNotNull(pickersEntity, "Данных в БД нет");
            assertTrue(pickersEntity.getHasPausedShipments(), "has_paused_shipments не равен true");
        });

        Allure.step("Перевести заказ в стф статус collecting (только для стейджей)", () ->
                changeToCollecting(order.getShipments().get(0).getNumber())
        );

        Allure.step("Начать смену сборщиком 2", () -> {
            SessionFactory.clearSession(SessionType.SHOPPER_APP);
            shopperApp.authorisation(universal2);
            //Удаляем все смены
            shiftsApi.cancelAllActiveShifts();
            shiftsApi.stopAllActiveShifts();
            shiftsApi.startOfShift(ASSEMBLY_START, RoleSHP.SHOPPER);

            final var pickersEntity = PickersDao.INSTANCE.getByPhone(universal.getPhone());
            assertNotNull(pickersEntity, "Данных в БД нет");
            assertTrue(pickersEntity.getActive(), "active не равен true");
        });


        Allure.step("Передать заказ", () -> {
            admin.auth();
            final var freePickers = admin.getFreePickers(storeUUID);
            admin.reassignOffer(freePickers.get(0).getId(), storeUUID);
        });

        Allure.step("Принять оффер сборщиком", () -> {
            assembly = shopperApp.startAssemblyOnDemand();
        });

        Allure.step("Собрать или отменить заказы", () -> {
            admin.authApi();
            admin.canselOrder(order);
        });
    }


    @TmsLink("ASML-14")
    @Story("Переназначение")
    @Test(groups = {ASSEMBLY_DISPATCH},
            description = "Переназначение на сборщика со сборкой на паузе")
    public void reassigningToBuilderWithAPausedBuild() {
        Allure.step("Перевести заказ в стф статус collecting (только для стейджей)", () ->
                changeToCollecting(order.getShipments().get(0).getNumber())
        );

        Allure.step("Начать смену сборщиком 2", () -> {
            SessionFactory.clearSession(SessionType.SHOPPER_APP);
            shopperApp.authorisation(universal2);
            //Удаляем все смены
            shiftsApi.cancelAllActiveShifts();
            shiftsApi.stopAllActiveShifts();
            shiftId = shiftsApi.startOfShift(ASSEMBLY_START, RoleSHP.SHOPPER);

            final var pickersEntity = PickersDao.INSTANCE.getByPhone(universal.getPhone());
            assertNotNull(pickersEntity, "Данных в БД нет");
            assertTrue(pickersEntity.getActive(), "active не равен true");
        });

        Allure.step("Принять оффер", () -> {
            assembly2 = shopperApp.startAssemblyOnDemand();
            shopperApp.assemblyItems();
        });

        Allure.step("Сборка на паузе", () ->
                shopperApp.pauseAssembly(assembly2)
        );

        Allure.step("Передать заказ", () -> {
            admin.auth();
            final var freePickers = admin.getFreePickers(storeUUID);
            admin.reassignOffer(freePickers.get(0).getId(), storeUUID);
        });

        Allure.step("Принять оффер сборщиком", () -> {
            assembly = shopperApp.startAssemblyOnDemand();
        });

        Allure.step("Собрать или отменить заказы", () -> {
            admin.authApi();
            admin.canselOrder(order);
        });
    }

    @TmsLink("ASML-15")
    @Story("Переназначение")
    @Test(groups = {ASSEMBLY_DISPATCH},
            description = "Переназначение со сборщика, у которого два заказа, один на паузе, второй нет (снять тот, что НЕ на паузе)")
    public void test14() {
        Allure.step("Принять оффер", () -> {
            assembly = shopperApp.startAssemblyOnDemand();
            shopperApp.assemblyItems();
        });
        simplyAwait(3);

        Allure.step("Поставить заказ на паузу", () -> {
            shopperApp.pauseAssembly(assembly);
            final var pickersEntity = PickersDao.INSTANCE.getByPhone(universal.getPhone());
            assertNotNull(pickersEntity, "Данных в БД нет");
            assertTrue(pickersEntity.getHasPausedShipments(), "has_paused_shipments не равен true");
        });


        Allure.step("Начать смену сборщиком 2", () -> {
            SessionFactory.clearSession(SessionType.SHOPPER_APP);
            shopperApp.authorisation(universal2);
            //Удаляем все смены
            shiftsApi.cancelAllActiveShifts();
            shiftsApi.stopAllActiveShifts();
            shiftsApi.startOfShift(ASSEMBLY_START, RoleSHP.SHOPPER);

            final var pickersEntity = PickersDao.INSTANCE.getByPhone(universal.getPhone());
            assertNotNull(pickersEntity, "Данных в БД нет");
            assertTrue(pickersEntity.getActive(), "active не равен true");
        });

        Allure.step("Принять оффер", () ->
                assembly2 = shopperApp.startAssemblyOnDemand()
        );

        Allure.step("Перевести заказ в стф статус collecting (только для стейджей)", () ->
                changeToCollecting(order2.getShipments().get(0).getNumber())
        );

        Allure.step("Передать заказ", () -> {
            admin.auth();
            final var freePickers = admin.getFreePickers(storeUUID);
            admin.reassignOffer(freePickers.get(0).getId(), storeUUID);
        });

        Allure.step("Принять оффер сборщиком", () -> {
            assembly = shopperApp.startAssemblyOnDemand();
        });

        Allure.step("Собрать или отменить заказы", () -> {
            admin.authApi();
            admin.canselOrder(order);
        });
    }
}
