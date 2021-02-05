package ru.instamart.tests;

import instamart.core.settings.Config;
import instamart.core.testdata.UserManager;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.common.lib.Addresses;
import instamart.ui.common.lib.Pages;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.ui.TestBase;

public class SelfCheckTests extends TestBase {

    private static final Logger log = LoggerFactory.getLogger(SelfCheckTests.class);

    private final BaseUICheckpoints baseChecks = new BaseUICheckpoints();

    @Test(description = "Тест базового URL",
            groups ="selfcheck")
    public void initialCheck() {
        kraken.get().baseUrl();
        Assert.assertEquals(kraken.grab().currentURL() , EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth());
    }

    @Test(description = "Тест корректности работы методов навигации",
            groups ="selfcheck")
    public void checkNavigation() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        Assert.assertEquals(kraken.grab().currentURL() , EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + "metro");

        kraken.get().page(Pages.Sbermarket.faq());
        Assert.assertEquals(kraken.grab().currentURL() , EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + Pages.Sbermarket.faq().getPath());
    }

    @Test(description = "Тест корректности определения модалки авторизации/регистрации",
            groups ="selfcheck")
    public void detectAuthModal() {

        kraken.get().baseUrl();

        Shop.AuthModal.open();
        Assert.assertTrue(kraken.detect().isAuthModalOpen());

        Shop.AuthModal.close();
        Assert.assertFalse(kraken.detect().isAuthModalOpen());

        kraken.get().page(Config.DEFAULT_RETAILER);

        Shop.AuthModal.open();
        Assert.assertTrue(kraken.detect().isAuthModalOpen());

        Shop.AuthModal.close();
        Assert.assertFalse(kraken.detect().isAuthModalOpen());
    }

    @Test(description = "Тест корректности определения авторизованности пользователя",
            groups ="selfcheck")
    public void detectAuthorisation() {

        kraken.reach().logout();

        kraken.get().baseUrl();
        Assert.assertFalse(kraken.detect().isUserAuthorised());

        kraken.get().page(Config.DEFAULT_RETAILER);
        Assert.assertFalse(kraken.detect().isUserAuthorised());

        User.Do.loginAs(UserManager.getDefaultAdmin());
        Assert.assertTrue(kraken.detect().isUserAuthorised());

        User.Logout.manually();
        Assert.assertFalse(kraken.detect().isUserAuthorised());
    }

    // TODO public void detectAddressModal() throws Exception { }

    @Test(description = "Тест корректности определения меню Профиль",
            groups ="selfcheck")
    public void detectAccountMenu() {

        kraken.get().baseUrl();
        User.Do.loginAs(UserManager.getDefaultAdmin());

        Shop.AccountMenu.open();
        Assert.assertTrue(kraken.detect().isAccountMenuOpen());

        Shop.AccountMenu.close();
        Assert.assertFalse(kraken.detect().isAccountMenuOpen());
    }

    @Test(description = "Тест корректности определения карточки товара",
            groups ="selfcheck")
    public void detectItemCard() {

        kraken.get().page("metro/interaktivnaya-igrushka-furreal-friends-pokormi-kotenka");
        Assert.assertTrue(kraken.detect().isItemCardOpen());

        kraken.get().page("metro/eliektronika");
        Assert.assertFalse(kraken.detect().isItemCardOpen());
    }

    @Test(description = "Тест корректности определения что находимся на сайте",
            groups ="selfcheck")
    public void detectIsOnSite() {

        kraken.get().page(Pages.Sbermarket.faq());
        Assert.assertTrue(kraken.detect().isOnSite());

        User.Do.loginAs(UserManager.getDefaultAdmin());
        kraken.get().page(Pages.Admin.retailers());
        Assert.assertFalse(kraken.detect().isOnSite());
    }

    @Test(description = "Тест корректности определения что находимся в админке",
            groups ="selfcheck")
    public void detectIsInAdmin() {

        kraken.get().page(Pages.Sbermarket.contacts());
        Assert.assertFalse(kraken.detect().isInAdmin());

        User.Do.loginAs(UserManager.getDefaultAdmin());
        kraken.get().page(Pages.Admin.settings());
        Assert.assertTrue(kraken.detect().isInAdmin());
    }

    @Test(description = "Тест корректности определения 404 ошибки на страниице",
            groups ="selfcheck")
    public void detect404() {

        kraken.get().page(Pages.page404());
        Assert.assertTrue(kraken.detect().is404());

        kraken.get().page(Config.DEFAULT_RETAILER);
        Assert.assertFalse(kraken.detect().is404());
    }

    @Test(description = "Тест корректности определения 500 ошибки на страниице",
            groups ="selfcheck")
    public void detect500() {

        kraken.get().page(Pages.page500());
        Assert.assertTrue(kraken.detect().is500());

        kraken.get().page(Config.DEFAULT_RETAILER);
        Assert.assertFalse(kraken.detect().is500());
    }

    @Test(description = "Тест корректности определения шторки Каталога",
            groups ="selfcheck")
    public void detectCatalogDrawer() {

        kraken.get().page(Config.DEFAULT_RETAILER);

        Shop.StoresDrawer.open();
        Assert.assertTrue(kraken.detect().isCatalogDrawerOpen());

        Shop.StoresDrawer.close();
        Assert.assertFalse(kraken.detect().isCatalogDrawerOpen());
    }

    @Test(description = "Тест корректности определения дефолтного селектора магазинов",
            groups ="selfcheck")
    public void detectDefaultStoresDrawer() {
        SoftAssert softAssert = new SoftAssert();
        User.Logout.quickly();

        //landing
        User.ShippingAddress.set(Addresses.Moscow.testAddress(),true);

        softAssert.assertTrue(kraken.detect().isStoresDrawerOpen(),
                "\nНе открывается дефолтный селектор магазинов на лендинге");

        softAssert.assertFalse(kraken.detect().isStoresDrawerEmpty(),
                "\nПусто в дефолтном селекторе магазинов на лендинге");

        Shop.StoresDrawer.open();

        softAssert.assertFalse(kraken.detect().isStoresDrawerOpen(),
                "\nНе закрывается дефолтный селектор магазинов на лендинге");

        //retailer
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.StoresDrawer.open();

        softAssert.assertTrue(kraken.detect().isStoresDrawerOpen(),
                "\nНе открывается дефолтный селектор магазинов на витрине ритейлера");

        softAssert.assertFalse(kraken.detect().isStoresDrawerEmpty(),
                "\nПусто в дефолтном селекторе магазинов на витрине ритейлера");

        Shop.StoresDrawer.close();

        softAssert.assertFalse(kraken.detect().isStoresDrawerOpen(),
                "\nНе закрывается дефолтный селектор магазинов на витрине ритейлера");

        softAssert.assertAll();
    }

    @Test(description = "Тест корректности определения пустого селектора магазинов",
            groups ="selfcheck")
    public void detectEmptyStoresDrawer() {
        SoftAssert softAssert = new SoftAssert();
        User.Logout.quickly();

        //landing
        User.ShippingAddress.set(Addresses.Moscow.outOfZoneAddress(),true);

        softAssert.assertTrue(kraken.detect().isStoresDrawerOpen(),
                "\nНе открывается пустой селектор магазинов на лендинге");

        softAssert.assertFalse(kraken.detect().isStoresDrawerEmpty(),
                "\nНе определяется пустой селектор магазинов на лендинге");

        Shop.StoresDrawer.close();;

        softAssert.assertFalse(kraken.detect().isStoresDrawerOpen(),
                "\nНе закрывается пустой селектор магазинов на лендинге");

        //retailer
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.StoresDrawer.open();;

        softAssert.assertTrue(kraken.detect().isStoresDrawerOpen(),
                "\nНе открывается пустой селектор магазинов на витрине ритейлера");

        softAssert.assertFalse(kraken.detect().isStoresDrawerEmpty(),
                "\nНе определяется пустой селектор магазинов на витрине ритейлера");

        Shop.StoresDrawer.close();;

        softAssert.assertFalse(kraken.detect().isStoresDrawerOpen(),
                "\nНе закрывается пустой селектор магазинов на витрине ритейлера");

        softAssert.assertAll();
    }

    @Test(description = "Тест корректности определения шторки корзины",
            groups ="selfcheck")
    public void detectCartDrawer() {

        kraken.get().page(Config.DEFAULT_RETAILER);

        Shop.Cart.open();
        Assert.assertTrue(kraken.detect().isCartOpen());

        Shop.Cart.close();
        Assert.assertFalse(kraken.detect().isCartOpen());
    }

    @Test(description = "Тест корректности определения модалки Доставка",
            groups ="selfcheck")
    public void detectDeliveryModal() {

        kraken.get().page(Config.DEFAULT_RETAILER);

        kraken.perform().click(Elements.Header.deliveryInfoButton());
        Assert.assertTrue(kraken.detect().isDeliveryModalOpen());

        kraken.perform().click(Elements.Modals.DeliveryModal.closeButton());
        Assert.assertFalse(kraken.detect().isDeliveryModalOpen());
    }
    
    @Test(description = "Тест корректности определения модалки Оплата",
            groups ="selfcheck")
    public void detectPaymentModal() {

        kraken.get().page(Config.DEFAULT_RETAILER);

        kraken.perform().click(Elements.Footer.paymentButton());
        Assert.assertTrue(kraken.detect().isPaymentModalOpen());

        kraken.perform().click(Elements.Modals.PaymentModal.closeButton());
        Assert.assertFalse(kraken.detect().isPaymentModalOpen());
    }

     @Test(description = "Тест корректности определения модалки Адрес",
            groups ="selfcheck")
     public void detectAddressModal() {

        kraken.get().page(Config.DEFAULT_RETAILER);

        Shop.ShippingAddressModal.open();
        Assert.assertTrue(kraken.detect().isAddressModalOpen());

        Shop.ShippingAddressModal.close();
        Assert.assertFalse(kraken.detect().isAddressModalOpen());
    }

    @Test(
            description = "Тест корректности определения суммы корзины",
            groups ="selfcheck")
    public void detectCartTotal() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        if (!kraken.detect().isShippingAddressSet()) {
            User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
        }
        Shop.Cart.drop();

        // корзина пустая
        Assert.assertFalse(kraken.detect().isCartTotalDisplayed());
        Assert.assertNull(kraken.grab().cartTotal());
        log.info("Сумма корзины = {}", kraken.grab().cartTotal());

        // корзина не пустая, но меньше суммы мин заказа
        Shop.Cart.close();
        Shop.Search.item("молоко");
        Shop.Cart.collect(1);
        Assert.assertTrue(kraken.detect().isCartTotalDisplayed());
        Assert.assertNotNull(kraken.grab().cartTotal());
        log.info("Сумма корзины = {}", kraken.grab().cartTotal());

        // корзина не пустая, больше суммы мин заказа
        Shop.Cart.collectFirstTime();
        Assert.assertTrue(kraken.detect().isCartTotalDisplayed());
        log.info("Сумма корзины = {}", kraken.grab().cartTotal());
    }

    @Test(description = "Тест корректности определения заглушки адреса вне зоны доставки",
            groups ="selfcheck")
    public void detectAddressOutOfZone() {

        kraken.get().page(Config.DEFAULT_RETAILER);
        User.ShippingAddress.set(Addresses.Moscow.outOfZoneAddress(),true);
        Assert.assertTrue(kraken.detect().isAddressOutOfZone());

        kraken.get().page(Config.DEFAULT_RETAILER);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
        Assert.assertFalse(kraken.detect().isAddressOutOfZone());
    }

    @Test(description = "Тест корректности работы ассертов",
            groups ="selfcheck")
    public void checkAsserts() {
        baseChecks.checkTransition("https://instamart.ru/auchan");
        baseChecks.checkTransition(Pages.Retailers.vkusvill());
        baseChecks.checkPageIsAvailable("https://instamart.ru/auchan");
        baseChecks.checkTransition(Pages.Sbermarket.contacts());
        baseChecks.checkPageIsAvailable();
        baseChecks.checkPageIs404(Pages.page404());
        baseChecks.checkPageIs404("https://instamart.ru/nowhere");
        baseChecks.checkPageIs404();
        baseChecks.checkPageIsUnavailable(Pages.checkout());
        baseChecks.checkPageIsUnavailable("https://instamart.ru/checkout/edit");
    }
}

