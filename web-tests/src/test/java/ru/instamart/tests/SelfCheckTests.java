package ru.instamart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.Elements;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.lib.Addresses;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;

import static ru.instamart.application.AppManager.session;
import static ru.instamart.application.platform.helpers.HelperBase.verboseMessage;

public class SelfCheckTests extends TestBase {

    @Test(description = "Тест базового URL",
            groups ="selfcheck",
            priority = 10000)
    public void initialCheck() {
        kraken.get().baseUrl();
        Assert.assertEquals(kraken.grab().currentURL() , kraken.grab().fullBaseUrl);
    }

    @Test(description = "Тест корректности работы методов навигации",
            groups ="selfcheck",
            priority = 10001)
    public void checkNavigation() {

        kraken.get().page("metro");
        Assert.assertEquals(kraken.grab().currentURL() , kraken.grab().fullBaseUrl + "metro");

        kraken.get().page(Pages.Site.Static.faq());
        Assert.assertEquals(kraken.grab().currentURL() , kraken.grab().fullBaseUrl + Pages.Site.Static.faq().getPath());
    }

    @Test(description = "Тест корректности определения модалки авторизации/регистрации",
            groups ="selfcheck",
            priority = 10002)
    public void detectAuthModal() {

        kraken.get().baseUrl();

        Shop.AuthModal.open();
        Assert.assertTrue(kraken.detect().isAuthModalOpen());

        Shop.AuthModal.close();
        Assert.assertFalse(kraken.detect().isAuthModalOpen());

        kraken.get().page("metro");

        Shop.AuthModal.open();
        Assert.assertTrue(kraken.detect().isAuthModalOpen());

        Shop.AuthModal.close();
        Assert.assertFalse(kraken.detect().isAuthModalOpen());
    }

    @Test(description = "Тест корректности определения авторизованности пользователя",
            groups ="selfcheck",
            priority = 10003)
    public void detectAuthorisation() throws Exception {

        kraken.drop().auth();

        kraken.get().baseUrl();
        Assert.assertFalse(kraken.detect().isUserAuthorised());

        kraken.get().page("metro");
        Assert.assertFalse(kraken.detect().isUserAuthorised());

        User.Do.loginAs(session.admin);
        Assert.assertTrue(kraken.detect().isUserAuthorised());

        User.Logout.manually();
        Assert.assertFalse(kraken.detect().isUserAuthorised());
    }

    // TODO public void detectAddressModal() throws Exception { }

    @Test(description = "Тест корректности определения меню Профиль",
            groups ="selfcheck",
            priority = 10004)
    public void detectAccountMenu() throws Exception {

        kraken.get().baseUrl();
        User.Do.loginAs(session.admin);

        Shop.AccountMenu.open();
        Assert.assertTrue(kraken.detect().isAccountMenuOpen());

        Shop.AccountMenu.close();
        Assert.assertFalse(kraken.detect().isAccountMenuOpen());
    }

    @Test(description = "Тест корректности определения карточки товара",
            groups ="selfcheck",
            priority = 10005)
    public void detectItemCard() {

        kraken.get().page("metro/interaktivnaya-igrushka-furreal-friends-pokormi-kotenka");
        Assert.assertTrue(kraken.detect().isItemCardOpen());

        kraken.get().page("metro/eliektronika");
        Assert.assertFalse(kraken.detect().isItemCardOpen());
    }

    @Test(description = "Тест корректности определения что находимся на сайте",
            groups ="selfcheck",
            priority = 10006)
    public void detectIsOnSite() throws Exception {

        kraken.get().page(Pages.Site.Static.faq());
        Assert.assertTrue(kraken.detect().isOnSite());

        User.Do.loginAs(session.admin);
        kraken.get().page(Pages.Admin.retailers());
        Assert.assertFalse(kraken.detect().isOnSite());
    }

    @Test(description = "Тест корректности определения что находимся в админке",
            groups ="selfcheck",
            priority = 10007)
    public void detectIsInAdmin() throws Exception {

        kraken.get().page(Pages.Site.Static.contacts());
        Assert.assertFalse(kraken.detect().isInAdmin());

        User.Do.loginAs(session.admin);
        kraken.get().page(Pages.Admin.settings());
        Assert.assertTrue(kraken.detect().isInAdmin());
    }

