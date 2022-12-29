package ru.instamart.test.reforged.admin_prod;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.reforged.Group.PROD_ADMIN_SMOKE;
import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Страница логина")
public final class AdministrationLoginTests {

    @TmsLink("2")
    @Story("Тест логаута из админки")
    @Test(description = "Тест логаута из админки", groups = PROD_ADMIN_SMOKE)
    public void successLogoutFromAdminPage() {
        UserData defaultAdmin = UserManager.getDefaultAdmin();

        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail(defaultAdmin.getEmail());
        activeDirectory().fillPassword(defaultAdmin.getPassword());
        activeDirectory().clickOnLoginButton();

        main().interactAuthoredHeader().checkAdminAuth();

        main().interactAuthoredHeader().clickToDropDown();
        main().interactAuthoredHeader().clickToLogout();

        login().checkTitle();
    }
}
