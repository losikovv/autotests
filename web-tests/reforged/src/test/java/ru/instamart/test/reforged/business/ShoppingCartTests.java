package ru.instamart.test.reforged.business;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.annotation.CookieProvider;

import static ru.instamart.reforged.Group.REGRESSION_BUSINESS;
import static ru.instamart.reforged.Group.SMOKE_B2B;
import static ru.instamart.reforged.business.page.BusinessRouter.*;
import static ru.instamart.reforged.core.config.UiProperties.*;

@Epic("SMBUSINESS UI")
@Feature("Основные тесты корзины")
public final class ShoppingCartTests {

    private final ApiHelper helper = new ApiHelper();

    @Skip
    //TODO: по НДС можно считать неактуальными, ребята из этой команды опишут смоук набор, учитывая новую логику НДС
    @TmsLink("261")
    @Test(description = "Отображение TOTAL НДС", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void testTotalVatDisplayed() {
        var company = JuridicalData.juridical();
        var userData = UserManager.getQaUser();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());
        helper.dropAndFillCart(userData, DEFAULT_SID);

        shop().goToPage(DEFAULT_SID);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactAuthModal().checkModalIsNotVisible();
        business().interactHeaderMultisearch().checkUserActionsButtonVisible();

        business().interactHeaderMultisearch().clickToCart();
        business().interactHeaderMultisearch().interactCart().checkCartOpen();
        business().interactHeaderMultisearch().interactCart().checkTotalVatDisplayed();
    }

    @Skip
    //TODO: по НДС можно считать неактуальными, ребята из этой команды опишут смоук набор, учитывая новую логику НДС
    @TmsLink("263")
    @Test(description = "Отображение TOTAL НДС мультизаказ", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void testTotalVatDisplayedMultiply() {
        var company = JuridicalData.juridical();
        var userData = UserManager.getQaUser();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());
        helper.dropAndFillCartMultiple(userData, RestAddresses.Moscow.defaultAddress(), DEFAULT_LENTA_SID, DEFAULT_AUCHAN_SID);

        shop().goToPage(DEFAULT_SID);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactAuthModal().checkModalIsNotVisible();
        business().interactHeaderMultisearch().checkUserActionsButtonVisible();

        business().interactHeaderMultisearch().clickToCart();
        business().interactHeaderMultisearch().interactCart().checkCartOpen();
        business().interactHeaderMultisearch().interactCart().checkTotalVatDisplayed();
    }

