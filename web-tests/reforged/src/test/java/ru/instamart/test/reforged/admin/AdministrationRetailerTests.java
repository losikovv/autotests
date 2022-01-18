package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;
import static ru.instamart.reforged.admin.AdminRout.regions;

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

    }

    @CaseId(185)
    @Story("Страница ретейлеров")
    @Test(description = "Проверка успешной загрузки файла с зонами доставки", groups = {"acceptance", "regression"})
    public void successDownloadRetailerZonesFile() {

        final String regionName = "тест-" + Generate.literalCyrillicString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().clickToAddNewRegion();

        regions().interactRegionsAddModal().checkAddNewRegionModalNotAnimated();
        regions().interactRegionsAddModal().fillNewTestRegionName(regionName);
        regions().interactRegionsAddModal().clickToCreateNewRegion();

        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableVisible(regionName);

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown(regionName);

        apiHelper.deleteOperationalZonesInShopper(regionName);

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableNotVisible(regionName);
    }

    @CaseId(213)
    @Story("Страница ретейлеров")
    @Test(description = "Ретейлер без действующих магазинов недоступен", groups = {"acceptance", "regression"})
    public void retailerInaccessibilityWithoutActiveStores() {

        final String regionName = "тест-" + Generate.literalCyrillicString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().clickToAddNewRegion();

        regions().interactRegionsAddModal().checkAddNewRegionModalNotAnimated();
        regions().interactRegionsAddModal().fillNewTestRegionName(regionName);
        regions().interactRegionsAddModal().clickToCreateNewRegion();

        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableVisible(regionName);

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown(regionName);

        apiHelper.deleteOperationalZonesInShopper(regionName);

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableNotVisible(regionName);
    }

    @CaseId(214)
    @Story("Страница ретейлеров")
    @Test(description = "Ритейлер доступен, если у него есть 1 или более действующих магазинов", groups = {"acceptance", "regression"})
    public void retailerAccessibilityWithActiveStore() {

        final String regionName = "тест-" + Generate.literalCyrillicString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().clickToAddNewRegion();

        regions().interactRegionsAddModal().checkAddNewRegionModalNotAnimated();
        regions().interactRegionsAddModal().fillNewTestRegionName(regionName);
        regions().interactRegionsAddModal().clickToCreateNewRegion();

        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableVisible(regionName);

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown(regionName);

        apiHelper.deleteOperationalZonesInShopper(regionName);

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableNotVisible(regionName);
    }

    @CaseId(536)
    @Story("Страница ретейлеров")
    @Test(description = "Cортировка городов по кол-ву магазинов в каждом и по алфавиту", groups = {"acceptance", "regression"})
    public void storesQuantityAndAlphabetSortCities() {

        final String regionName = "тест-" + Generate.literalCyrillicString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().clickToAddNewRegion();

        regions().interactRegionsAddModal().checkAddNewRegionModalNotAnimated();
        regions().interactRegionsAddModal().fillNewTestRegionName(regionName);
        regions().interactRegionsAddModal().clickToCreateNewRegion();

        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableVisible(regionName);

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown(regionName);

        apiHelper.deleteOperationalZonesInShopper(regionName);

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableNotVisible(regionName);
    }

    @CaseId(537)
    @Story("Страница ретейлеров")
    @Test(description = "Cортировка городов по дате создания", groups = {"acceptance", "regression"})
    public void storesCreateDateSortCities() {

        final String regionName = "тест-" + Generate.literalCyrillicString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().clickToAddNewRegion();

        regions().interactRegionsAddModal().checkAddNewRegionModalNotAnimated();
        regions().interactRegionsAddModal().fillNewTestRegionName(regionName);
        regions().interactRegionsAddModal().clickToCreateNewRegion();

        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableVisible(regionName);

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown(regionName);

        apiHelper.deleteOperationalZonesInShopper(regionName);

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableNotVisible(regionName);
    }

    @CaseId(558)
    @Story("Страница ретейлеров")
    @Test(description = "При клике на адрес магазина происходит переход на его страницу", groups = {"acceptance", "regression"})
    public void successTransitOnStorePageViaClickOnAddress() {

        final String regionName = "тест-" + Generate.literalCyrillicString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().clickToAddNewRegion();

        regions().interactRegionsAddModal().checkAddNewRegionModalNotAnimated();
        regions().interactRegionsAddModal().fillNewTestRegionName(regionName);
        regions().interactRegionsAddModal().clickToCreateNewRegion();

        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableVisible(regionName);

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown(regionName);

        apiHelper.deleteOperationalZonesInShopper(regionName);

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableNotVisible(regionName);
    }

    @CaseId(532)
    @Story("Страница ретейлеров")
    @Test(description = "Поиск ретейлера", groups = {"acceptance", "regression"})
    public void retailerSearchTest() {

        final String regionName = "тест-" + Generate.literalCyrillicString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().clickToAddNewRegion();

        regions().interactRegionsAddModal().checkAddNewRegionModalNotAnimated();
        regions().interactRegionsAddModal().fillNewTestRegionName(regionName);
        regions().interactRegionsAddModal().clickToCreateNewRegion();

        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableVisible(regionName);

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown(regionName);

        apiHelper.deleteOperationalZonesInShopper(regionName);

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableNotVisible(regionName);
    }

    @CaseId(533)
    @Story("Страница ретейлеров")
    @Test(description = "Сортировка ретейлеров по названию asc, desc", groups = {"acceptance", "regression"})
    public void retailerNamesSortTest() {

        final String regionName = "тест-" + Generate.literalCyrillicString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().clickToAddNewRegion();

        regions().interactRegionsAddModal().checkAddNewRegionModalNotAnimated();
        regions().interactRegionsAddModal().fillNewTestRegionName(regionName);
        regions().interactRegionsAddModal().clickToCreateNewRegion();

        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableVisible(regionName);

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown(regionName);

        apiHelper.deleteOperationalZonesInShopper(regionName);

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableNotVisible(regionName);
    }

    @CaseId(557)
    @Story("Страница ретейлеров")
    @Test(description = "Сортировка ретейлеров по дате создания", groups = {"acceptance", "regression"})
    public void retailerCreateDateSortTest() {

        final String regionName = "тест-" + Generate.literalCyrillicString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().clickToAddNewRegion();

        regions().interactRegionsAddModal().checkAddNewRegionModalNotAnimated();
        regions().interactRegionsAddModal().fillNewTestRegionName(regionName);
        regions().interactRegionsAddModal().clickToCreateNewRegion();

        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableVisible(regionName);

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown(regionName);

        apiHelper.deleteOperationalZonesInShopper(regionName);

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableNotVisible(regionName);
    }

    @CaseId(534)
    @Story("Страница ретейлеров")
    @Test(description = "Сохранение и воспроизведение фильтров и сортировок в URL", groups = {"acceptance", "regression"})
    public void successSaveAndRepeatFiltersAndSortViaURL() {

        final String regionName = "тест-" + Generate.literalCyrillicString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().clickToAddNewRegion();

        regions().interactRegionsAddModal().checkAddNewRegionModalNotAnimated();
        regions().interactRegionsAddModal().fillNewTestRegionName(regionName);
        regions().interactRegionsAddModal().clickToCreateNewRegion();

        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableVisible(regionName);

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown(regionName);

        apiHelper.deleteOperationalZonesInShopper(regionName);

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableNotVisible(regionName);
    }

    @CaseId(538)
    @Story("Страница ретейлеров")
    @Test(description = "Проверка работы кнопки 'Добавить ретейлера'", groups = {"acceptance", "regression"})
    public void successTransitOnRetailerCreatePageViaClickOnRetailerAdd() {

        final String regionName = "тест-" + Generate.literalCyrillicString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().clickToAddNewRegion();

        regions().interactRegionsAddModal().checkAddNewRegionModalNotAnimated();
        regions().interactRegionsAddModal().fillNewTestRegionName(regionName);
        regions().interactRegionsAddModal().clickToCreateNewRegion();

        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableVisible(regionName);

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown(regionName);

        apiHelper.deleteOperationalZonesInShopper(regionName);

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableNotVisible(regionName);
    }

    @CaseId(539)
    @Story("Страница ретейлеров")
    @Test(description = "Проверка работы кнопки 'Добавить магазин'", groups = {"acceptance", "regression"})
    public void successTransitOnStoreCreatePageViaClickOnStoreAdd() {

        final String regionName = "тест-" + Generate.literalCyrillicString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().clickToAddNewRegion();

        regions().interactRegionsAddModal().checkAddNewRegionModalNotAnimated();
        regions().interactRegionsAddModal().fillNewTestRegionName(regionName);
        regions().interactRegionsAddModal().clickToCreateNewRegion();

        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableVisible(regionName);

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown(regionName);

        apiHelper.deleteOperationalZonesInShopper(regionName);

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableNotVisible(regionName);
    }

    @CaseId(541)
    @Story("Страница ретейлеров")
    @Test(description = "Тест активации магазина", groups = {"acceptance", "regression"})
    public void successStoreActivate() {

        final String regionName = "тест-" + Generate.literalCyrillicString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().clickToAddNewRegion();

        regions().interactRegionsAddModal().checkAddNewRegionModalNotAnimated();
        regions().interactRegionsAddModal().fillNewTestRegionName(regionName);
        regions().interactRegionsAddModal().clickToCreateNewRegion();

        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableVisible(regionName);

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown(regionName);

        apiHelper.deleteOperationalZonesInShopper(regionName);

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableNotVisible(regionName);
    }

    @CaseId(542)
    @Story("Страница ретейлеров")
    @Test(description = "Тест деактивации магазина", groups = {"acceptance", "regression"})
    public void successStoreDeactivate() {

        final String regionName = "тест-" + Generate.literalCyrillicString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().clickToAddNewRegion();

        regions().interactRegionsAddModal().checkAddNewRegionModalNotAnimated();
        regions().interactRegionsAddModal().fillNewTestRegionName(regionName);
        regions().interactRegionsAddModal().clickToCreateNewRegion();

        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableVisible(regionName);

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown(regionName);

        apiHelper.deleteOperationalZonesInShopper(regionName);

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableNotVisible(regionName);
    }


    @CaseId(580)
    @Story("Страница ретейлеров")
    @Test(description = "Сортировка ретейлеров по доступности", groups = {"acceptance", "regression"})
    public void retailerAccessibilitySortTest() {

        final String regionName = "тест-" + Generate.literalCyrillicString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().clickToAddNewRegion();

        regions().interactRegionsAddModal().checkAddNewRegionModalNotAnimated();
        regions().interactRegionsAddModal().fillNewTestRegionName(regionName);
        regions().interactRegionsAddModal().clickToCreateNewRegion();

        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableVisible(regionName);

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown(regionName);

        apiHelper.deleteOperationalZonesInShopper(regionName);

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableNotVisible(regionName);
    }

    @CaseId(581)
    @Story("Страница ретейлеров")
    @Test(description = "Фильтрация ритейлеров и магазинов по региону", groups = {"acceptance", "regression"})
    public void retailerAndStoresRegionsSortTest() {

        final String regionName = "тест-" + Generate.literalCyrillicString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().clickToAddNewRegion();

        regions().interactRegionsAddModal().checkAddNewRegionModalNotAnimated();
        regions().interactRegionsAddModal().fillNewTestRegionName(regionName);
        regions().interactRegionsAddModal().clickToCreateNewRegion();

        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableVisible(regionName);

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown(regionName);

        apiHelper.deleteOperationalZonesInShopper(regionName);

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableNotVisible(regionName);
    }
}