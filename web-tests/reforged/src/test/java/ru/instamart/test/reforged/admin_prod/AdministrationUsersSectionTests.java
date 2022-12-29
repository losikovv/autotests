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
@Feature("Управление юзерами")
public final class AdministrationUsersSectionTests {

    @TmsLink("19")
    @Story("Тест поиска пользователя в админке")
    @Test(description = "Работоспособность поиска в списке юзеров", groups =  PROD_ADMIN_SMOKE)
    public void successSearchUser() {

        final var email = UserManager.getDefaultAdmin().getEmail();

        UserData defaultAdmin = UserManager.getDefaultAdmin();

        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail(defaultAdmin.getEmail());
        activeDirectory().fillPassword(defaultAdmin.getPassword());
        activeDirectory().clickOnLoginButton();

        main().interactAuthoredHeader().checkAdminAuth();

        users().goToPage();
        users().fillSearchByEmailOrPhone(email);
        users().clickToSearch();
        users().checkFoundUserEmail(users().getFoundUserEmail(), email);
    }
}
