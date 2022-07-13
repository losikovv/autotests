package ru.instamart.test.reforged.stf.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.stf.page.StfRouter.home;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Деавторизация пользователя")
public final class UserLogoutTests {

    @CaseId(1473)
    @Story("Позитивный кейс")
    @Test(description = "Тест успешной быстрой деавторизации", groups = "regression")
    public void successQuickLogout() {
        UserData userData = UserManager.getQaUser();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clearSessionLogout(userData);
        shop().interactHeader().checkLoginIsVisible();
        home().goToPage();
        home().checkLoginButtonIsVisible();
    }

    @CaseId(1474)
    @Story("Позитивный кейс")
    @Test(description = "Тест успешной деавторизации", groups = {"production", "regression", "smoke"})
    public void successManualLogout() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToLogout();
        home().checkLoginButtonIsVisible();
    }

    @CaseId(2548)
    @Story("Позитивный кейс")
    @Test(description = "Тест сброса адреса доставки и корзины после деавторизации", groups = "regression")
    public void noShipAddressAndEmptyCartAfterLogout() {
        final ApiHelper apiHelper = new ApiHelper();
        final UserData userData = UserManager.getQaUser();
        apiHelper.dropAndFillCart(userData, UiProperties.DEFAULT_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
        shop().interactCart().closeCart();
        shop().interactCart().checkCartClose();
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().checkAccountMenuVisible();
        shop().interactHeader().interactAccountMenu().clickToLogout();

        home().checkLoginButtonIsVisible();

        shop().goToPage();
        shop().interactHeader().checkEnteredAddressNotVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartEmpty();
    }
}
