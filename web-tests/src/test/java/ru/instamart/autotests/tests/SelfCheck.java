package ru.instamart.autotests.tests;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.application.Addresses;


// Тесты самопроверки кракена


public class SelfCheck extends TestBase {


    @Test(description = "Тест базового URL",
            groups ="selfcheck",
            priority = 10000)
    public void initialCheck() throws Exception {
        kraken.get().baseUrl();
        Assert.assertEquals(kraken.grab().currentURL() , kraken.grab().fullBaseUrl);
    }


    @Test(description = "Тест корректности работы методов навигации",
            groups ="selfcheck",
            priority = 10001)
    public void checkNavigation() throws Exception {

        kraken.get().page("metro");
        Assert.assertEquals(kraken.grab().currentURL() , kraken.grab().fullBaseUrl + "metro");

        kraken.get().page(Pages.Site.Static.faq());
        Assert.assertEquals(kraken.grab().currentURL() , kraken.grab().fullBaseUrl + Pages.getPagePath());
    }


    // TODO проверка GO методов


    @Test(description = "Тест корректности определения модалки авторизации/регистрации",
            groups ="selfcheck",
            priority = 10002)
    public void detectAuthModal() throws Exception {

        kraken.get().baseUrl();

        kraken.perform().openAuthModal();
        Assert.assertTrue(kraken.detect().isAuthModalOpen());

        kraken.perform().closeAuthModal();
        Assert.assertFalse(kraken.detect().isAuthModalOpen());

        kraken.get().page("metro");

        kraken.perform().openAuthModal();
        Assert.assertTrue(kraken.detect().isAuthModalOpen());

        kraken.perform().closeAuthModal();
        Assert.assertFalse(kraken.detect().isAuthModalOpen());
    }


    @Test(description = "Тест корректности определения авторизованности пользователя",
            groups ="selfcheck",
            priority = 10003)
    public void detectAuthorisation() throws Exception {

        kraken.perform().dropAuth();

        kraken.get().baseUrl();
        Assert.assertFalse(kraken.detect().isUserAuthorised());

        kraken.get().page("metro");
        Assert.assertFalse(kraken.detect().isUserAuthorised());

        kraken.perform().loginAs("admin");
        Assert.assertTrue(kraken.detect().isUserAuthorised());

        kraken.perform().logout();
        Assert.assertFalse(kraken.detect().isUserAuthorised());
    }


    // TODO public void detectAddressModal() throws Exception { }


    @Test(description = "Тест корректности определения меню Профиль",
            groups ="selfcheck",
            priority = 10004)
    public void detectAccountMenu() throws Exception {

        kraken.get().baseUrl();
        kraken.perform().loginAs("admin");

        kraken.perform().openAccountMenu();
        Assert.assertTrue(kraken.detect().isAccountMenuOpen());

        kraken.perform().closeAccountMenu();
        Assert.assertFalse(kraken.detect().isAccountMenuOpen());
    }


    @Test(description = "Тест корректности определения карточки товара",
            groups ="selfcheck",
            priority = 10005)
    public void detectItemCard() throws Exception {

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

        kraken.perform().loginAs("admin");
        kraken.get().page(Pages.Admin.retailers());
        Assert.assertFalse(kraken.detect().isOnSite());
    }


    @Test(description = "Тест корректности определения что находимся в админке",
            groups ="selfcheck",
            priority = 10007)
    public void detectIsInAdmin() throws Exception {

        kraken.get().page(Pages.Site.Static.contacts());
        Assert.assertFalse(kraken.detect().isInAdmin());

        kraken.perform().loginAs("admin");
        kraken.get().page(Pages.Admin.settings());
        Assert.assertTrue(kraken.detect().isInAdmin());
    }


    @Test(description = "Тест корректности определения 404 ошибки на страниице",
            groups ="selfcheck",
            priority = 10008)
    public void detect404() throws Exception {

        kraken.get().page("nowhere");
        Assert.assertTrue(kraken.detect().is404());

        kraken.get().page("metro");
        Assert.assertFalse(kraken.detect().is404());
    }


    @Test(description = "Тест корректности определения 500 ошибки на страниице",
            groups ="selfcheck",
            priority = 10009)
    public void detect500() throws Exception {

        kraken.get().page("stores/21/shipping_methods");
        Assert.assertTrue(kraken.detect().is500());

        kraken.get().page("metro");
        Assert.assertFalse(kraken.detect().is500());
    }


    @Test(description = "Тест корректности определения шторки Каталога",
            groups ="selfcheck",
            priority = 10010)
    public void detectCatalogDrawer() throws Exception {

        kraken.get().page("metro");

        kraken.shopping().openCatalog();
        Assert.assertTrue(kraken.detect().isCatalogDrawerOpen());

        kraken.shopping().closeCatalog();
        Assert.assertFalse(kraken.detect().isCatalogDrawerOpen());
    }


    @Test(description = "Тест корректности определения дефолтного селектора магазинов",
            groups ="selfcheck",
            priority = 10011)
    public void detectDefaultStoresDrawer() {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().quickLogout();

        //landing
        kraken.shipAddress().set(Addresses.Moscow.testAddress());

        softAssert.assertTrue(kraken.detect().isStoreSelectorOpen(),
                "\nНе открывается дефолтный селектор магазинов на лендинге");

        softAssert.assertFalse(kraken.detect().isStoreSelectorEmpty(),
                "\nПусто в дефолтном селекторе магазинов на лендинге");

        kraken.shopping().closeStoreSelector();

        softAssert.assertFalse(kraken.detect().isStoreSelectorOpen(),
                "\nНе закрывается дефолтный селектор магазинов на лендинге");

        //retailer
        kraken.get().page("metro");
        kraken.shopping().openStoreSelector();

        softAssert.assertTrue(kraken.detect().isStoreSelectorOpen(),
                "\nНе открывается дефолтный селектор магазинов на витрине ритейлера");

        softAssert.assertFalse(kraken.detect().isStoreSelectorEmpty(),
                "\nПусто в дефолтном селекторе магазинов на витрине ритейлера");

        kraken.shopping().closeStoreSelector();

        softAssert.assertFalse(kraken.detect().isStoreSelectorOpen(),
                "\nНе закрывается дефолтный селектор магазинов на витрине ритейлера");

        softAssert.assertAll();
    }


