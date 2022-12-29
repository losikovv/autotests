package ru.instamart.test.reforged.admin;

import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.reforged.Group.*;
import static ru.instamart.reforged.admin.AdminRout.*;

public class ActiveDirectoryLoginTests {

    @TmsLink("31")
    @Story("Успешная авторизация через Active Directory")
    @Test(description = "Успешная аутентификация через Active Directory в админке STF", groups = {REGRESSION_ADMIN, ADMIN_KEYCLOAK, PROD_ADMIN_SMOKE})
    public void successLoginViaActiveDirectory() {
        UserData activeDirectoryUser = UserManager.getDefaultActiveDirectoryUser();

        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail(activeDirectoryUser.getEmail());
        activeDirectory().fillPassword(activeDirectoryUser.getPassword());
        activeDirectory().clickOnLoginButton();

        main().interactAuthoredHeader().checkAdminAvatar();
    }

    @TmsLink("32")
    @Story("Неуспешная авторизация через Active Directory")
    @Test(description = "Нет залогина при вводе некорректного пароля и логина", groups = {REGRESSION_ADMIN, ADMIN_KEYCLOAK})
    public void noAuthUnexistLoginViaActiveDirectory() {
        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail("wrong");
        activeDirectory().fillPassword("wrong");
        activeDirectory().clickOnLoginButton();
        activeDirectory().checkWrongLoginFormatAlertVisible();
    }

    @TmsLink("32")
    @Story("Неуспешная авторизация через Active Directory")
    @Test(description = "Нет залогина при вводе некорректного пароля и логина", groups = PROD_ADMIN_SMOKE)
    public void noAuthUnexistLoginViaActiveDirectoryProd() {
        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail("wrong");
        activeDirectory().fillPassword("wrong");
        activeDirectory().clickOnLoginButton();
        activeDirectory().checkWrongUserDataAlertVisible();
    }

    @TmsLinks(value = {@TmsLink("33"), @TmsLink("34")})
    @Story("Неуспешная авторизация через Active Directory")
    @Test(description = "Нет залогина при вводе несуществующего логина или пароля", groups = {REGRESSION_ADMIN, ADMIN_KEYCLOAK, PROD_ADMIN_SMOKE})
    public void noAuthWrongLoginViaActiveDirectory() {
        UserData activeDirectoryUser = UserManager.getDefaultActiveDirectoryUser();

        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail("wrong@sbmt.team");
        activeDirectory().fillPassword(activeDirectoryUser.getPassword());
        activeDirectory().clickOnLoginButton();
        activeDirectory().checkWrongUserDataAlertVisible();

        activeDirectory().fillMail(activeDirectoryUser.getEmail());
        activeDirectory().fillPassword("wrong");
        activeDirectory().clickOnLoginButton();
        activeDirectory().checkWrongUserDataAlertVisible();
    }
}
