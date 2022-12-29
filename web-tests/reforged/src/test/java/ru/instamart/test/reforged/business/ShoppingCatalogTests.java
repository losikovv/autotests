package ru.instamart.test.reforged.business;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.user.UserManager;
import io.qameta.allure.TmsLink;

import static ru.instamart.reforged.Group.REGRESSION_BUSINESS;
import static ru.instamart.reforged.Group.REGRESSION_BUSINESS;
import static ru.instamart.reforged.business.page.BusinessRouter.*;
import static ru.instamart.reforged.business.page.BusinessRouter.b2cHome;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_AUCHAN_SID;

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

        business().clickOnStoreWithSid(DEFAULT_AUCHAN_SID);

        shop().interactHeader().checkEnteredAddressIsVisible();
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

        business().clickOnStoreWithSid(DEFAULT_AUCHAN_SID);

        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().openFirstProductCard();
        shop().interactProductCard().checkProductCardVisible();
        shop().interactProductCard().clickOnBuy();

        shop().interactHeader().checkCartNotificationIsVisible();
        shop().interactHeader().checkCartItemsCountSpoilerIsVisible();
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
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();

        shop().interactHeader().interactStoreSelector().checkStoreSelectorFrameIsOpen();
        shop().interactHeader().interactStoreSelector().clickOnStoreWithSid(DEFAULT_AUCHAN_SID);
        shop().interactHeader().interactStoreSelector().checkStoreSelectorFrameIsNotOpen();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().addFirstItemToFavourites();
        shop().interactAuthModal().checkModalIsVisible();
    }
}
