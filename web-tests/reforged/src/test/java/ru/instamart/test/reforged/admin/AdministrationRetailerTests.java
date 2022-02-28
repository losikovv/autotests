package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.api.request.admin.StoresAdminRequest;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;
import static ru.instamart.reforged.admin.AdminRout.retailers;

@Epic("Админка STF")
@Feature("Управление ретейлерами")
public final class AdministrationRetailerTests extends BaseTest {

    private final ApiHelper apiHelper = new ApiHelper();
    String retailerName = Generate.literalString(6) + "_retailer";

    String cityNameFirst = Generate.literalString(6) + "_city";
    String cityNameSecond = Generate.literalString(6) + "_city";

    StoresAdminRequest.Store firstStore = new StoresAdminRequest.Store();
    StoresAdminRequest.Store secondStore = new StoresAdminRequest.Store();
    StoresAdminRequest.Store thirdStore = new StoresAdminRequest.Store();

    @BeforeClass(alwaysRun = true)
    public void prepareData() {
        apiHelper.createRetailerInAdmin(retailerName);

        apiHelper.setupCity(cityNameFirst);
        firstStore = apiHelper.createStoreInAdmin(retailerName, cityNameFirst);

        apiHelper.setupCity(cityNameSecond);
        secondStore = apiHelper.createStoreInAdmin(retailerName, cityNameSecond);
        thirdStore = apiHelper.createStoreInAdmin(retailerName, cityNameSecond);

        apiHelper.setupStoreForActivation(firstStore);
        apiHelper.setupStoreForActivation(secondStore);
        apiHelper.setupStoreForActivation(thirdStore);
    }

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
        retailers().clickOnRetailer(retailerName);

        retailer().checkAddNewStoreButtonVisible();
        retailer().clickOnFirstStore();

        retailer().clickOnFirstAddress();

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
        String retailerNameUnique = Generate.literalString(6) + "_retailer";
        String cityNameFirstUnique = Generate.literalString(6) + "_city";

        StoresAdminRequest.Store firstStore;

        apiHelper.createRetailerInAdmin(retailerNameUnique);

        apiHelper.setupCity(cityNameFirstUnique);
        firstStore = apiHelper.createStoreInAdmin(retailerNameUnique, cityNameFirstUnique);

        apiHelper.setupStoreForActivation(firstStore);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnRetailer(retailerNameUnique);

        retailer().checkAddNewStoreButtonVisible();
        retailer().clickOnFirstStore();

        retailer().checkAllStoresDisable();

        retailers().goToPage();
        retailers().checkRetailerInactive(retailerNameUnique);

        apiHelper.deleteRetailerByNameInAdmin(retailerNameUnique);

        apiHelper.deleteCityInAdmin(cityNameFirstUnique);
        apiHelper.deleteOperationalZonesInAdmin(cityNameFirstUnique);