    @Test(description = "Тест корректности определения пустого селектора магазинов",
            groups ="selfcheck",
            priority = 10012)
    public void detectEmptyStoresDrawer() {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().quickLogout();

        //landing
        kraken.shipAddress().set(Addresses.Moscow.outOfZoneAddress());

        softAssert.assertTrue(kraken.detect().isStoreSelectorOpen(),
                "\nНе открывается пустой селектор магазинов на лендинге");

        softAssert.assertFalse(kraken.detect().isStoreSelectorEmpty(),
                "\nНе определяется пустой селектор магазинов на лендинге");

        kraken.shopping().closeStoreSelector();

        softAssert.assertFalse(kraken.detect().isStoreSelectorOpen(),
                "\nНе закрывается пустой селектор магазинов на лендинге");

        //retailer
        kraken.get().page("metro");
        kraken.shopping().openStoreSelector();

        softAssert.assertTrue(kraken.detect().isStoreSelectorOpen(),
                "\nНе открывается пустой селектор магазинов на витрине ритейлера");

        softAssert.assertFalse(kraken.detect().isStoreSelectorEmpty(),
                "\nНе определяется пустой селектор магазинов на витрине ритейлера");

        kraken.shopping().closeStoreSelector();

        softAssert.assertFalse(kraken.detect().isStoreSelectorOpen(),
                "\nНе закрывается пустой селектор магазинов на витрине ритейлера");

        softAssert.assertAll();
    }


    @Test(description = "Тест корректности определения шторки корзины",
            groups ="selfcheck",
            priority = 10013)
    public void detectCartDrawer() throws Exception {

        kraken.get().page("metro");

        kraken.shopping().openCart();
        Assert.assertTrue(kraken.detect().isCartOpen());

        kraken.shopping().closeCart();
        Assert.assertFalse(kraken.detect().isCartOpen());
    }


    @Test(description = "Тест корректности определения модалки Доставка",
            groups ="selfcheck",
            priority = 10014)
    public void detectDeliveryModal() throws Exception {

        kraken.get().page("metro");

        kraken.perform().click(Elements.Site.Header.deliveryButton());
        Assert.assertTrue(kraken.detect().isDeliveryModalOpen());

        kraken.perform().click(Elements.Site.DeliveryModal.closeButton());
        Assert.assertFalse(kraken.detect().isDeliveryModalOpen());
    }


    @Test(description = "Тест корректности определения модалки Партнеры",
            groups ="selfcheck",
            priority = 10015)
    public void detectPartnersModal() throws Exception {

        kraken.get().page("metro");

        kraken.perform().click(Elements.Site.Header.partnersButton());
        Assert.assertTrue(kraken.detect().isPartnersModalOpen());

        kraken.perform().click(Elements.Site.PartnersModal.closeButton());
        Assert.assertFalse(kraken.detect().isPartnersModalOpen());
    }


    @Test(description = "Тест корректности определения модалки Оплата",
            groups ="selfcheck",
            priority = 10016)
    public void detectPaymentModal() throws Exception {

        kraken.get().page("metro");

        kraken.perform().click(Elements.Site.Footer.paymentButton());
        Assert.assertTrue(kraken.detect().isPaymentModalOpen());

        kraken.perform().click(Elements.Site.PaymentModal.closeButton());
        Assert.assertFalse(kraken.detect().isPaymentModalOpen());
    }


     @Test(description = "Тест корректности определения модалки Адрес",
            groups ="selfcheck",
            priority = 10017)
     public void detectAddressModal() throws Exception {

        kraken.get().page("metro");

        kraken.shipAddress().openAddressModal();
        Assert.assertTrue(kraken.detect().isAddressModalOpen());

        kraken.shipAddress().closeAddressModal();
        Assert.assertFalse(kraken.detect().isAddressModalOpen());
    }

    @Test(description = "Тест корректности определения суммы корзины",
            groups ="selfcheck",
            priority = 10018)
    public void detectCartTotal() throws Exception {

        kraken.get().page("metro");

        if (!kraken.detect().isShippingAddressSet()) {
            kraken.shipAddress().set(Addresses.Moscow.defaultAddress());}

        if (!kraken.detect().isCartEmpty()) {
            kraken.perform().dropCart();}

        // корзина пустая
        Assert.assertFalse(kraken.detect().isCartTotalDisplayed());
        Assert.assertNull(kraken.grab().currentCartTotal());
        kraken.perform().printMessage("Сумма корзины = " + kraken.grab().currentCartTotal());

        // корзина не пустая, но меньше суммы мин заказа
        kraken.shopping().closeCart();
        kraken.search().item("хлеб");
        kraken.shopping().collectItems(1);
        Assert.assertTrue(kraken.detect().isCartTotalDisplayed());
        Assert.assertNotNull(kraken.grab().currentCartTotal());
        kraken.perform().printMessage("Сумма корзины = " + kraken.grab().currentCartTotal());

        // корзина не пустая, больше суммы мин заказа
        kraken.shopping().collectItems();
        Assert.assertTrue(kraken.detect().isCartTotalDisplayed());
        kraken.perform().printMessage("Сумма корзины = " + kraken.grab().currentCartTotal());
    }

}

