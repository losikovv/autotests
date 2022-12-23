package ru.instamart.test.api.shopper.scango;

import io.qameta.allure.Allure;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.shopper.admin.*;
import ru.instamart.api.model.shopper.app.ShipmentSHP;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.shopper.admin.ShopperAdminRequest;
import ru.instamart.jdbc.dao.shopper.AssembliesDao;
import ru.instamart.jdbc.dao.stf.StoresDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.ApiV3Helper.checkFlipperOff;

public class CreatingSoftCheckTest extends RestBase {

    private final String storeUUID = "adaa359e-6c53-462a-928d-307317e399b1";

    private OrderV2 order;
    private ShipmentSHP.Data shipment;

    @BeforeClass(alwaysRun = true)
    public void precondition() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdmin());
        final var scangoLenta = ScangoRetailerConfigSHP.builder()
                .scangoRetailerConfig(ScangoRetailerConfigV1.builder()
                        .id(15)
                        .engine("lenta")
                        .endpoint("https://")
                        .available(true)
                        .webhooksAuthToken("")
                        .customSetting(CustomSettingsItemV1.builder()
                                .key("client_card_number_b2b")
                                .value("900000771787")
                                .build())
                        .customSetting(CustomSettingsItemV1.builder()
                                .key("client_card_number_b2c")
                                .value("900000771787")
                                .build())
                        .customSetting(CustomSettingsItemV1.builder()
                                .key("discount")
                                .value("10")
                                .build())
                        .customSetting(CustomSettingsItemV1.builder()
                                .key("guid_prefix")
                                .value("4631")
                                .build())
                        .customSetting(CustomSettingsItemV1.builder()
                                .key("login")
                                .value("PI_SBER")
                                .build())
                        .customSetting(CustomSettingsItemV1.builder()
                                .key("send_markings")
                                .value("false")
                                .build())
                        .customSetting(CustomSettingsItemV1.builder()
                                .key("password")
                                .value("X5zYq#s2dD")
                                .build())
                        .build())
                .build();

        final var responseRetailer = ShopperAdminRequest.Scango.Retailer.PUT(8, scangoLenta);
        checkStatusCode200(responseRetailer);

        final var storeConfig = ScangoStoreConfigSHP.builder()
                .scangoStoreConfig(ScangoStoreConfigV1.builder()
                        .id(86)
                        .enabled(true)
                        .externalStoreId("12")
                        .alcoholEnabled(false)
                        .build())
                .build();
        final var responseStore = ShopperAdminRequest.Scango.Store.PUT(storeConfig, storeUUID);
        checkStatusCode200(responseStore);
        Allure.step("Конвейерная сборка выключена", () ->
                StoresDao.INSTANCE.updateHasConveyorByUUID(storeUUID, 0));
        Allure.step("Выкл или не существует фф", () -> {
            checkFlipperOff("scango_channels_white_list");
            checkFlipperOff("scango_skip_#b2c");
            checkFlipperOff("scango_skip_#b2b6");
            checkFlipperOff("scango_skip_#b2b2b");
        });

        SessionFactory.makeSession(SessionType.API_V2);
        order = apiV2.order(
                SessionFactory.getSession(SessionType.API_V2).getUserData(),
                EnvironmentProperties.DEFAULT_SID,
                1,
                ""
        );
        //Авторизация
        //Удаляем текущую сборку
        //Берем в сборку заказ
        shipment = shopperApp.simpleCollectPacker(order.getShipments().get(0).getNumber());

    }

    @Test(groups = "api-sg", description = "Создание софтчека с штучным товаром")
    public void INAPI1455() {
        final var byShipmentId = AssembliesDao.INSTANCE.getByShipmentId(Integer.valueOf(shipment.getId()));
        final var response = ShopperAdminRequest.Scango.SoftChecks.POST(byShipmentId.getId());
        checkStatusCode200(response);
    }
}
