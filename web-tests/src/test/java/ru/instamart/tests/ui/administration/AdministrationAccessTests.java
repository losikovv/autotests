package ru.instamart.tests.ui.administration;

import ru.instamart.core.testdata.UserManager;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;
import ru.instamart.ui.checkpoint.admin.AdminPageCheckpoints;
import ru.instamart.ui.common.lib.Pages;
import ru.instamart.ui.module.User;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

@Epic("Админка STF")
@Feature("Доступ в админку")
public class AdministrationAccessTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    AdminPageCheckpoints adminChecks = new AdminPageCheckpoints();

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        User.Logout.quicklyAdmin();
    }

    //TODO сделать тесты на проверку доступов всех ролей пользователей админки - ATST-232
    @CaseId(417)
    @Story("Тест недоступности админки пользователю без админ. прав")
    @Test(  description = "Тест недоступности админки пользователю без админ. прав",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    ) public void noAccessToAdministrationWithoutAdminPrivileges() {
        User.Do.loginAs(UserManager.getDefaultUser());
        baseChecks.checkPageIsUnavailable(Pages.Admin.shipments());
    }

    @CaseId(418)
    @Story("Тест доступности админки пользователю c админ. правми")
    @Test(  description = "Тест доступности админки пользователю c админ. правми",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    ) public void successAccessAdministrationThroughAuthOnSite() {
        User.Do.loginAs(UserManager.getDefaultAdmin());
        kraken.get().page(Pages.Admin.shipments());
        adminChecks.checkIsAdminPageOpen();
    }
}
