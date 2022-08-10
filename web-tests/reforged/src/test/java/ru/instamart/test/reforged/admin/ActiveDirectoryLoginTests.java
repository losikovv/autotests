package ru.instamart.test.reforged.admin;

import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.active_directory.ActiveDirectoryRouter.activeDirectory;
import static ru.instamart.reforged.admin.AdminRout.login;
import static ru.instamart.reforged.admin.AdminRout.main;

public class ActiveDirectoryLoginTests {

    @CaseId(31)
    @Story("Успешная авторизация через Active Directory")
    @Test(description = "Успешная аутентификация через Active Directory в админке STF", groups = {"regression", "admin-keycloak"})
    public void successLoginViaActiveDirectory() {
        UserData activeDirectoryUser = UserManager.getActiveDirectoryUser();

        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail(activeDirectoryUser.getEmail());
        activeDirectory().fillPassword(activeDirectoryUser.getPassword());
        activeDirectory().clickOnLoginButton();

        main().interactAuthoredHeader().checkAdminAuth();
    }

    @CaseId(32)
    @Story("Успешная авторизация через Active Directory")
    @Test(description = "Успешная аутентификация через Active Directory в админке STF", groups = {"regression", "admin-keycloak"})
    public void noAuthUnexistLoginViaActiveDirectory() {
        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail("wrong");
        activeDirectory().fillPassword("wrong");
        activeDirectory().clickOnLoginButton();
        activeDirectory().checkWrongLoginFormatAlertVisible();

        main().interactAuthoredHeader().checkIsNotAuth();
    }

    @CaseId(33)
    @Story("Успешная авторизация через Active Directory")
    @Test(description = "Успешная аутентификация через Active Directory в админке STF", groups = {"regression", "admin-keycloak"})
    public void noAuthWrongLoginViaActiveDirectory() {
        UserData activeDirectoryUser = UserManager.getActiveDirectoryUser();

        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail("wrong@sbmt.team");
        activeDirectory().fillPassword(activeDirectoryUser.getPassword());
        activeDirectory().clickOnLoginButton();
        activeDirectory().checkWrongUserDataAlertVisible();

        main().interactAuthoredHeader().checkIsNotAuth();
    }

    @CaseId(34)
    @Story("Успешная авторизация через Active Directory")
    @Test(description = "Успешная аутентификация через Active Directory в админке STF", groups = {"regression", "admin-keycloak"})
    public void noAuthWrongPasswordViaActiveDirectory() {
        UserData activeDirectoryUser = UserManager.getActiveDirectoryUser();

        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail(activeDirectoryUser.getEmail());
        activeDirectory().fillPassword("wrong");
        activeDirectory().clickOnLoginButton();
        activeDirectory().checkWrongUserDataAlertVisible();

        main().interactAuthoredHeader().checkIsNotAuth();
    }
}
