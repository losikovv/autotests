package ru.instamart.test.reforged.business;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.reforged.Group.REGRESSION_BUSINESS;
import static ru.instamart.reforged.business.page.BusinessRouter.business;
import static ru.instamart.reforged.business.page.BusinessRouter.shop;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_SID;

@Epic("SMBUSINESS UI")
@Feature("Каталог B2B")
public final class ShoppingCatalogTests {

    private final ApiHelper helper = new ApiHelper();

    @Issue("B2C-9789")
    @TmsLink("302")
    @Test(description = "Добавление товара в корзину из плитки (+удаление)", groups = REGRESSION_BUSINESS)
    public void testAddedAndRemoveProductFromShop() {
        var company = JuridicalData.juridical();
        var userData = UserManager.getQaUser();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(userData);
        business().interactHeaderMultisearch().checkUserActionsButtonVisible();

        shop().goToPage(DEFAULT_SID);
        shop().interactHeader().checkProfileButtonVisible();

        shop().plusFirstItemToCart();
        shop().interactHeader().checkCartNotificationIsVisible();
        shop().interactHeader().checkCartItemsCountSpoilerIsVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartNotEmpty();
        shop().interactCart().closeCart();
        shop().interactCart().checkCartClose();

        shop().minusFirstItemFromCart();
        shop().checkMinusButtonIsNotVisible();
        shop().interactHeader().checkCartItemsCountSpoilerIsNotVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartEmpty();
    }

    @Issue("B2C-9789")
    @TmsLink("303")
    @Test(description = "Добавление товара в корзину из карточки товара (+удаление)", groups = REGRESSION_BUSINESS)
    public void testAddedAndRemoveProductFromProductCard() {
        var company = JuridicalData.juridical();
        var userData = UserManager.getQaUser();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(userData);
        business().interactHeaderMultisearch().checkUserActionsButtonVisible();

        shop().goToPage(DEFAULT_SID);
        shop().interactHeader().checkProfileButtonVisible();

        shop().openFirstProductCard();
        shop().interactProductCard().checkProductCardVisible();
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().clickOnClose();
        shop().interactProductCard().checkProductCardIsNotVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartNotEmpty();
        shop().interactCart().closeCart();
        shop().interactCart().checkCartClose();

        shop().openFirstProductCard();
        shop().interactProductCard().checkProductCardVisible();
        shop().interactProductCard().decreaseItemCount();
        shop().interactProductCard().checkDecreaseCountButtonNotVisible();
        shop().interactHeader().checkCartItemsCountSpoilerIsNotVisible();
        shop().interactProductCard().clickOnClose();
        shop().interactProductCard().checkProductCardIsNotVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartEmpty();
    }

    @TmsLink("290")
    @Test(description = "Проверка обязательной авторизации при добавлении товара в корзину", groups = REGRESSION_BUSINESS)
    public void testNeedAuthAddToFavourites() {
        business().goToPage();
        business().clickToSetAddress();
        business().interactAddressModal().checkYmapsReady();
        business().interactAddressModal().fillAddress(Addresses.Moscow.defaultAddress());
        business().interactAddressModal().selectFirstAddress();
        business().interactAddressModal().clickFindStores();
        business().interactAddressModal().checkAddressModalNotVisible();

        business().clickOnStoreWithSid(DEFAULT_SID);
        shop().addFirstItemToFavourites();
        shop().interactAuthModal().checkModalIsVisible();
    }
}
