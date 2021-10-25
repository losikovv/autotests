package ru.instamart.test.reforged.stf.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.kraken.config.CoreProperties.DEFAULT_SMS;
import static ru.instamart.reforged.stf.page.StfRouter.home;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Деавторизация пользователя")
public final class UserLogoutTests extends BaseTest {

    @CaseId(1473)
    @Story("Позитивный кейс")
    @Test(description = "Тест успешной быстрой деавторизации", groups = {"acceptance", "regression"})
    public void successQuickLogout() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getUser());
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clearSessionLogout();
        shop().interactHeader().checkLoginIsVisible();
        home().goToPage();
        home().checkLoginButtonIsVisible();
    }

    @CaseId(1474)
    @Story("Позитивный кейс")
    @Test(description = "Тест успешной деавторизации", groups = {"acceptance", "regression", "smoke"})
    public void successManualLogout() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getUser());
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToLogout();
        home().checkLoginButtonIsVisible();
    }

    @CaseId(1475)
    @Story("Позитивный кейс")
    @Test(description = "Тест сброса адреса доставки и корзины после деавторизации", groups = {"acceptance", "regression"})
    public void noShipAddressAndEmptyCartAfterLogout() {
        final ApiHelper apiHelper = new ApiHelper();
        final UserData userData = UserManager.getUser();
        apiHelper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

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
