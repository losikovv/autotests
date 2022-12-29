package ru.instamart.test.reforged.admin.partners;

import io.qameta.allure.*;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.helper.ApiV2Helper;
import ru.instamart.api.helper.ShiftsApiHelper;
import ru.instamart.api.helper.ShopperAppApiHelper;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.StartPointsTenants;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;

import java.util.Objects;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkIsDeliveryToday;
import static ru.instamart.reforged.Group.*;
import static ru.instamart.reforged.admin.AdminRout.login;
import static ru.instamart.reforged.admin.AdminRout.partnersMap;

@Epic("Админка STF")
@Feature("Карта партнеров")
@Story("Страница 'Список партнеров' admin/spa/partners_map")
public final class AdminPartnersMapTests {

    private final ApiV2Helper helper = new ApiV2Helper();
    private final ShopperAppApiHelper shopperApp = new ShopperAppApiHelper();
    private final ShiftsApiHelper shiftsApiHelper = new ShiftsApiHelper();

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        shopperApp.authorisation(UserManager.getShpUniversalUi());

        shiftsApiHelper.cancelAllActiveShifts();
        shiftsApiHelper.stopAllActiveShifts();

        shiftsApiHelper.startOfShift(StartPointsTenants.METRO_9);
    }

    @AfterClass(alwaysRun = true)
    public void after() {
        shiftsApiHelper.cancelAllActiveShifts();
        shiftsApiHelper.stopAllActiveShifts();
    }

    @TmsLink("2167")
    @Test(description = "Поиск партнера по ФИО", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void partnerSearchByName() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();

        partnersMap().addFilter("kraken_ui");
        partnersMap().clickOnApply();
        partnersMap().checkPartnerBalloon();
        partnersMap().checkNameInBalloon("kraken_ui");
    }

    @Flaky
    @TmsLink("2168")
    @Test(description = "Поиск по номеру заказа", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void partnerSearchByOrderNumber() {
        shopperApp.deleteCurrentAssembly();

        var shipment = shopperApp.getShipmentByComment("PARTNER_SEARCH_BY_ORDER");

        if (Objects.isNull(shipment)) {
            SessionFactory.makeSession(SessionType.API_V2);
            OrderV2 order = helper.order(
                    SessionFactory.getSession(SessionType.API_V2).getUserData(),
                    EnvironmentProperties.DEFAULT_SID,
                    5,
                    "PARTNER_SEARCH_BY_ORDER");
            if (Objects.isNull(order)) throw new SkipException("Заказ не удалось оплатить");
            String errorMessageIfDeliveryIsNotToday = checkIsDeliveryToday(order);
            shipment = shopperApp.getShipment(order.getShipments().get(0).getNumber(), errorMessageIfDeliveryIsNotToday);
        }

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();

        partnersMap().addFilter(shipment.getAttributes().getNumber());
        partnersMap().clickOnApply();
        partnersMap().waitPageLoad();

        partnersMap().checkPartnerBalloon();
        partnersMap().checkOrderNumberInBalloon(shipment.getAttributes().getNumber());
    }

    @Flaky
    @TmsLink("2169")
    @Test(description = "Отображение исполнителей на карте", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void displayPerformersOnTheMap() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();
        partnersMap().addFilter("kraken_ui");
        partnersMap().clickOnApply();
        partnersMap().checkPartnerBalloon();
        partnersMap().checkMapWithBalloon();
    }

    @Skip
    @TmsLink("2170")
    @Test(description = "Обновление карты", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void mapUpdate() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();
    }

    @TmsLink("2171")
    @Test(description = "Открытие карты другого города", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void openingMapOfAnotherCity() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkRequestsWasLoad();
        partnersMap().checkMapLoaded();

        partnersMap().addFilterCity("Новосибирск");
        partnersMap().checkRequestsWasLoad();
        partnersMap().checkMapLoaded();

        partnersMap().checkMapScreen();
    }

    @Skip
    @TmsLink("2172")
    @Test(description = "Карточка исполнителя", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void artistCard() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();
    }

    @Skip
    @TmsLink("2173")
    @Test(description = "Отображение в легенде подсчет исполнителей", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void displayingThePerformerCountInTheLegend() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();
    }

    @TmsLink("2174")
    @Test(description = "Поиск по номеру телефона", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void searchByPhoneNumber() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();

        partnersMap().addFilter("+79235559227");
        partnersMap().clickOnApply();
        partnersMap().checkPartnerBalloon();
        partnersMap().checkPhoneInBalloon("+79235559227");
    }

    @Skip
    @TmsLink("2175")
    @Test(description = "Фильтр плановые / ondemand", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void filterOnDemand() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();
    }
}
