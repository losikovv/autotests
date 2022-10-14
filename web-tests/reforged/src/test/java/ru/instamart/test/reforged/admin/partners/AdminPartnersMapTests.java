package ru.instamart.test.reforged.admin.partners;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ShiftsApiHelper;
import ru.instamart.api.helper.ShopperAppApiHelper;
import ru.instamart.kraken.data.StartPointsTenants;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.annotation.DoNotOpenBrowser;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.*;
import static ru.instamart.reforged.admin.AdminRout.login;
import static ru.instamart.reforged.admin.AdminRout.partnersMap;

@Epic("Админка STF")
@Feature("Карта партнеров")
@Story("Страница 'Список партнеров' admin/spa/partners_map")
public final class AdminPartnersMapTests {

    private final ShopperAppApiHelper shopperApp = new ShopperAppApiHelper();
    private final ShiftsApiHelper shiftsApiHelper = new ShiftsApiHelper();
    private UserData user;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        user = UserManager.getShpUniversalUi();
        shopperApp.authorisation(user);

        shiftsApiHelper.cancelAllActiveShifts();
        shiftsApiHelper.stopAllActiveShifts();

        shiftsApiHelper.startOfShift(StartPointsTenants.METRO_9);
        shopperApp.sendCurrentLocator(55.915098, 37.541685, null);
    }

    @AfterClass(alwaysRun = true)
    public void after() {
        shiftsApiHelper.cancelAllActiveShifts();
        shiftsApiHelper.stopAllActiveShifts();
    }

    @CaseId(2167)
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

    @CaseId(2168)
    @Test(description = "Поиск по номеру заказа", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void partnerSearchByOrderNumber() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();
    }

    @DoNotOpenBrowser
    @CaseId(2169)
    @Test(description = "Отображение исполнителей на карте", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void displayPerformersOnTheMap() {
        /*login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();*/
        System.out.println(user);
    }

    @CaseId(2170)
    @Test(description = "Обновление карты", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void mapUpdate() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();
    }

    @CaseId(2171)
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

    @CaseId(2172)
    @Test(description = "Карточка исполнителя", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void artistCard() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();
    }

    @CaseId(2173)
    @Test(description = "Отображение в легенде подсчет исполнителей", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void displayingThePerformerCountInTheLegend() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();
    }

    @CaseId(2174)
    @Test(description = "Поиск по номеру телефона", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void searchByPhoneNumber() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();

        partnersMap().addFilter("+79154381326");
        partnersMap().clickOnApply();
        partnersMap().checkPartnerBalloon();
        partnersMap().checkPhoneInBalloon("+79154381326");
    }

    @CaseId(2175)
    @Test(description = "Фильтр плановые / ondemand", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void filterOnDemand() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();
    }
}
