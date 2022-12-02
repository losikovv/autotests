package ru.instamart.test.reforged.stf_prod.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.BasicProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.sber_id_auth.SberIdPageRouter.sberId;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Авторизация")
public final class UserAuthorisationTests {

    @CaseId(2735)
    @Story("Авторизация через VK")
    @Issue("B2C-11803")
    @Test(description = "Тест успешной авторизация через ВКонтакте", groups = {STF_PROD_S})
    public void successRegWithVkontakte() {
        final var vkUser = UserManager.getNewVkUser();

        shop().goToPage();
        shop().checkRequestsWasLoad();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().authViaVk();

        shop().interactAuthModal().interactAuthVkWindow().switchToNextWindow();
        shop().interactAuthModal().interactAuthVkWindow().setEmail(vkUser.getEmail());
        shop().interactAuthModal().interactAuthVkWindow().setPassword(vkUser.getPassword());
        shop().interactAuthModal().interactAuthVkWindow().clickToLogin();
        shop().interactAuthModal().interactAuthVkWindow().switchToFirstWindow();
        shop().interactAuthModal().checkModalIsNotVisible();

        shop().checkRequestsWasLoad();
        shop().interactAuthModal().checkModalConfirmPhoneIsVisible();
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(1460)
    @Story("Авторизация через Mail.ru")
    @Issue("B2C-11803")
    @Test(description = "Тест успешной авторизация через MailRu", groups = {STF_PROD_S})
    public void successRegWithMailRu() {
        shop().goToPage();
        shop().checkRequestsWasLoad();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().authViaMail();

        shop().interactAuthModal().interactAuthMailWindow().switchToNextWindow();
        shop().interactAuthModal().interactAuthMailWindow()
                .fillName(UserManager.getDefaultMailRuUser().getEmail());
        shop().interactAuthModal().interactAuthMailWindow()
                .clickToEnterPassword();
        shop().interactAuthModal().interactAuthMailWindow()
                .fillPassword(UserManager.getDefaultMailRuUser().getPassword());
        shop().interactAuthModal().interactAuthMailWindow().clickToSubmit();
        shop().interactAuthModal().interactAuthMailWindow().switchToFirstWindow();
        shop().interactAuthModal().checkModalIsNotVisible();

        shop().checkRequestsWasLoad();
        shop().interactAuthModal().checkModalConfirmPhoneIsVisible();
        shop().interactHeader().checkProfileButtonVisible();
    }

    //@CaseId(1459)
    @CaseId(3522)
    @Story("Авторизация через SberID")
    @Test(description = "Тест перехода на сайт Sber ID", groups = {STF_PROD_S})
    public void checkCorrectTransitionToSberIdSite() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().checkSberIdIsVisible();
        shop().interactAuthModal().authViaSberId();

        sberId().checkPhoneInputVisible();
        sberId().checkPageContains(BasicProperties.SBER_ID_URL);
    }
}
