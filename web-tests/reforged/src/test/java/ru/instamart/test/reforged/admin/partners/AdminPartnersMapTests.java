package ru.instamart.test.reforged.admin.partners;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminGroup.*;
import static ru.instamart.reforged.admin.AdminRout.login;
import static ru.instamart.reforged.admin.AdminRout.partnersMap;

@Epic("Админка STF")
@Feature("Карта партнеров")
@Story("Страница 'Список партнеров' admin/spa/partners_map")
public final class AdminPartnersMapTests {

    @CaseId(2167)
    @Test(description = "Поиск партнера по ФИО", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void partnerSearchByName() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();

        partnersMap().addFilter("Алексей Павловвв");
        partnersMap().clickOnApply();
        partnersMap().checkPartnerBalloon();
        partnersMap().checkNameInBalloon("Алексей Павловвв");
    }

    @CaseId(2168)
    @Test(description = "Поиск по номеру заказа", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void partnerSearchByOrderNumber() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();
    }

    @CaseId(2169)
    @Test(description = "Отображение исполнителей на карте", groups = {OD_SHOPPERS_MAP_REGRESS, OD_SHOPPERS_MAP_SMOKE, OD_SMOKE, OD_REGRESS})
    public void displayPerformersOnTheMap() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();
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
    public void openingAMapOfAnotherCity() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        partnersMap().goToPage();
        partnersMap().checkMapLoaded();

        partnersMap().addFilterCity("Новосибирск");
        partnersMap().checkMapLoaded();
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
