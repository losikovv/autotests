package ru.instamart.test.reforged.business;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.CookieProvider;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_AUCHAN_SID;
import static ru.instamart.reforged.business.page.BusinessRouter.*;

@Epic("SMBUSINESS UI")
@Feature("Основные тесты корзины")
public final class ShoppingCartTests {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(261)
    @CookieProvider(cookieFactory = CookieFactory.class, cookies = {"EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Отображение TOTAL НДС", groups = {"smoke", "regression"})
    public void testTotalVatDisplayed() {
        var company = JuridicalData.juridical();
        var userData = UserManager.getQaUser();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusFirstItemToCart();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkTotalVatDisplayed();
    }

    @CaseId(263)
    @CookieProvider(cookieFactory = CookieFactory.class, cookies = {"EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Отображение TOTAL НДС мультизаказ", groups = {"smoke", "regression"})
    public void testTotalVatDisplayedMultiply() {
        var company = JuridicalData.juridical();
        var userData = UserManager.getQaUser();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusFirstItemToCart();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage(ShopUrl.LENTA.getUrl());
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().plusFirstItemToCart();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkTotalVatDisplayed();
    }

    @CaseId(725)
    //TODO Переход с STF на Business при текущей схеме невозможен см коммент https://jira.sbmt.io/browse/ATST-2251
    @Test(enabled = false, description = "Перенос корзины при отсутствующем ритейлере на B2B", groups = {"smoke", "regression"})
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
    //TODO Переход с STF на Business при текущей схеме невозможен см коммент https://jira.sbmt.io/browse/ATST-2251
    @Test(enabled = false, description = "Перенос корзины с B2C на B2B витрину", groups = {"smoke", "regression"})
    public void testTransferCartB2CToB2B() {
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
    //TODO Переход с STF на Business при текущей схеме невозможен см коммент https://jira.sbmt.io/browse/ATST-2251
    @Test(enabled = false, description = "Перенос мультиритейлерной корзины с B2C на B2B витрину", groups = {"smoke", "regression"})
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
    @CookieProvider(cookieFactory = CookieFactory.class, cookies = {"EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Изменение количества товаров в корзине вводом числа", groups = {"smoke", "regression"})
    public void testChangeItemCountFromKeyboard() {
        var company = JuridicalData.juridical();
        var userData = UserManager.getQaUser();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusFirstItemToCart();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().setItemCount("200");

        shop().interactCart().checkDisplayedItemCount("199 шт");
    }
}
