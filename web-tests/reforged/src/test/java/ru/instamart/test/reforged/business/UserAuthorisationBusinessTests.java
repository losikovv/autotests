package ru.instamart.test.reforged.business;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.reforged.Group.REGRESSION_BUSINESS;
import static ru.instamart.reforged.Group.SMOKE_B2B;
import static ru.instamart.reforged.business.page.BusinessRouter.business;

@Epic("SMBUSINESS UI")
@Feature("Регистрация и авторизация B2B")
public final class UserAuthorisationBusinessTests {

    @TmsLink("230")
    @Story("Авторизация")
    @Test(description = "Вход по мобильному телефону (B2B)", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void successAuthOnMainPage() {
        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().checkModalIsVisible();

        business().interactAuthModal().authViaPhone(UserManager.getQaUser());
        business().interactHeaderMultisearch().checkUserActionsButtonVisible();
    }

    @Issue("DEVB2B-1977")
    @TmsLink("231")
    @Story("Авторизация")
    @Test(description = "Вход по СберБизнес ID (B2B)", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void successRegWithSberBusinessID() {
        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().checkModalIsVisible();

        business().interactAuthModal().clickAuthViaSberBusinessId();
        business().interactAuthModal().interactAuthSberBusinessIdPage().authViaSberBusinessID();
        business().waitPageLoad();
        business().interactAuthModal().checkModalIsNotVisible();
        business().interactHeader().checkProfileButtonVisible();
    }
}
