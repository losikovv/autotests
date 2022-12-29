package ru.instamart.test.reforged.admin.retailer;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ru.instamart.jdbc.dao.shopper.OperationalZonesShopperDao;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.reforged.Group.*;
import static ru.instamart.reforged.admin.AdminRout.login;
import static ru.instamart.reforged.admin.AdminRout.regions;

@Epic("Админка STF")
@Feature("Управление регионами ретейлера")
public final class AdministrationRetailerRegionsTests {

    @TmsLink("469")
    @Story("Тест добавления нового региона для магазинов в админке")
    @Test(description = "Создать новый регион",
            groups = {OD_SHOPPERS_REGRESS, OD_SHOPPERS_SMOKE, OD_SMOKE, OD_REGRESS})
    public void successCreateNewRetailerRegion() {
        final var regionName = "тест-" + Generate.literalCyrillicString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        regions().goToPage();
        regions().clickToAddNewRegion();

        regions().interactRegionsAddModal().checkAddNewRegionModalVisible();
        regions().interactRegionsAddModal().fillNewTestRegionName(regionName);
        regions().interactRegionsAddModal().clickToCreateNewRegion();

        regions().interactAlert().checkSuccessFlashVisible();
        regions().checkRegionInTableVisible(regionName);
    }

    @TmsLink("470")
    @Test(description = "Настройки региона",
            groups = {OD_SHOPPERS_REGRESS, OD_SHOPPERS_SMOKE, OD_SMOKE, OD_REGRESS})
    public void regionSetting() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        regions().goToPage();
        regions().checkRegionSettingsButtonsEqualsRegions();
        regions().clickToRegionSetting(0);
        regions().interactRegionsAddModal().checkAddNewRegionModalVisible();
    }

    @TmsLink("472")
    @Story("Валидация страницы регионов")
    @Test(description = "Валидация страницы регионов",
            groups = {OD_SHOPPERS_REGRESS, OD_SHOPPERS_SMOKE, OD_SMOKE, OD_REGRESS, PROD_ADMIN_SMOKE})
    public void storeRegionsPageValidation() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        regions().goToPage();
        regions().checkRegionsTableVisible();
        regions().checkRegionSettingsButtonsEqualsRegions();
        regions().checkDispatchSettingsButtonsEqualsRegions();
        regions().checkQuantityRowsNumbersEqualsRegions();
        regions().checkPageTitleVisible();
        regions().checkRegionSearchInputVisible();
        regions().checkAddNewRegionButtonVisible();
    }

    @AfterClass(alwaysRun = true)
    public void clearData() {
        OperationalZonesShopperDao.INSTANCE.deleteZoneByNameLike("тест-");
    }
}
