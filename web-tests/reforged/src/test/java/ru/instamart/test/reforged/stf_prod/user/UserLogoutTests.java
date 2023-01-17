package ru.instamart.test.reforged.stf_prod.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;
import io.qameta.allure.TmsLink;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.stf.page.StfRouter.home;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Деавторизация пользователя")
public final class UserLogoutTests {

    @TmsLink("1473")
    @Story("Позитивный кейс")
    @Test(description = "Тест успешной быстрой деавторизации", groups = {STF_PROD_S})
    public void successQuickLogout() {
        final var userData = UserManager.getQaUser();

        shop().goToPageProd();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clearSessionLogoutProd();
        shop().interactHeader().checkLoginIsVisible();

        home().goToPage();
        home().checkLoginButtonIsVisible();
    }

    @TmsLink("1474")
    @Story("Позитивный кейс")
    @Test(description = "Тест успешной деавторизации", groups = {STF_PROD_S})
    public void successManualLogout() {
        shop().goToPageProd();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToLogout();
        home().checkLoginButtonIsVisible();
    }

    @TmsLink("2548")
    @Story("Позитивный кейс")
    @Test(description = "Тест сброса адреса доставки и корзины после деавторизации", groups = {STF_PROD_S})
    public void noShipAddressAndEmptyCartAfterLogout() {
        final var apiHelper = new ApiHelper();
        final var userData = UserManager.getQaUser();
        apiHelper.dropAndFillCart(userData, UiProperties.DEFAULT_SID);

        shop().goToPageProd();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().goToPageProd();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
        shop().interactCart().closeCart();
        shop().interactCart().checkCartClose();
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().checkAccountMenuVisible();
        shop().interactHeader().interactAccountMenu().clickToLogout();

        home().checkLoginButtonIsVisible();

        shop().goToPageProd();
        shop().interactHeader().checkEnteredAddressNotVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartEmpty();
    }
}
