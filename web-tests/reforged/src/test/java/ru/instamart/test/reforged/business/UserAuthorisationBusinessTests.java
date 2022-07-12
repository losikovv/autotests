package ru.instamart.test.reforged.business;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Run;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.CookieProvider;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.business.page.BusinessRouter.business;

@Epic("SMBUSINESS UI")
@Feature("Регистрация и авторизация B2B")
public final class UserAuthorisationBusinessTests {

    @CaseId(230)
    @Story("Авторизация")
    @Test(description = "Вход по мобильному телефону (B2B)", groups = {"smoke", "regression"})
    public void successAuthOnMainPage() {
        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().checkModalIsVisible();

        business().interactAuthModal().authViaPhone(UserManager.getQaUser());
        business().interactHeader().checkProfileButtonVisible();
    }

    @Run(onServer = Server.PREPROD)
    @Issue("DEVB2B-1977")
    @CaseId(231)
    @Story("Авторизация")
    @Test(enabled = false, description = "Вход по СберБизнес ID (B2B)", groups = {"smoke", "regression"})
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
