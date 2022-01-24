package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.TimeUtil;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseId;


import java.text.ParseException;
import java.util.Date;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Управление ретейлерами")
public final class AdministrationRetailerTests extends BaseTest {

    private final ApiHelper apiHelper = new ApiHelper();

    @CaseId(535)
    @Story("Страница ретейлеров")
    @Test(description = "На страницу выводится весь список ретейлеров с информацией об их доступности и датах их создания", groups = {"acceptance", "regression"})
    public void successViewRetailerPage() {

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        final var retailersQuantity = retailers().retailerQuantityReturn();
        retailers().retailerAccessibilityCompare(retailersQuantity);
        retailers().retailerCreateDateCompare(retailersQuantity);
    }

    @CaseId(184)
    @Story("Страница ретейлеров")
    @Test(description = "Корректное отображение страницы загрузки зон", groups = {"acceptance", "regression"})
    public void successViewRetailerZones() {

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnRetailer("METRO");

        retailer().checkAddNewStoreButtonVisible();
        retailer().clickOnStore("тест-352519385 (17)");

        retailer().clickOnAddress("Москва, просп. Мира, 211, стр. 1");

        store().checkBackToStoresListButtonVisible();
        final var storeName = store().returnStoreName();

        store().clickOnRetailerZoneButton();

        zonePage().checkZonePageVisible();
        zonePage().checkZonePageTitleCorrect(storeName);

        final var zonesInTableQuantity = zonePage().zoneQuantityReturn();
        zonePage().zoneDeleteButtonsCompare(zonesInTableQuantity);
        zonePage().checkZonesTableVisible();
        zonePage().checkHintVisible();
        zonePage().checkBackButtonVisible();
        zonePage().checkDownloadButtonVisible();
        zonePage().assertAll();
    }

    @CaseId(213)
    @Story("Страница ретейлеров")
    @Test(description = "Ретейлер без действующих магазинов недоступен", groups = {"acceptance", "regression"})
    public void retailerInaccessibilityWithoutActiveStores() {

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnRetailer("ZBS.retailer");

        retailer().checkAddNewStoreButtonVisible();
        retailer().clickOnStore("тест-352519385 (1)");

        retailer().checkAllStoresDisable();

        retailers().goToPage();
        retailers().checkRetailerInactive("ZBS.retailer");
    }

    @CaseId(214)
    @Story("Страница ретейлеров")
    @Test(description = "Ритейлер доступен, если у него есть 1 или более действующих магазинов", groups = {"acceptance", "regression"})
    public void retailerAccessibilityWithActiveStore() {

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnRetailer("METRO");

        retailer().checkAddNewStoreButtonVisible();
        retailer().clickOnStore("тест-352519385 (17)");

        retailer().checkSomeStoreEnable();

        retailers().goToPage();
        retailers().checkRetailerActive("METRO");
    }

    @CaseId(536)
    @Story("Страница ретейлеров")
    @Test(description = "Cортировка городов по кол-ву магазинов в каждом и по алфавиту", groups = {"acceptance", "regression"})
    public void storesQuantityAndAlphabetSortCities() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnPlusForRetailer("METRO");

        retailers().checkStoreNumbersSortCorrect();
        retailers().checkStoreNameSortCorrect();
    }

    @CaseId(537)
    @Story("Страница ретейлеров")
    @Test(description = "Cортировка городов по дате создания", groups = {"acceptance", "regression"})
    public void storesCreateDateSortCities() throws ParseException {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnPlusForRetailer("METRO");
        retailers().clickOnStore("тест-352519385 (17)");

        retailers().checkDateSortCorrect();
    }

    @CaseId(558)
    @Story("Страница ретейлеров")
    @Test(description = "При клике на адрес магазина происходит переход на его страницу", groups = {"acceptance", "regression"})
    public void successTransitOnStorePageViaClickOnAddress() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnPlusForRetailer("METRO");
        retailers().clickOnStore("тест-352519385 (17)");
        retailers().clickOnAddress("Москва, просп. Мира, 211, стр. 1");

        store().checkBackToStoresListButtonVisible();
        store().checkAddressCorrect("Москва, просп. Мира, 211, стр. 1");
    }
}