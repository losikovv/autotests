package ru.instamart.test.reforged.stf.user;

import io.qameta.allure.*;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.kraken.config.CoreProperties.DEFAULT_SMS;
import static ru.instamart.reforged.stf.page.StfRouter.home;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Деавторизация пользователя")
public final class UserLogoutTests extends BaseTest {

    @CaseId(1473)
    @Story("Позитивный кейс")
    @Test(description = "Тест успешной быстрой деавторизации",
            groups = {
                    "metro-acceptance", "metro-regression", "sbermarket-Ui-smoke"
            }
    )
    public void successQuickLogout() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().fillPhone(Generate.phoneNumber());
        shop().interactAuthModal().sendSms();
        shop().interactAuthModal().fillSMS(DEFAULT_SMS);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clearSessionLogout();
        home().goToPage();
        home().checkLoginButtonIsVisible();
    }

    @CaseId(1474)
    @Story("Позитивный кейс")
    @Test(description = "Тест успешной деавторизации",
            groups = {
                    "metro-acceptance", "metro-regression", "sbermarket-Ui-smoke"
            }
    )
    public void successManualLogout() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().fillPhone(Generate.phoneNumber());
        shop().interactAuthModal().sendSms();
        shop().interactAuthModal().fillSMS(DEFAULT_SMS);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToLogout();
        home().checkLoginButtonIsVisible();
    }

    @Issue("ATST-645")
    @Flaky
    @CaseId(1475)
    @Story("Позитивный кейс")
    @Test(description = "Тест сброса адреса доставки и корзины после деавторизации",
            groups = {
                    "metro-acceptance", "metro-regression", "sbermarket-Ui-smoke"
            }
    )
    public void noShipAddressAndEmptyCartAfterLogout() {
        final ApiHelper apiHelper = new ApiHelper();
        final UserData userData = UserManager.getUser();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().fillPhone(userData.getPhone());
        shop().interactAuthModal().sendSms();
        shop().interactAuthModal().fillSMS(DEFAULT_SMS);
        shop().interactHeader().checkProfileButtonVisible();

        apiHelper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

        shop().goToShopPageWithDefaultSid();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
        shop().interactCart().closeCart();
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToLogout();
        home().checkLoginButtonIsVisible();
        shop().goToPage();
        shop().interactHeader().checkEnteredAddressNotVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartEmpty();
    }
}
