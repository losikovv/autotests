package ru.instamart.test.reforged.admin;

import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import ru.instamart.jdbc.dao.shopper.OperationalZonesShopperDao;
import ru.sbermarket.qase.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Управление регионами ретейлера")
public final class AdministrationRetailerRegionsTests {

    @CaseId(469)
    @Story("Тест добавления нового региона для магазинов в админке")
    @Test(description = "Тест добавления нового региона для магазинов в админке", groups = {"regression", "smoke"})
    public void successCreateNewRetailerRegion() {

        final String regionName = "тест-" + Generate.literalCyrillicString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().clickToAddNewRegion();

        regions().interactRegionsAddModal().checkAddNewRegionModalVisible();
        regions().interactRegionsAddModal().fillNewTestRegionName(regionName);
        regions().interactRegionsAddModal().clickToCreateNewRegion();

        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableVisible(regionName);

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown(regionName);
    }

    @CaseId(472)
    @Story("Валидация страницы регионов")
    @Test(description = "Валидация страницы регионов", groups = "regression")
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
