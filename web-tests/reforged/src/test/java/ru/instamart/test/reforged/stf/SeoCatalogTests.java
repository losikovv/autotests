package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Run;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Seo Каталог")
public final class SeoCatalogTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(1802)
    @Test(description = "Тест доступности страницы SEO-каталога", groups = {"acceptance", "regression"})
    public void successCheckSeoPage() {
        seo().goToPage();
        seo().checkPageIsAvailable();
    }

    @CaseId(1803)
    @Test(description = "Тест доступности товаров на странице SEO-каталога", groups = "regression")
    public void successCheckProductsOnSeoCatalog() {
        seo().goToPage();
        seo().checkProductGridVisible();
    }

    @CaseId(1804)
    @Test(description = "Тест открытия карточки товара на странице SEO-каталога", groups = "regression")
    public void successOpenItemCardOnSeoCatalog() {
        seo().goToPage();
        seo().refreshWithoutBasicAuth();
        seo().openFirstProductCardOnTaxon();
        seo().interactProductCard().checkProductCardVisible();
    }

    @CaseId(1805)
    @Test(description = "Тест на ввод адреса в модалке после добавления товара из карточки на странице SEO-каталога", groups = "regression" )
    public void successSetShippingAddressAfterAddingProductFromItemCardOnSeoCatalog() {
        seo().goToPage();
        seo().refreshWithoutBasicAuth();
        seo().openFirstProductCardOnTaxon();
        seo().interactProductCard().clickOnBuy();

        seo().interactHeader().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        seo().interactHeader().interactAddress().selectFirstAddress();
        seo().interactHeader().interactAddress().clickOnSave();
        seo().interactProductCard().checkProductCardVisible();
    }

    @CaseId(1806)
    @Test(description = "Тест авторизации при попытке добавления товара в корзину на странице SEO-каталога", groups = "regression" )
    public void successAuthFromItemCardOnSeoCatalog() {
        seo().goToPage();
        seo().refreshWithoutBasicAuth();
        seo().openFirstProductCardOnTaxon();
        seo().interactProductCard().clickOnBuy();

        seo().interactHeader().interactAddress().clickToLogin();
        seo().interactAuthModal().authViaPhone(UserManager.getQaUser());
        seo().interactHeader().checkProfileButtonVisible();
    }

    @Run(onServer = Server.PRODUCTION)
    @CaseId(1582)
    @Test(description = "Добавление товара в корзину из SEO-каталога", groups = "regression")
    public void successAddItemToCartFromSEOCatalog() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        seo().goToPage();
        seo().refreshWithoutBasicAuth();
        seo().openFirstProductCardOnTaxon();
        seo().interactProductCard().clickOnBuy();
        seo().interactProductCard().close();
        seo().interactHeader().clickToCart();
        seo().interactCart().checkCartOpen();
        seo().interactCart().checkCartNotEmpty();
    }
}
