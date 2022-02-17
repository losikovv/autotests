package ru.instamart.test.reforged.business;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.kraken.config.EnvironmentProperties.*;
import static ru.instamart.reforged.business.page.BusinessRouter.b2cShop;
import static ru.instamart.reforged.business.page.BusinessRouter.shop;

@Epic("SMBUSINESS UI")
@Feature("Основные тесты корзины")
public final class ShoppingCartTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(261)
    @Test(description = "Отображение TOTAL НДС", groups = {"smoke", "regression"})
    public void testTotalVatDisplayed() {
        var userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, DEFAULT_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkTotalVatDisplayed();
    }

    @CaseId(263)
    @Test(description = "Отображение TOTAL НДС мультизаказ", groups = {"smoke", "regression"})
    public void testTotalVatDisplayedMultiply() {
        var userData = UserManager.getQaUser();
        helper.dropAndFillCartMultiple(
                userData,
                RestAddresses.Moscow.defaultAddress(),
                DEFAULT_METRO_MOSCOW_SID,
                DEFAULT_AUCHAN_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkTotalVatDisplayed();
    }

    @CaseId(726)
    @Test(description = "Перенос корзины с B2C на B2B витрину", groups = {"smoke", "regression"})
    public void testTransferCartB2CToB2B() {
        var userData = UserManager.getQaUser();

        b2cShop().goToPage();
        b2cShop().interactHeader().clickToLogin();
        b2cShop().interactAuthModal().authViaPhone(userData);
        b2cShop().interactHeader().checkProfileButtonVisible();
        b2cShop().interactHeader().clickToSelectAddress();
        b2cShop().interactAddress().checkYmapsReady();
        b2cShop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        b2cShop().interactAddress().selectFirstAddress();
        b2cShop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        b2cShop().interactAddress().clickOnSave();
        b2cShop().interactAddress().checkAddressModalIsNotVisible();
        b2cShop().interactHeader().checkEnteredAddressIsVisible();

        b2cShop().plusFirstItemToCart();
        b2cShop().interactHeader().checkCartNotificationIsVisible();
        b2cShop().interactHeader().clickBuyForBusiness();
        b2cShop().interactHeader().interactTransferCartModal().checkTransferCartModalDisplayed();
        b2cShop().interactHeader().interactTransferCartModal().confirm();

        shop().switchToNextWindow();
        shop().refreshWithBasicAuth();
        shop().interactHeader().interactTransferCartModal().checkTransferSuccessDisplayed();
        shop().interactHeader().interactTransferCartModal().confirm();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
    }

    @CaseId(729)
    @Test(description = "Перенос корзины при заполненной корзине на B2B", groups = {"smoke", "regression"})
    public void testTransferCartB2CToB2BNegative() {
        var userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, DEFAULT_SID);

        //Для инициализации наполненной корзины на B2B витрине необходимо залогиниться
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        b2cShop().goToPage();
        b2cShop().interactHeader().clickToLogin();
        b2cShop().interactAuthModal().authViaPhone(userData);
        b2cShop().interactHeader().checkProfileButtonVisible();
        b2cShop().interactHeader().clickToSelectAddress();
        b2cShop().interactAddress().checkYmapsReady();
        b2cShop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        b2cShop().interactAddress().selectFirstAddress();
        b2cShop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        b2cShop().interactAddress().clickOnSave();
        b2cShop().interactAddress().checkAddressModalIsNotVisible();
        b2cShop().interactHeader().checkEnteredAddressIsVisible();

        b2cShop().plusFirstItemToCart();
        b2cShop().interactHeader().checkCartNotificationIsVisible();
        b2cShop().interactHeader().clickBuyForBusiness();
        b2cShop().interactHeader().interactTransferCartModal().checkTransferCartModalDisplayed();
        b2cShop().interactHeader().interactTransferCartModal().confirm();

        shop().switchToNextWindow();
        shop().interactHeader().interactTransferCartModal().checkTransferNegativeDisplayed();
    }
}