    @Test(description = "Тест корректности определения 404 ошибки на страниице",
            groups ="selfcheck",
            priority = 10008)
    public void detect404() {

        kraken.get().page(Pages.page404());
        Assert.assertTrue(kraken.detect().is404());

        kraken.get().page("metro");
        Assert.assertFalse(kraken.detect().is404());
    }

    @Test(description = "Тест корректности определения 500 ошибки на страниице",
            groups ="selfcheck",
            priority = 10009)
    public void detect500() {

        kraken.get().page(Pages.page500());
        Assert.assertTrue(kraken.detect().is500());

        kraken.get().page("metro");
        Assert.assertFalse(kraken.detect().is500());
    }

    @Test(description = "Тест корректности определения шторки Каталога",
            groups ="selfcheck",
            priority = 10010)
    public void detectCatalogDrawer() {

        kraken.get().page("metro");

        Shop.StoreSelector.open();
        Assert.assertTrue(kraken.detect().isCatalogDrawerOpen());

        Shop.StoreSelector.close();
        Assert.assertFalse(kraken.detect().isCatalogDrawerOpen());
    }

    @Test(description = "Тест корректности определения дефолтного селектора магазинов",
            groups ="selfcheck",
            priority = 10011)
    public void detectDefaultStoresDrawer() {
        SoftAssert softAssert = new SoftAssert();
        User.Logout.quickly();

        //landing
        User.ShippingAddress.set(Addresses.Moscow.testAddress());

        softAssert.assertTrue(kraken.detect().isStoreSelectorOpen(),
                "\nНе открывается дефолтный селектор магазинов на лендинге");

        softAssert.assertFalse(kraken.detect().isStoreSelectorEmpty(),
                "\nПусто в дефолтном селекторе магазинов на лендинге");

        Shop.StoreSelector.open();

        softAssert.assertFalse(kraken.detect().isStoreSelectorOpen(),
                "\nНе закрывается дефолтный селектор магазинов на лендинге");

        //retailer
        kraken.get().page("metro");
        Shop.StoreSelector.open();

        softAssert.assertTrue(kraken.detect().isStoreSelectorOpen(),
                "\nНе открывается дефолтный селектор магазинов на витрине ритейлера");

        softAssert.assertFalse(kraken.detect().isStoreSelectorEmpty(),
                "\nПусто в дефолтном селекторе магазинов на витрине ритейлера");

        Shop.StoreSelector.close();

        softAssert.assertFalse(kraken.detect().isStoreSelectorOpen(),
                "\nНе закрывается дефолтный селектор магазинов на витрине ритейлера");

        softAssert.assertAll();
    }

    @Test(description = "Тест корректности определения пустого селектора магазинов",
            groups ="selfcheck",
            priority = 10012)
    public void detectEmptyStoresDrawer() {
        SoftAssert softAssert = new SoftAssert();
        User.Logout.quickly();

        //landing
        User.ShippingAddress.set(Addresses.Moscow.outOfZoneAddress());

        softAssert.assertTrue(kraken.detect().isStoreSelectorOpen(),
                "\nНе открывается пустой селектор магазинов на лендинге");

        softAssert.assertFalse(kraken.detect().isStoreSelectorEmpty(),
                "\nНе определяется пустой селектор магазинов на лендинге");

        Shop.StoreSelector.close();;

        softAssert.assertFalse(kraken.detect().isStoreSelectorOpen(),
                "\nНе закрывается пустой селектор магазинов на лендинге");

        //retailer
        kraken.get().page("metro");
        Shop.StoreSelector.open();;

        softAssert.assertTrue(kraken.detect().isStoreSelectorOpen(),
                "\nНе открывается пустой селектор магазинов на витрине ритейлера");

        softAssert.assertFalse(kraken.detect().isStoreSelectorEmpty(),
                "\nНе определяется пустой селектор магазинов на витрине ритейлера");

        Shop.StoreSelector.close();;

        softAssert.assertFalse(kraken.detect().isStoreSelectorOpen(),
                "\nНе закрывается пустой селектор магазинов на витрине ритейлера");

        softAssert.assertAll();
    }

    @Test(description = "Тест корректности определения шторки корзины",
            groups ="selfcheck",
            priority = 10013)
    public void detectCartDrawer() {

        kraken.get().page("metro");

        Shop.Cart.open();
        Assert.assertTrue(kraken.detect().isCartOpen());

        Shop.Cart.close();
        Assert.assertFalse(kraken.detect().isCartOpen());
    }

