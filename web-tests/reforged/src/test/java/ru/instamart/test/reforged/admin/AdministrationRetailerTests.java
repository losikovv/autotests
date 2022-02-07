package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Run;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;
import static ru.instamart.reforged.admin.AdminRout.retailers;

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

        final int retailersQuantity = retailers().retailerQuantityReturn();
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

        retailers().checkStoreSortViaNumbersCorrect();

        retailers().checkIfStoreAlphabeticallySorted();
    }

    @CaseId(537)
    @Story("Страница ретейлеров")
    @Test(description = "Cортировка городов по дате создания", groups = {"acceptance", "regression"})
    public void storesCreateDateSortCities() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnPlusForRetailer("METRO");
        retailers().clickOnStore("тест-352519385 (17)");

        retailers().checkStoreSortViaCreationDateCorrect();
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

    @CaseId(532)
    @Story("Страница ретейлеров")
    @Test(description = "Поиск ретейлера", groups = {"acceptance", "regression"})
    public void retailerSearch() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().clickOnRetailerSearchElement();
        retailers().checkOptionsInRetailerSearchVisible();

        retailers().clickOnRetailerSearchElement();
        retailers().searchRetailerFill("METRO");
        retailers().clickOnFirstRetailerInSearchSuggest();

        retailers().checkRetailerLabelInSearchFieldVisible("METRO");
        retailers().checkRetailerSearchCorrect("METRO");
    }

    @CaseId(533)
    @Story("Страница ретейлеров")
    @Test(description = "Сортировка по названию", groups = {"acceptance", "regression"})
    public void retailerSortViaName() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().clickOnSortViaName();
        retailers().checkSpinnerVisible();
        retailers().checkSpinnerNotVisible();

        retailers().checkSortViaNameAscEnabled();
        retailers().checkSortViaNameAsc();

        retailers().clickOnSortViaName();
        retailers().checkSpinnerVisible();
        retailers().checkSpinnerNotVisible();

        retailers().checkSortViaNameDescEnabled();
        retailers().checkSortViaNameDesc();
        retailers().assertAll();
    }

    @CaseId(557)
    @Story("Страница ретейлеров")
    @Test(description = "Сортировка по дате создания", groups = {"acceptance", "regression"})
    public void retailerSortViaDate() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().clickOnSortViaCreationDate();
        retailers().checkSpinnerVisible();
        retailers().checkSpinnerNotVisible();

        retailers().checkSortViaCreationDateAscEnabled();
        retailers().checkSortViaCreationDateAsc();

        retailers().clickOnSortViaCreationDate();
        retailers().checkSpinnerVisible();
        retailers().checkSpinnerNotVisible();

        retailers().checkSortViaCreationDateDescEnabled();
        retailers().checkSortViaCreationDateDesc();
        retailers().assertAll();
    }

    @CaseId(534)
    @Story("Страница ретейлеров")
    @Test(description = "Сохранение и воспроизведение фильтров и сортировок в URL", groups = {"acceptance", "regression"})
    public void saveSortViaUrl() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().clickOnSortViaCreationDate();
        retailers().checkSpinnerVisible();
        retailers().checkSpinnerNotVisible();

        retailers().checkSortViaCreationDateAscEnabled();
        retailers().checkSortViaCreationDateAsc();

        retailers().checkPageContains("?sortKey=created_at&sortOrder=ascend");

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().openAdminPage("retailers?sortKey=created_at&sortOrder=ascend");
        retailers().checkAddNewRetailerButtonVisible();

        retailers().checkSortViaCreationDateAscEnabled();
        retailers().checkSortViaCreationDateAsc();

        retailers().assertAll();
    }

    @CaseId(538)
    @Story("Страница ретейлеров")
    @Test(description = "Кнопка 'Добавить ритейлера' ведёт на страницу создания нового ритейлера", groups = {"acceptance", "regression"})
    public void successTransitToRetailerCreatePage() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().clickOnAddRetailerButton();

        retailerAdd().checkPageContains(retailerAdd().pageUrl());
        retailerAdd().checkNameInputVisible();
    }

    @CaseId(539)
    @Story("Страница ретейлеров")
    @Test(description = "Кнопка 'Добавить магазин' ведёт на страницу создания нового магазина ритейлера", groups = {"acceptance", "regression"})
    public void successTransitToRetailerStoreCreatePage() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().clickOnAddStore("Flora");

        shopAdd().checkPageContains("retailers/flora/stores/new");
        shopAdd().checkRegionDropdownVisible();
    }

    @Run(onServer = Server.PREPROD)
    @CaseId(541)
    @Story("Страница ретейлеров")
    @Test(description = "Активация магазина", groups = {"acceptance", "regression"})
    public void shopActivate() {
        final Long storeId = 18L;
        apiHelper.updateStore(storeId, null);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().clickOnPlusForRetailer("METRO");
        retailers().clickOnPlusForCity("тест-352519385 (17)");

        retailers().clickOnActivateStoreViaAddress("Москва, Обучение, 1 ");

        retailers().interactActivateStoreModal().checkActivateStoreModalVisible();
        retailers().interactActivateStoreModal().clickOnOkButton();
        retailers().interactActivateStoreModal().checkActivateStoreModalNotVisible();

        retailers().checkStoreActiveViaAddress("Москва, Обучение, 1 ");
    }

    @Run(onServer = Server.PREPROD)
    @CaseId(542)
    @Story("Страница ретейлеров")
    @Test(description = "Деактивация магазина", groups = {"acceptance", "regression"})
    public void shopInactivate() {
        final Long storeId = 18L;
        apiHelper.updateStore(storeId, "2020-04-08 10:27:00");

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().clickOnPlusForRetailer("METRO");
        retailers().clickOnPlusForCity("тест-352519385 (17)");

        retailers().clickOnDeactivateStoreViaAddress("Москва, Обучение, 1 ");
        retailers().checkDeactivateStorePopupVisible();
        retailers().clickOkOnDeactivateStorePopup();
        retailers().checkDeactivateStorePopupNotVisible();
        retailers().checkStoreInativeViaAddress("Москва, Обучение, 1 ");
    }

    @CaseId(580)
    @Story("Страница ретейлеров")
    @Test(description = "Фильтрация ритейлеров по доступности", groups = {"acceptance", "regression"})
    public void retailerFilterViaAvailability() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().waitPageLoad();

        retailers().checkAccessibilityFilterButtonNotAnimated();
        retailers().clickOnAccessibilityFilter();
        retailers().checkAccessibilityFilterDropdownVisible();

        retailers().selectAccessibleRetailers();
        retailers().clickOnOkRetailersFilterButton();
        retailers().checkAccessibilityFilterDropdownNotVisible();

        retailers().checkOnlyAccessibleRetailersVisible();

        retailers().clickOnAccessibilityFilter();
        retailers().checkAccessibilityFilterDropdownVisible();

        retailers().selectInaccessibleRetailers();
        retailers().clickOnOkRetailersFilterButton();
        retailers().checkAccessibilityFilterDropdownNotVisible();

        retailers().checkOnlyInaccessibleRetailersVisible();
        retailers().assertAll();
    }

    @CaseId(581)
    @Story("Страница ретейлеров")
    @Test(description = "Фильтрация ритейлеров и магазинов по региону", groups = {"acceptance", "regression"})
    public void retailerFilterViaRegion() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().searchRegionFill("Казань");
        retailers().checkOptionsInRegionSearchVisible();
        retailers().clickOnFirstRegionInSearchSuggest();

        retailers().clickOnPlusForFirstRetailer();
        retailers().checkRetailerRegionCorrect("Казань");

    }
}