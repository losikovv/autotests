package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Управление ретейлерами")
public final class AdministrationRetailerTests {

    private final ApiHelper apiHelper = new ApiHelper();
    private final String retailerName = Generate.literalString(6) + "_retailer";

    private final String cityNameFirst = Generate.literalString(6) + "_city";
    private final String cityNameSecond = Generate.literalString(6) + "_city";

    @BeforeClass(alwaysRun = true)
    public void prepareData() {
        apiHelper.createRetailerInAdmin(retailerName);

        apiHelper.setupCity(cityNameFirst);
        final var firstStore = apiHelper.createStoreInAdmin(retailerName, cityNameFirst);

        apiHelper.setupCity(cityNameSecond);
        final var secondStore = apiHelper.createStoreInAdmin(retailerName, cityNameSecond);
        final var thirdStore = apiHelper.createStoreInAdmin(retailerName, cityNameSecond);

        apiHelper.setupStoreForActivation(firstStore);
        apiHelper.setupStoreForActivation(secondStore);
        apiHelper.setupStoreForActivation(thirdStore);
    }

    @CaseId(535)
    @Story("Страница ретейлеров")
    @Test(description = "На страницу выводится весь список ретейлеров с информацией об их доступности и датах их создания", groups = "regression")
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
    @Test(description = "Корректное отображение страницы загрузки зон", groups = "regression")
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

    @CaseId(536)
    @Story("Страница ретейлеров")
    @Test(description = "Cортировка городов по кол-ву магазинов в каждом и по алфавиту", groups = "regression")
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
    @Test(description = "Cортировка городов по дате создания", groups = "regression")
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
    @Test(description = "При клике на адрес магазина происходит переход на его страницу", groups = "regression")
    public void successTransitOnStorePageViaClickOnAddress() {
        String address;

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnPlusForRetailer(retailerName);
        retailers().clickOnFirstStore();

        address = retailers().getFirstAddressFromTable();
        retailers().clickOnFirstAddress();

        store().checkBackToStoresListButtonVisible();
        store().checkAddressCorrect(address);
    }

    @CaseId(532)
    @Story("Страница ретейлеров")
    @Test(description = "Поиск ретейлера", groups = "regression")
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

        retailers().checkRetailersNotLoaded();
        retailers().checkRetailersLoaded();

        retailers().checkRetailerLabelInSearchFieldVisible(retailerName);
        retailers().checkRetailerSearchCorrect(retailerName);
    }

    @CaseId(533)
    @Story("Страница ретейлеров")
    @Test(description = "Сортировка по названию", groups = "regression")
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
    @Test(description = "Сортировка по дате создания", groups = "regression")
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
    @Test(description = "Сохранение и воспроизведение фильтров и сортировок в URL", groups = "regression")
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
    @Test(description = "Кнопка 'Добавить ритейлера' ведёт на страницу создания нового ритейлера", groups = "regression")
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
    @Test(description = "Кнопка 'Добавить магазин' ведёт на страницу создания нового магазина ритейлера", groups = "regression")
    public void successTransitToRetailerStoreCreatePage() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().clickOnAddStore(retailerName);

        shopAdd().checkPageContains("retailers/"+ retailerName  + "_slug/stores/new");
        shopAdd().checkRegionInputVisible();
    }

    @CaseId(580)
    @Story("Страница ретейлеров")
    @Test(description = "Фильтрация ритейлеров по доступности", groups = "regression")
    public void retailerFilterViaAvailability() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().checkRetailersLoaded();
        retailers().checkAccessibilityFilterButtonNotAnimated();
        retailers().checkAccessibilityFilterButtonClickable();
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
        retailers().checkRetailersLoaded();

        retailers().checkOnlyInaccessibleRetailersVisible();
        retailers().assertAll();
    }

    @CaseId(581)
    @Story("Страница ретейлеров")
    @Test(description = "Фильтрация ритейлеров и магазинов по региону", groups = "regression")
    public void retailerFilterViaRegion() {
        String cityName = "Москва";

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().checkRetailersLoaded();
        retailers().fillRegionSearch(cityName);
        retailers().checkOptionsInRegionSearchVisible();
        retailers().clickOnFirstRegionInSearchSuggest();

        retailers().refresh();
        retailers().clickOnPlusForFirstRetailer();
        retailers().checkRetailerRegionCorrect(cityName);
    }

    @AfterClass(alwaysRun = true)
    public void clearData() {
        apiHelper.deleteRetailerWithStores(retailerName);

        apiHelper.deleteCityInAdmin(cityNameFirst);
        apiHelper.deleteOperationalZonesInAdmin(cityNameFirst);
        apiHelper.deleteOperationalZonesInShopper(cityNameFirst);

        apiHelper.deleteCityInAdmin(cityNameSecond);
        apiHelper.deleteOperationalZonesInAdmin(cityNameSecond);
        apiHelper.deleteOperationalZonesInShopper(cityNameSecond);
    }
}