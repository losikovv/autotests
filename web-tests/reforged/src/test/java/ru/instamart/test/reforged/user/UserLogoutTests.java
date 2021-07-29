package ru.instamart.test.reforged.user;

import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.home;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

public class UserLogoutTests extends BaseTest {

    @CaseId(1473)
    @Story("Позитивный кейс")
    @Test(  description = "Тест успешной быстрой деавторизации",
            groups = {
                    "metro-acceptance", "metro-regression","sbermarket-Ui-smoke"
            }
    )
    public void successQuickLogout() {
        home().openSitePage(Config.DEFAULT_RETAILER);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().fillPhone(Generate.phoneNumber());
        shop().interactAuthModal().sendSms();
        shop().interactAuthModal().fillSMS(Config.DEFAULT_SMS);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactUser().clearSessionLogout();
        home().goToPage();
        home().checkLoginButtonIsVisible();
    }

    @CaseId(1474)
    @Story("Позитивный кейс")
    @Test(  description = "Тест успешной деавторизации",
            groups = {
                    "metro-acceptance", "metro-regression","sbermarket-Ui-smoke"
            }
    )
    public void successManualLogout() {
        home().openSitePage(Config.DEFAULT_RETAILER);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().fillPhone(Generate.phoneNumber());
        shop().interactAuthModal().sendSms();
        shop().interactAuthModal().fillSMS(Config.DEFAULT_SMS);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToLogout();
        home().goToPage();
        home().checkLoginButtonIsVisible();
    }

    @Skip
    @CaseId(1475)
    @Story("Позитивный кейс")
    @Test(  description = "Тест сброса адреса доставки и корзины после деавторизации",
            groups = {
                    "metro-acceptance", "metro-regression","sbermarket-Ui-smoke"
            }
    )
    public void noShipAddressAndEmptyCartAfterLogout() {

    }
}
