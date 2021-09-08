package ru.instamart.test.reforged.admin;

import io.qameta.allure.*;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.kraken.util.Crypt;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Управление регионами ретейлера")
public final class AdministrationRetailerRegionsTests extends BaseTest {

    @CaseId(469)
    @Story("Тест добавления нового региона для магазинов в админке")
    @Test(description = "Тест добавления нового региона для магазинов в админке",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void successCreateNewRetailerRegion() {
        final String cityName = "TestCity_" + Generate.literalString(6);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        cityAdd().goToPage();
        cityAdd().inputCityName(cityName);
        cityAdd().createNewCityButton();

        allCities().checkAddCityAlertVisible();

        regions().goToPage();
        regions().clickToAddNewRegion();

        regionsAdd().fillNewTestRegionName(cityName);
        regionsAdd().clickToCreateNewRegion();

        regions().checkPageUrl(EnvironmentData.INSTANCE.getAdminUrl() + regions().pageUrl());
        regions().checkSuccessCreateRegionAlertVisible();
        regions().checkAutotestRegionInTableVisible(cityName);

        shopAdd().goToPage();
        shopAdd().selectTestRegionInRegionsDropdown(cityName);
        shopAdd().goToPage();

        regions().goToPage();
        regions().deleteTestRegion(cityName);
        regions().confirmBrowserAlert();

        allCities().goToPage();
        allCities().deleteTestCity(cityName);
        allCities().confirmBrowserAlert();
        allCities().checkDeleteCityAlertVisible();
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
