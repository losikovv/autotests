package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Run;
import io.qameta.allure.TmsLink;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.stf.page.StfRouter.seo;

@Epic("STF UI")
@Feature("Seo Каталог")
public final class SeoCatalogTests {

    private final ApiHelper helper = new ApiHelper();

    @TmsLink("1802")
    @Test(description = "Тест доступности страницы SEO-каталога", groups = REGRESSION_STF)
    public void successCheckSeoPage() {
        seo().goToPage();
        seo().checkPageIsAvailable();
        seo().checkPageOpened();
    }

    @TmsLink("1803")
    @Test(description = "Тест доступности товаров на странице SEO-каталога", groups = REGRESSION_STF)
    public void successCheckProductsOnSeoCatalog() {
        seo().goToPage();
        seo().checkProductGridVisible();
        seo().checkProductVisible();
    }

    @TmsLink("1804")
    @Test(description = "Тест открытия карточки товара на странице SEO-каталога", groups = REGRESSION_STF)
    public void successOpenItemCardOnSeoCatalog() {
        seo().goToPage();
        seo().refreshWithoutBasicAuth();
        seo().openFirstProductCardOnTaxon();
        seo().interactProductCard().checkProductCardVisible();
    }

    @TmsLink("1805")
    @Test(description = "Тест на ввод адреса в модалке после добавления товара из карточки на странице SEO-каталога", groups = REGRESSION_STF)
    public void successSetShippingAddressAfterAddingProductFromItemCardOnSeoCatalog() {
        seo().goToPage();
        seo().refreshWithoutBasicAuth();
        seo().openFirstProductCardOnTaxon();
        seo().interactProductCard().clickOnBuy();

        seo().interactHeader().interactAddressLarge().checkYmapsReady();
        seo().interactHeader().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        seo().interactHeader().interactAddressLarge().selectFirstAddress();
        seo().interactHeader().interactAddressLarge().clickSave();
        seo().interactHeader().interactAddress().checkAddressModalIsNotVisible();

        seo().interactProductCard().checkProductCardVisible();
    }

    @Run(onServer = Server.PRODUCTION)
    @TmsLink("1582")
    @Test(description = "Добавление товара в корзину из SEO-каталога", groups = REGRESSION_STF)
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

    @TmsLink("2589")
    @Test(description = "Работоспособность сортировки товаров(сначала дешевые)", groups = REGRESSION_STF)
    public void successSortProductsViaCheap() {
        seo().goToPage("auchan/c/new-sladosti/piechienie/ovsianoie");

        seo().selectSort("Сначала дешевые");
        seo().checkSortEnabled("Сначала дешевые");

        seo().checkSearchImgLoaded();

        seo().refresh();
        seo().checkPriceAscSortCorrect();
    }
}
