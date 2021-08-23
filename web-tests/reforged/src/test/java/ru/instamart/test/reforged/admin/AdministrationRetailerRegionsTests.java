package ru.instamart.test.reforged.admin;

import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.admin.AdminRout.*;

public class AdministrationRetailerRegionsTests extends BaseTest {

    @CaseId(469)
    @Story("Тест добавления нового региона для магазинов в админке")
    @Test(description = "Тест добавления нового региона для магазинов в админке",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
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
    @Story("Верификация страницы регионов")
    @Test(description = "Верификация страницы регионов",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void storeRegionsPageValidation() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        users().goToPage();

    }

    @CaseId(471)
    @Story("Тест добавления удаления региона для магазинов в админке")
    @Test(description = "Тест добавления удаления региона для магазинов в админке",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void successDeleteRetailerRegion() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());


    }
}
