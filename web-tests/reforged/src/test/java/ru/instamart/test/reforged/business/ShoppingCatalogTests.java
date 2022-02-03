package ru.instamart.test.reforged.business;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.business.page.BusinessRouter.shop;

@Epic("SMBUSINESS UI")
@Feature("Каталог B2B")
public class ShoppingCatalogTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(302)
    @Test(description = "Добавление товара в корзину из плитки (+удаление)", groups = "regression")
    public void testAddedAndRemoveProductFromShop() {
        var company = JuridicalData.juridical();
        var userData = UserManager.getQaUser();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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

    @CaseId(303)
    @Test(description = "Добавление товара в корзину из карточки товара (+удаление)", groups = "regression")
    public void testAddedAndRemoveProductFromProductCard() {
        var company = JuridicalData.juridical();
        var userData = UserManager.getQaUser();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().openFirstProductCard();
        shop().interactProductCard().checkProductCardVisible();
        shop().interactProductCard().clickOnBuy();

        shop().interactHeader().checkCartNotificationIsVisible();
        shop().interactHeader().checkCartItemsCountSpoilerIsVisible();
        shop().interactProductCard().close();
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

        shop().interactProductCard().close();
        shop().interactProductCard().checkProductCardIsNotVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartEmpty();
    }

    @CaseId(290)
    @Test(description = "Проверка обязательной авторизации при добавлении товара в корзину", groups = "regression")
    public void testNeedAuthAddToFavourites() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().addFirstItemToFavourites();
        shop().interactAuthModal().checkModalIsVisible();
    }
}
