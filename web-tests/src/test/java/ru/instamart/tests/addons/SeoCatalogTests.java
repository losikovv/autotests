package ru.instamart.tests.addons;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.Environments;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.lib.Addresses;
import ru.instamart.application.Elements;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.Config.TestsConfiguration.AddonsTests.enableSeoCatalogTests;
import static ru.instamart.application.AppManager.session;

public class SeoCatalogTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void reachSeoCatalog() {
        kraken.reach().seoCatalog();
    }

    @Test(  enabled = enableSeoCatalogTests,
            description = "Тест доступности страницы SEO-каталога",
            groups = {"sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"},
            priority = 9100
    )
    public void successCheckSeoPage() {
        skipTestOnTenant("sbermarket_staging");

        assertPageIsAvailable(
                Pages.seo_catalog());
    }

    @Test(  enabled = enableSeoCatalogTests,
            description = "Тест доступности товаров на странице SEO-каталога",
            groups = {"sbermarket-regression"},
            priority = 9101
    )
    public void successCheckProductsOnSeoCatalog() {
        skipTestOnServer("staging");

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.SeoCatalog.product()),
                    "Нет товаров на странице SEO-каталога");
    }

    @Test(  enabled = enableSeoCatalogTests,
            description = "Тест открытия карточки товара на странице SEO-каталога",
            groups = {"sbermarket-regression"},
            priority = 9102
    )
    public void successOpenItemCardOnSeoCatalog() {
        skipTestOnServer("staging");
        Shop.Catalog.Item.open();

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Нет открывается карточка товара на странице SEO-каталога");
    }

    @Test(  enabled = enableSeoCatalogTests,
            description = "Тест на ввод адреса в модалке после добавления товара из карточки на странице SEO-каталога",
            groups = {"sbermarket-regression"},
            priority = 9103
    )
    public void successSetShippingAddressAfterAddingProductFromItemCardOnSeoCatalog() throws Exception {
        skipTestOnServer("staging");
        SoftAssert softAssert = new SoftAssert();
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToCart();

        softAssert.assertTrue(
                kraken.detect().isAddressModalOpen(),
                    "Не открывается адресная модалка после добавления товара на странице SEO-каталога");

        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        Shop.ItemCard.close();

        softAssert.assertTrue(
                kraken.detect().isShippingAddressSet(),
                    "Адрес доставки не был введен при попытке добавления товара в корзину на странице SEO-каталога");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(Elements.Catalog.Product.snippet()),
                    "SEO-каталог не перезагрузился на обычный каталог");

        softAssert.assertAll();
    }

    @Test(  enabled = enableSeoCatalogTests,
            description = "Тест авторизации при попытке добавления товара в корзину на странице SEO-каталога",
            groups = {"sbermarket-regression"},
            priority = 9104
    )
    public void successAuthFromItemCardOnSeoCatalog() {
        skipTestOnServer("staging");
        SoftAssert softAssert = new SoftAssert();
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToCart();
        kraken.perform().click(Elements.Modals.AddressModal.authButton());
        User.Do.loginAs(session.user);

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает авторизация при попытке добавления товара в корзину на странице SEO-каталога");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(Elements.Catalog.Product.snippet()),
                    "SEO-каталог не перезагрузился на обычный каталог");

        softAssert.assertAll();
    }

    @Test(  enabled = enableSeoCatalogTests,
            description = "Тест регистрации при попытке добавления товара в корзину на странице SEO-каталога",
            groups = {"sbermarket-regression"},
            priority = 9105
    )
    public void successRegFromItemCardOnSeoCatalog() {
        skipTestOnServer("staging");
        SoftAssert softAssert = new SoftAssert();
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToCart();
        kraken.perform().click(Elements.Modals.AddressModal.authButton());
        User.Do.registration();
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает регистрация при попытке добавления товара в корзину на странице SEO-каталога");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(Elements.Catalog.Product.snippet()),
                    "SEO-каталог не перезагрузился на обычный каталог");

        softAssert.assertAll();
    }
}