    @Test(description = "Тест корректности определения модалки Доставка",
            groups ="selfcheck",
            priority = 10014)
    public void detectDeliveryModal() {

        kraken.get().page("metro");

        kraken.perform().click(Elements.Header.deliveryInfoButton());
        Assert.assertTrue(kraken.detect().isDeliveryModalOpen());

        kraken.perform().click(Elements.Modals.DeliveryModal.closeButton());
        Assert.assertFalse(kraken.detect().isDeliveryModalOpen());
    }

    @Test(description = "Тест корректности определения модалки Партнеры",
            groups ="selfcheck",
            priority = 10015)
    public void detectPartnersModal() {

        kraken.get().page("metro");

        //kraken.perform().click(Elements.Site.Header.partnersButton());
        Assert.assertTrue(kraken.detect().isPartnersModalOpen());

        kraken.perform().click(Elements.Modals.PartnersModal.closeButton());
        Assert.assertFalse(kraken.detect().isPartnersModalOpen());
    }

    @Test(description = "Тест корректности определения модалки Оплата",
            groups ="selfcheck",
            priority = 10016)
    public void detectPaymentModal() {

        kraken.get().page("metro");

        kraken.perform().click(Elements.Footer.paymentButton());
        Assert.assertTrue(kraken.detect().isPaymentModalOpen());

        kraken.perform().click(Elements.Modals.PaymentModal.closeButton());
        Assert.assertFalse(kraken.detect().isPaymentModalOpen());
    }

     @Test(description = "Тест корректности определения модалки Адрес",
            groups ="selfcheck",
            priority = 10017)
     public void detectAddressModal() {

        kraken.get().page("metro");

        Shop.ShippingAddressModal.open();
        Assert.assertTrue(kraken.detect().isAddressModalOpen());

        Shop.ShippingAddressModal.close();
        Assert.assertFalse(kraken.detect().isAddressModalOpen());
    }

    @Test(
            description = "Тест корректности определения суммы корзины",
            groups ="selfcheck",
            priority = 10018)
    public void detectCartTotal() {
        kraken.get().page("metro");
        if (!kraken.detect().isShippingAddressSet()) {
            User.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        }
        Shop.Cart.drop();

        // корзина пустая
        Assert.assertFalse(kraken.detect().isCartTotalDisplayed());
        Assert.assertNull(kraken.grab().cartTotal());
        verboseMessage("Сумма корзины = " + kraken.grab().cartTotal());

        // корзина не пустая, но меньше суммы мин заказа
        Shop.Cart.close();
        Shop.Search.item("молоко");
        Shop.Cart.collect(1);
        Assert.assertTrue(kraken.detect().isCartTotalDisplayed());
        Assert.assertNotNull(kraken.grab().cartTotal());
        verboseMessage("Сумма корзины = " + kraken.grab().cartTotal());

        // корзина не пустая, больше суммы мин заказа
        Shop.Cart.collect();
        Assert.assertTrue(kraken.detect().isCartTotalDisplayed());
        verboseMessage("Сумма корзины = " + kraken.grab().cartTotal());
    }

    @Test(description = "Тест корректности определения заглушки адреса вне зоны доставки",
            groups ="selfcheck",
            priority = 10019)
    public void detectAddressOutOfZone() {

        kraken.get().page("metro");
        User.ShippingAddress.set(Addresses.Moscow.outOfZoneAddress());
        Assert.assertTrue(kraken.detect().isAddressOutOfZone());

        kraken.get().page("metro");
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        Assert.assertFalse(kraken.detect().isAddressOutOfZone());
    }

    @Test(description = "Тест корректности работы ассертов",
            groups ="selfcheck",
            priority = 10020)
    public void checkAsserts() {

        assertTransition("https://instamart.ru/auchan");
        assertTransition(Pages.Site.Retailers.vkusvill());

        assertPageIsAvailable("https://instamart.ru/auchan");
        assertPageIsAvailable(Pages.Site.Static.contacts());
        assertPageIsAvailable();

        assertPageIs404(Pages.page404());
        assertPageIs404("https://instamart.ru/nowhere");
        assertPageIs404();

        assertPageIsUnavailable(Pages.Site.checkout());
        assertPageIsUnavailable("https://instamart.ru/checkout/edit");
    }
}

