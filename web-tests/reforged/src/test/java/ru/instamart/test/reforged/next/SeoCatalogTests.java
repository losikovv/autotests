package ru.instamart.test.reforged.next;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Run;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.CookieProvider;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.next.page.StfRouter.seo;
import static ru.instamart.reforged.next.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Seo Каталог")
public final class SeoCatalogTests {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(1802)
    @CookieProvider(cookieFactory = CookieFactory.class, cookies = {"EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Тест доступности страницы SEO-каталога", groups = "regression")
    public void successCheckSeoPage() {
        seo().goToPage();
        seo().checkPageIsAvailable();
        seo().checkPageOpened();
    }

    @CaseId(1803)
    @CookieProvider(cookieFactory = CookieFactory.class, cookies = {"EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Тест доступности товаров на странице SEO-каталога", groups = "regression")
    public void successCheckProductsOnSeoCatalog() {
        seo().goToPage();
        seo().checkProductGridVisible();
        seo().checkProductVisible();
    }

    @CaseId(1804)
    @CookieProvider(cookieFactory = CookieFactory.class, cookies = {"EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Тест открытия карточки товара на странице SEO-каталога", groups = "regression")
    public void successOpenItemCardOnSeoCatalog() {
        seo().goToPage();
        seo().refreshWithoutBasicAuth();
        seo().openFirstProductCardOnTaxon();
        seo().interactProductCard().checkProductCardVisible();
    }

    @CaseId(1805)
    @CookieProvider(cookieFactory = CookieFactory.class, cookies = {"EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Тест на ввод адреса в модалке после добавления товара из карточки на странице SEO-каталога", groups = "regression")
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

    @Run(onServer = Server.PRODUCTION)
    @CaseId(1582)
    @CookieProvider(cookieFactory = CookieFactory.class, cookies = {"EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Добавление товара в корзину из SEO-каталога", groups = "regression")
    public void successAddItemToCartFromSEOCatalog() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        seo().goToPage();
        seo().interactHeader().clickToLogin();
        seo().interactAuthModal().authViaPhone(userData);
        seo().interactHeader().checkProfileButtonVisible();

        seo().interactHeader().checkEnteredAddressIsVisible();
        seo().openFirstProductCardOnTaxon();
        seo().interactProductCard().clickOnBuy();
        seo().interactProductCard().clickOnClose();
        seo().interactHeader().clickToCart();
        seo().interactCart().checkCartOpen();
        seo().interactCart().checkCartNotEmpty();
    }

    @CaseId(2589)
    @CookieProvider(cookieFactory = CookieFactory.class, cookies = {"EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Работоспособность сортировки товаров(сначала дешевые)", groups = "regression")
    public void successSortProductsViaCheap() {
        seo().goToPage("auchan/c/new-sladosti/piechienie/ovsianoie");

        seo().selectSort("Сначала дешевые");
        seo().checkSortEnabled("Сначала дешевые");

        seo().checkSearchImgLoaded();

        seo().refresh();
        seo().checkPriceAscSortCorrect();
    }
}
