package ru.instamart.tests.ui.administration;

import instamart.core.testdata.UserManager;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.checkpoints.users.AdminPageCheckpoints;
import instamart.ui.common.lib.Pages;
import instamart.ui.modules.User;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

public class AdministrationAccessTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    AdminPageCheckpoints adminChecks = new AdminPageCheckpoints();
    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
    }

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.get().page(Pages.Admin.login());
    }

    //TODO сделать тесты на проверку доступов всех ролей пользователей админки - ATST-232
    @CaseId(417)
    @Test(  description = "Тест недоступности админки пользователю без админ. прав",

            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    ) public void noAccessToAdministrationWithoutAdminPrivileges() {
        User.Do.loginAs(UserManager.getDefaultUser());
        baseChecks.checkPageIsUnavailable(Pages.Admin.shipments());
        User.Logout.quickly();
    }

    @CaseId(418)
    @Test(  description = "Тест доступности админки пользователю c админ. правми",

            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    ) public void successAccessAdministrationThroughAuthOnSite() {
        User.Do.loginAs(UserManager.getDefaultAdmin());
        kraken.get().page(Pages.Admin.shipments());
        adminChecks.checkIsAdminPageOpen();
    }
}
