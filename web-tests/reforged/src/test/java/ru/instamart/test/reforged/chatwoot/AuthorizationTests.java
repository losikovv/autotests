package ru.instamart.test.reforged.chatwoot;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.chatwoot.user.UserData;
import ru.instamart.kraken.data.chatwoot.user.UserManager;

import static ru.instamart.reforged.Group.CHATWOOT;
import static ru.instamart.reforged.chatwoot.ChatwootRouter.dialogs;
import static ru.instamart.reforged.chatwoot.ChatwootRouter.login;

@Epic("Chatwoot")
@Feature("Авторизация")
public final class AuthorizationTests {

    @TmsLink("66")
    @Test(description = "Авторизация в чатвут зарегистрированного пользователя", groups = {CHATWOOT})
    public void authorizationTest() {
        UserData userData = UserManager.getOperatorUser();

        login().goToPage();
        login().waitPageLoad();

        login().fillEmail(userData.getEmail());
        login().fillPassword(userData.getPassword());
        login().clickSubmit();

        dialogs().waitPageLoad();
        dialogs().interactLeftMenu().checkAccountMenuButtonDisplayed();

        //dialogs().interactSecondaryMenu().checkMenuItemActive("Диалоги");

        dialogs().checkActiveGroupTab("Мои");

        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().checkActiveUserState("Оффлайн");
    }

    @TmsLink("71")
    @Test(description = "Разлогин из чатвута", groups = {CHATWOOT})
    public void logoutTest() {
        UserData userData = UserManager.getOperatorUser();

        login().goToPage();
        login().waitPageLoad();

        login().fillEmail(userData.getEmail());
        login().fillPassword(userData.getPassword());

        login().clickSubmit();

        dialogs().waitPageLoad();
        dialogs().interactLeftMenu().checkAccountMenuButtonDisplayed();

        dialogs().interactLeftMenu().clickAccountMenuButton();
        dialogs().interactLeftMenu().interactAccountMenu().checkAccountMenuDisplayed();
        dialogs().interactLeftMenu().interactAccountMenu().clickLogout();

        login().waitPageLoad();
        login().checkEmailInputValue("");
        login().checkPasswordInputValue("");
        login().checkSubmitDisabled();
    }

    @TmsLink("124")
    @Test(description = "Невозможность авторизации с неверным паролем", groups = {CHATWOOT})
    public void authorizationTestNegative() {
        UserData userData = UserManager.getOperatorUser();

        login().goToPage();
        login().waitPageLoad();

        login().fillEmail(userData.getEmail());
        login().fillPassword(Generate.digitalString(5));
        login().clickSubmit();

        login().checkSnackbarVisible();
        login().checkSnackbarText("Неверные логин или пароль. Пожалуйста, попробуйте еще раз.");
        login().checkPageContains("app/login");
    }
}


