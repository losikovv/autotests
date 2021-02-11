package ru.instamart.tests.ui.addons;

import instamart.core.testdata.UserManager;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.common.lib.Addresses;
import instamart.ui.common.lib.Pages;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.ui.TestBase;

public class SeoCatalogTests extends TestBase {
    
    private final BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    
    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.reach().seoCatalog();
    }

    @Test(  description = "Тест доступности страницы SEO-каталога",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successCheckSeoPage() {
        skipTestOnServer("staging");

        baseChecks.checkPageIsAvailable(
                Pages.seo_catalog());
    }

    @Test(  description = "Тест доступности товаров на странице SEO-каталога",
            groups = {"sbermarket-regression"}
    )
    public void successCheckProductsOnSeoCatalog() {
        skipTestOnServer("staging");

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.SeoCatalog.product()),
                    "Нет товаров на странице SEO-каталога");
    }

    @Test(  description = "Тест открытия карточки товара на странице SEO-каталога",
            groups = {"sbermarket-regression"}
    )
    public void successOpenItemCardOnSeoCatalog() {
        skipTestOnServer("staging");
        Shop.Catalog.Item.open();

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Нет открывается карточка товара на странице SEO-каталога");
    }

    @Test(  description = "Тест на ввод адреса в модалке после добавления товара из карточки на странице SEO-каталога",
            groups = {"sbermarket-regression"}
    )
    public void successSetShippingAddressAfterAddingProductFromItemCardOnSeoCatalog() throws Exception {
        skipTestOnServer("staging");
        SoftAssert softAssert = new SoftAssert();
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToCart();

        softAssert.assertTrue(
                kraken.detect().isAddressModalOpen(),
                    "Не открывается адресная модалка после добавления товара на странице SEO-каталога");

        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
        Shop.ItemCard.close();

        softAssert.assertTrue(
                kraken.detect().isShippingAddressSet(),
                    "Адрес доставки не был введен при попытке добавления товара в корзину на странице SEO-каталога");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(Elements.Catalog.Product.snippet()),
                    "SEO-каталог не перезагрузился на обычный каталог");

        softAssert.assertAll();
    }

    @Test(  description = "Тест авторизации при попытке добавления товара в корзину на странице SEO-каталога",
            groups = {"sbermarket-regression"}
    )
    public void successAuthFromItemCardOnSeoCatalog() {
        skipTestOnServer("staging");
        SoftAssert softAssert = new SoftAssert();
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToCart();
        kraken.perform().click(Elements.Modals.AddressModal.authButton());
        User.Do.loginAs(UserManager.getDefaultUser());

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает авторизация при попытке добавления товара в корзину на странице SEO-каталога");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(Elements.Catalog.Product.snippet()),
                    "SEO-каталог не перезагрузился на обычный каталог");

        softAssert.assertAll();
    }

    @Test(  description = "Тест регистрации при попытке добавления товара в корзину на странице SEO-каталога",
            groups = {"sbermarket-regression"}
    )
    public void successRegFromItemCardOnSeoCatalog() {
        skipTestOnServer("staging");
        SoftAssert softAssert = new SoftAssert();
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToCart();
        kraken.perform().click(Elements.Modals.AddressModal.authButton());
        User.Do.registration();
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает регистрация при попытке добавления товара в корзину на странице SEO-каталога");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(Elements.Catalog.Product.snippet()),
                    "SEO-каталог не перезагрузился на обычный каталог");

        softAssert.assertAll();
    }
}
