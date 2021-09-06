package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Управление регионами ретейлера")
public final class AdministrationRetailerRegionsTests extends BaseTest {

    @CaseId(469)
    @Story("Тест добавления нового региона для магазинов в админке")
    @Test(description = "Тест добавления нового региона для магазинов в админке",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"},
            enabled = false
    )
    public void successCreateNewRetailerRegion() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        regions().goToPage();
        regions().checkAutotestRegionInTableNotVisible();
        regions().clickToAddNewRegion();

        regionsAdd().fillNewTestRegionName();
        regionsAdd().clickToCreateNewRegion();

        regions().checkPageUrl(EnvironmentData.INSTANCE.getAdminUrl() + regions().pageUrl());
        regions().checkSuccessCreateRegionAlertVisible();
        regions().checkAutotestRegionInTableVisible();

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown();

        regions().goToPage();
        regions().deleteTestRegion();
        regions().confirmBrowserAlert();
        regions().checkAutotestRegionInTableNotVisible();
    }

    @CaseId(472)
    @Story("Валидация страницы регионов")
    @Test(description = "Валидация страницы регионов",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
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
