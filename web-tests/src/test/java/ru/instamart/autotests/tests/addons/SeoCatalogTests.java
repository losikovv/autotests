package ru.instamart.autotests.tests.addons;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.application.Config.TestsConfiguration.AddonsTests.enableSeoCatalogTests;
import static ru.instamart.autotests.application.Environments.instamart_staging;
import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class SeoCatalogTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void reachSeoCatalog() {
        kraken.reach().seoCatalog();
    }

    @Test(  enabled = enableSeoCatalogTests,
            description = "Тест доступности страницы SEO-каталога",
            groups = {"smoke","acceptance","regression"},
            priority = 9100
    )
    public void successCheckSeoPage() {
        skipOn(instamart_staging());

        assertPageIsAvailable(
                Pages.Site.Catalog.seo());
    }

    @Test(  enabled = enableSeoCatalogTests,
            description = "Тест доступности товаров на странице SEO-каталога",
            groups = {"regression"},
            priority = 9101
    )
    public void successCheckProductsOnSeoCatalog() {
        skipOn(instamart_staging());

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.SeoCatalog.product()),
                    "Нет товаров на странице SEO-каталога");
    }

    @Test(  enabled = enableSeoCatalogTests,
            description = "Тест открытия карточки товара на странице SEO-каталога",
            groups = {"regression"},
            priority = 9102
    )
    public void successOpenItemCardOnSeoCatalog() {
        skipOn(instamart_staging());
        ShopHelper.Catalog.Item.open();

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Нет открывается карточка товара на странице SEO-каталога");
    }

    @Test(  enabled = enableSeoCatalogTests,
            description = "Тест на ввод адреса в модалке после добавления товара из карточки на странице SEO-каталога",
            groups = {"regression"},
            priority = 9103
    )
    public void successSetShippingAddressAfterAddingProductFromItemCardOnSeoCatalog() throws Exception {
        skipOn(instamart_staging());
        SoftAssert softAssert = new SoftAssert();
        ShopHelper.Catalog.Item.open();
        ShopHelper.ItemCard.addToCart();

        softAssert.assertTrue(
                kraken.detect().isAddressModalOpen(),
                    "Не открывается адресная модалка после добавления товара на странице SEO-каталога");

        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        ShopHelper.ItemCard.close();

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
            groups = {"regression"},
            priority = 9104
    )
    public void successAuthFromItemCardOnSeoCatalog() throws Exception {
        skipOn(instamart_staging());
        SoftAssert softAssert = new SoftAssert();
        ShopHelper.Catalog.Item.open();
        ShopHelper.ItemCard.addToCart();
        kraken.perform().click(Elements.Modals.AddressModal.authButton());
        kraken.perform().loginAs(session.user);

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
            groups = {"regression"},
            priority = 9105
    )
    public void successRegFromItemCardOnSeoCatalog() {
        skipOn(instamart_staging());
        SoftAssert softAssert = new SoftAssert();
        ShopHelper.Catalog.Item.open();
        ShopHelper.ItemCard.addToCart();
        kraken.perform().click(Elements.Modals.AddressModal.authButton());
        kraken.perform().registration();
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает регистрация при попытке добавления товара в корзину на странице SEO-каталога");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(Elements.Catalog.Product.snippet()),
                    "SEO-каталог не перезагрузился на обычный каталог");

        softAssert.assertAll();
    }
}