        apiHelper.deleteStoreInAdmin(firstStore);
    }

    @CaseId(214)
    @Story("Страница ретейлеров")
    @Test(description = "Ритейлер доступен, если у него есть 1 или более действующих магазинов", groups = {"acceptance", "regression"})
    public void retailerAccessibilityWithActiveStore() {
        String retailerNameUnique = Generate.literalString(6) + "_retailer";
        String cityNameFirstUnique = Generate.literalString(6) + "_city";

        StoresAdminRequest.Store firstStore;

        apiHelper.createRetailerInAdmin(retailerNameUnique);

        apiHelper.setupCity(cityNameFirstUnique);
        firstStore = apiHelper.createStoreInAdmin(retailerNameUnique, cityNameFirstUnique);

        apiHelper.setupStoreForActivation(firstStore);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().clickOnPlusForRetailer(retailerNameUnique);
        retailers().clickOnPlusForFirstCity();

        retailers().clickOnActivateStoreViaAddress(cityNameFirstUnique + ", Mira, 211 ");

        retailers().interactActivateStoreModal().checkActivateStoreModalVisible();
        retailers().interactActivateStoreModal().clickOnOkButton();
        retailers().interactActivateStoreModal().checkActivateStoreModalNotVisible();

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnRetailer(retailerNameUnique);

        retailer().checkAddNewStoreButtonVisible();
        retailer().clickOnFirstStore();

        retailer().checkSomeStoreEnable();

        retailers().goToPage();
        retailers().checkRetailerActive(retailerNameUnique);

        apiHelper.deleteRetailerByNameInAdmin(retailerNameUnique);

        apiHelper.deleteCityInAdmin(cityNameFirstUnique);
        apiHelper.deleteOperationalZonesInAdmin(cityNameFirstUnique);

        apiHelper.deleteStoreInAdmin(firstStore);

    }

    @CaseId(536)
    @Story("Страница ретейлеров")
    @Test(description = "Cортировка городов по кол-ву магазинов в каждом и по алфавиту", groups = {"acceptance", "regression"})
    public void storesQuantityAndAlphabetSortCities() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnPlusForRetailer(retailerName);

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
        retailers().clickOnPlusForRetailer(retailerName);
        retailers().clickOnFirstStore();

        retailers().checkStoreSortViaCreationDateCorrect();
    }

    @CaseId(558)
    @Story("Страница ретейлеров")
    @Test(description = "При клике на адрес магазина происходит переход на его страницу", groups = {"acceptance", "regression"})
    public void successTransitOnStorePageViaClickOnAddress() {
        String address;

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnPlusForRetailer(retailerName);
        retailers().clickOnFirstStore();

        address = retailers().returnFirstAddressFromTable();
        retailers().clickOnFirstAddress();

        store().checkBackToStoresListButtonVisible();
        store().checkAddressCorrect(address);
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
        retailers().fillRetailerSearch(retailerName);
        retailers().clickOnFirstRetailerInSearchSuggest();

        retailers().checkRetailerLabelInSearchFieldVisible(retailerName);
        retailers().checkRetailerSearchCorrect(retailerName);
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
        retailers().refresh();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().checkSortViaNameAscEnabled();
        retailers().checkSortViaNameAsc();

        retailers().clickOnSortViaName();
        retailers().refresh();
        retailers().checkAddNewRetailerButtonVisible();

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
        retailers().refresh();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().checkSortViaCreationDateAscEnabled();
        retailers().checkSortViaCreationDateAsc();

        retailers().clickOnSortViaCreationDate();
        retailers().refresh();
        retailers().checkAddNewRetailerButtonVisible();

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
        retailers().refresh();
        retailers().checkAddNewRetailerButtonVisible();

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

        retailers().clickOnAddStore(retailerName);

        shopAdd().checkPageContains("retailers/"+ retailerName  + "_slug/stores/new");
        shopAdd().checkRegionDropdownVisible();
    }

    @CaseIDs(value = {@CaseId(541), @CaseId(542)})
    @Story("Страница ретейлеров")
    @Test(description = "Активация, деактивация магазина", groups = {"acceptance", "regression"})
    public void shopActivateAndDeactivate() {
        String retailerNameUnique = Generate.literalString(6) + "_retailer";
        String cityNameFirstUnique = Generate.literalString(6) + "_city";

        StoresAdminRequest.Store firstStore;

        apiHelper.createRetailerInAdmin(retailerNameUnique);

        apiHelper.setupCity(cityNameFirstUnique);
        firstStore = apiHelper.createStoreInAdmin(retailerNameUnique, cityNameFirstUnique);

        apiHelper.setupStoreForActivation(firstStore);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().clickOnPlusForRetailer(retailerNameUnique);
        retailers().clickOnPlusForFirstCity();

        retailers().clickOnActivateStoreViaAddress(cityNameFirstUnique + ", Mira, 211 ");

        retailers().interactActivateStoreModal().checkActivateStoreModalVisible();
        retailers().interactActivateStoreModal().clickOnOkButton();
        retailers().interactActivateStoreModal().checkActivateStoreModalNotVisible();

        retailers().checkStoreActiveViaAddress(cityNameFirstUnique + ", Mira, 211 ");

        retailers().clickOnDeactivateStoreViaAddress(cityNameFirstUnique + ", Mira, 211 ");

        retailers().checkDeactivateStorePopupVisible();
        retailers().clickOkOnDeactivateStorePopup();
        retailers().checkDeactivateStorePopupNotVisible();

        retailers().checkStoreInactiveViaAddress(cityNameFirstUnique + ", Mira, 211 ");

        apiHelper.deleteRetailerByNameInAdmin(retailerNameUnique);

        apiHelper.deleteCityInAdmin(cityNameFirstUnique);
        apiHelper.deleteOperationalZonesInAdmin(cityNameFirstUnique);

        apiHelper.deleteStoreInAdmin(firstStore);
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

        retailers().fillRegionSearch(cityNameFirst);
        retailers().checkOptionsInRegionSearchVisible();
        retailers().clickOnFirstRegionInSearchSuggest();

        retailers().refresh();
        retailers().clickOnPlusForFirstRetailer();
        retailers().checkRetailerRegionCorrect(cityNameFirst);
    }

    @AfterClass(alwaysRun = true)
    public void clearData() {
        apiHelper.deleteRetailerByNameInAdmin(retailerName);

        apiHelper.deleteCityInAdmin(cityNameFirst);
        apiHelper.deleteOperationalZonesInAdmin(cityNameFirst);

        apiHelper.deleteCityInAdmin(cityNameSecond);
        apiHelper.deleteOperationalZonesInAdmin(cityNameSecond);

        apiHelper.deleteStoreInAdmin(firstStore);
        apiHelper.deleteStoreInAdmin(secondStore);
        apiHelper.deleteStoreInAdmin(thirdStore);
    }
}