    //TODO: Нужно подобрать ритейлеров
    @TmsLink("725")
    //TODO Переход с STF на Business при текущей схеме невозможен см коммент https://jira.sbmt.io/browse/ATST-2251
    @Issue("ATST-2251")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_GUEST"})
    @Test(enabled = false, description = "Перенос корзины при отсутствующем ритейлере на B2B", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void testTransferCartNoSuchRetailer() {
        b2cShop().goToPage(DEFAULT_SID);
        b2cShop().interactHeader().clickToLogin();
        b2cShop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        b2cShop().interactHeader().checkProfileButtonVisible();

        b2cShop().interactHeader().clickToSelectAddress();
        b2cShop().interactAddressLarge().checkYmapsReady();

        b2cShop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        b2cShop().interactAddressLarge().selectFirstAddress();
        b2cShop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        b2cShop().interactAddressLarge().clickSave();
        b2cShop().interactAddressLarge().checkAddressModalIsNotVisible();
        b2cShop().interactHeader().checkEnteredAddressIsVisible();

        b2cShop().plusFirstItemToCart();
        b2cShop().interactHeader().checkCartNotificationIsVisible();
        b2cShop().interactHeader().checkCartNotificationIsNotVisible();
        b2cShop().interactHeader().clickBuyForBusiness();
        b2cShop().interactHeader().interactTransferCartModal().checkTransferCartModalDisplayed();
        b2cShop().interactHeader().interactTransferCartModal().confirm();

        b2cShop().addCookie(CookieFactory.FORWARD_FEATURE_B2B);
        shop().switchToNextWindow();
        shop().waitPageLoad();
        shop().refreshWithBasicAuth();
        shop().interactHeader().interactTransferCartModal().checkTransferNegativeDisplayed();
        shop().interactHeader().interactTransferCartModal().checkErrorTextContains("Нужного магазина нет на витрине для бизнеса.");
    }

    @TmsLinks({@TmsLink("726"), @TmsLink("729")})
    //TODO Переход с STF на Business при текущей схеме невозможен см коммент https://jira.sbmt.io/browse/ATST-2251
    @Issue("ATST-2251")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_GUEST"})
    @Test(description = "Перенос корзины с B2C на B2B витрину", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void testTransferCartB2CToB2B() {
        b2cShop().goToPage(DEFAULT_SID);
        b2cShop().interactHeader().clickToLogin();
        b2cShop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        b2cShop().interactHeader().checkProfileButtonVisible();

        b2cShop().interactHeader().clickToSelectAddress();
        b2cShop().interactAddressLarge().checkYmapsReady();
        b2cShop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        b2cShop().interactAddressLarge().selectFirstAddress();
        b2cShop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        b2cShop().interactAddressLarge().clickSave();
        b2cShop().interactAddressLarge().checkAddressModalIsNotVisible();
        b2cShop().interactHeader().checkEnteredAddressIsVisible();

        b2cShop().plusFirstItemToCart();
        b2cShop().interactHeader().checkCartNotificationIsVisible();
        b2cShop().interactHeader().checkCartNotificationIsNotVisible();
        b2cShop().interactHeader().clickBuyForBusiness();
        b2cShop().interactHeader().interactTransferCartModal().checkTransferCartModalDisplayed();
        b2cShop().interactHeader().interactTransferCartModal().confirm();

        b2cShop().addCookie(CookieFactory.FORWARD_FEATURE_B2B);
        shop().switchToNextWindow();
        shop().waitPageLoad();
        shop().refreshWithBasicAuth();
        shop().interactHeader().interactTransferCartModal().checkTransferSuccessDisplayed();
        shop().interactHeader().interactTransferCartModal().confirm();

        shop().goToPage(DEFAULT_SID);
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartNotEmpty();
    }

    //TODO: Нет мультиретейлеров на 15 стейдже, нужно подбирать или доделывать
    @TmsLink("730")
    //TODO Переход с STF на Business при текущей схеме невозможен см коммент https://jira.sbmt.io/browse/ATST-2251
    @Issue("ATST-2251")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_GUEST"})
    @Test(enabled = false, description = "Перенос мультиритейлерной корзины с B2C на B2B витрину", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void testTransferCartB2CToB2BMultiply() {
        var userData = UserManager.getQaUser();

        b2cShop().goToPage(DEFAULT_SID);
        b2cShop().interactHeader().clickToLogin();
        b2cShop().interactAuthModal().authViaPhone(userData);
        b2cShop().interactHeader().checkProfileButtonVisible();
        b2cShop().interactHeader().clickToSelectAddress();
        b2cShop().interactAddressLarge().checkYmapsReady();

        b2cShop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        b2cShop().interactAddressLarge().selectFirstAddress();
        b2cShop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        b2cShop().interactAddressLarge().clickSave();
        b2cShop().interactAddressLarge().checkAddressModalIsNotVisible();
        b2cShop().interactHeader().checkEnteredAddressIsVisible();

        b2cShop().plusFirstItemToCart();
        b2cShop().interactHeader().checkCartNotificationIsVisible();
        b2cShop().interactHeader().checkCartNotificationIsNotVisible();

        b2cShop().interactHeader().clickToStoreSelector();
        b2cHome().checkDeliveryStoresContainerVisible();
        b2cHome().clickOnStoreWithSid(DEFAULT_AUCHAN_SID);
        b2cShop().interactHeader().checkEnteredAddressIsVisible();

        b2cShop().plusFirstItemToCart();
        b2cShop().interactHeader().checkCartNotificationIsVisible();
        b2cShop().interactHeader().checkCartNotificationIsNotVisible();

        b2cShop().interactHeader().clickBuyForBusiness();
        b2cShop().interactHeader().interactTransferCartModal().checkTransferCartModalDisplayed();
        b2cShop().interactHeader().interactTransferCartModal().confirm();

        b2cShop().addCookie(CookieFactory.FORWARD_FEATURE_B2B);
        shop().switchToNextWindow();
        shop().waitPageLoad();
        shop().refreshWithBasicAuth();
        shop().interactHeader().interactTransferCartModal().checkTransferSuccessDisplayed();
        shop().interactHeader().interactTransferCartModal().confirm();

        shop().goToPage(DEFAULT_SID);
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
        shop().interactCart().checkItemsCount(2);
        shop().interactCart().checkRetailersCount(2);
    }

    @TmsLink("731")
    @Test(description = "Изменение количества товаров в корзине вводом числа", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void testChangeItemCountFromKeyboard() {
        var company = JuridicalData.juridical();
        var userData = UserManager.getQaUser();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());
        helper.dropAndFillCart(userData, DEFAULT_SID);

        shop().goToPage(DEFAULT_SID);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactAuthModal().checkModalIsNotVisible();
        business().interactHeaderMultisearch().checkUserActionsButtonVisible();

        business().interactHeaderMultisearch().clickToCart();
        business().interactHeaderMultisearch().interactCart().checkCartOpen();
        business().interactHeaderMultisearch().interactCart().setItemCount("200");

        business().interactHeaderMultisearch().interactCart().checkDisplayedItemCount("199 шт");
    }
}
