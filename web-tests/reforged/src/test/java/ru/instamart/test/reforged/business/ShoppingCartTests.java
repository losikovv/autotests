package ru.instamart.test.reforged.business;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.kraken.config.EnvironmentProperties.*;
import static ru.instamart.reforged.business.page.BusinessRouter.*;

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
        var company = JuridicalData.juridical();
        var userData = UserManager.getQaUser();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());
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

    @CaseId(725)
    @Test(description = "Перенос корзины при отсутствующем ритейлере на B2B", groups = {"smoke", "regression"})
    public void testTransferCartNoSuchRetailer() {
        var userData = UserManager.getQaUser();

        b2cShop().goToPageFromTenant(ShopUrl.AZBUKAVKUSA, true);
        b2cShop().interactHeader().clickToLogin();
        b2cShop().interactAuthModal().authViaPhone(userData);
        b2cShop().interactHeader().checkProfileButtonVisible();

        b2cShop().interactHeader().clickToSelectAddress();
        b2cShop().interactAddressLarge().checkYmapsReady();

        b2cShop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        b2cShop().interactAddressLarge().selectFirstAddress();
        b2cShop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        b2cShop().interactAddressLarge().clickSave();
        b2cShop().interactAddressLarge().checkAddressModalNotVisible();
        b2cShop().interactHeader().checkEnteredAddressIsVisible();

        b2cShop().plusItemToCart("1", "0");
        b2cShop().interactHeader().checkCartNotificationIsVisible();
        b2cShop().interactHeader().checkCartNotificationIsNotVisible();
        b2cShop().interactHeader().clickBuyForBusiness();
        b2cShop().interactHeader().interactTransferCartModal().checkTransferCartModalDisplayed();
        b2cShop().interactHeader().interactTransferCartModal().confirm();

        shop().switchToNextWindow();
        shop().refreshWithBasicAuth();
        shop().interactHeader().interactTransferCartModal().checkTransferNegativeDisplayed();
        shop().interactHeader().interactTransferCartModal().checkErrorTextContains("Нужного магазина нет на витрине для бизнеса.");
    }

    @CaseIDs({@CaseId(726), @CaseId(729)})
    @Test(description = "Перенос корзины с B2C на B2B витрину", groups = {"smoke", "regression"})
    public void testTransferCartB2CToB2B() {
        var userData = UserManager.getQaUser();

        b2cShop().goToPageFromTenant();
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

        b2cShop().plusItemToCart("1", "0");
        b2cShop().interactHeader().checkCartNotificationIsVisible();
        b2cShop().interactHeader().checkCartNotificationIsNotVisible();
        b2cShop().interactHeader().clickBuyForBusiness();
        b2cShop().interactHeader().interactTransferCartModal().checkTransferCartModalDisplayed();
        b2cShop().interactHeader().interactTransferCartModal().confirm();

        shop().switchToNextWindow();
        shop().refreshWithBasicAuth();
        shop().interactHeader().interactTransferCartModal().checkTransferSuccessDisplayed();
        shop().interactHeader().interactTransferCartModal().confirm();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartNotEmpty();

    }

    @CaseId(730)
    @Test(description = "Перенос мультиритейлерной корзины с B2C на B2B витрину", groups = {"smoke", "regression"})
    public void testTransferCartB2CToB2BMultiply() {
        var userData = UserManager.getQaUser();

        b2cShop().goToPageFromTenant(true);
        b2cShop().interactHeader().clickToLogin();
        b2cShop().interactAuthModal().authViaPhone(userData);
        b2cShop().interactHeader().checkProfileButtonVisible();
        b2cShop().interactHeader().clickToSelectAddress();
        b2cShop().interactAddressLarge().checkYmapsReady();

        b2cShop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        b2cShop().interactAddressLarge().selectFirstAddress();
        b2cShop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        b2cShop().interactAddressLarge().clickSave();
        b2cShop().interactAddressLarge().checkAddressModalNotVisible();
        b2cShop().interactHeader().checkEnteredAddressIsVisible();

        b2cShop().plusItemToCart("1", "0");
        b2cShop().interactHeader().checkCartNotificationIsVisible();
        b2cShop().interactHeader().checkCartNotificationIsNotVisible();

        b2cShop().interactHeader().clickToStoreSelector();
        b2cHome().checkDeliveryStoresContainerVisible();
        b2cHome().clickOnStoreWithSid(DEFAULT_AUCHAN_SID);
        b2cShop().interactHeader().checkEnteredAddressIsVisible();

        b2cShop().plusItemToCart("1", "0");
        b2cShop().interactHeader().checkCartNotificationIsVisible();
        b2cShop().interactHeader().checkCartNotificationIsNotVisible();

        b2cShop().interactHeader().clickBuyForBusiness();
        b2cShop().interactHeader().interactTransferCartModal().checkTransferCartModalDisplayed();
        b2cShop().interactHeader().interactTransferCartModal().confirm();

        shop().switchToNextWindow();
        shop().refreshWithBasicAuth();
        shop().interactHeader().interactTransferCartModal().checkTransferSuccessDisplayed();
        shop().interactHeader().interactTransferCartModal().confirm();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
        shop().interactCart().checkItemsCount(2);
        shop().interactCart().checkRetailersCount(2);
    }

    @CaseId(731)
    @Test(description = "Изменение количества товаров в корзине вводом числа", groups = {"smoke", "regression"})
    public void testChangeItemCountFromKeyboard() {
        var company = JuridicalData.juridical();
        var userData = UserManager.getQaUser();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());
        helper.dropAndFillCart(userData, DEFAULT_METRO_MOSCOW_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().setItemCount("200");

        shop().interactCart().checkDisplayedItemCount("199 шт");
    }
}
