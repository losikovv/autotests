package ru.instamart.test.reforged.admin;

import io.qameta.allure.*;
import ru.sbermarket.qase.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Управление регионами ретейлера")
public final class AdministrationRetailerRegionsTests extends BaseTest {

    private final ApiHelper apiHelper = new ApiHelper();

    @CaseId(469)
    @Story("Тест добавления нового региона для магазинов в админке")
    @Test(description = "Тест добавления нового региона для магазинов в админке", groups = {"acceptance", "regression", "smoke"})
    public void successCreateNewRetailerRegion() {

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

        apiHelper.deleteOperationalZonesInAdmin(regionName);

        regions().goToPage();
        regions().checkAddNewRegionButtonVisible();
        regions().checkRegionInTableNotVisible(regionName);
    }

    @CaseId(472)
    @Story("Валидация страницы регионов")
    @Test(enabled = false, description = "Валидация страницы регионов", groups = {"acceptance", "regression"})
    public void storeRegionsPageValidation() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        regions().goToPage();
        regions().checkRegionsTableVisible();
        regions().checkQuantityDeleteButtonsEqualsRegions();
        regions().checkQuantityEditButtonsEqualsRegions();
        regions().checkQuantityRowsNumbersEqualsRegions();
        regions().checkPageTitleVisible();
        regions().checkAddNewRegionButtonVisible();
    }
}
