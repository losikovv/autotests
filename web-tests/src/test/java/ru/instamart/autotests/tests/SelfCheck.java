package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Pages;
import ru.instamart.autotests.testdata.Addresses;



// Тесты самопроверки кракена



public class SelfCheck extends TestBase {


    @Test(description = "Тест базового URL", priority = 10000)
    public void initialCheck() throws Exception {
        kraken.get().baseUrl();
        Assert.assertEquals(kraken.perform().fetchCurrentURL() , kraken.perform().fullBaseUrl);
    }


    @Test(description = "Тест корректности работы методов навигации", priority = 10001)
    public void checkNavigation() throws Exception {

        kraken.get().page("metro");
        Assert.assertEquals(kraken.perform().fetchCurrentURL() , kraken.perform().fullBaseUrl + "metro");


        kraken.get().page(Pages.Site.Static.faq());
        Assert.assertEquals(kraken.perform().fetchCurrentURL() , kraken.perform().fullBaseUrl + Pages.getPagePath());
        // TODO проверка GO методов
    }


    @Test(description = "Тест корректности определения модалки авторизации/регистрации", priority = 10002)
    public void detectAuthModal() throws Exception {

        kraken.get().baseUrl();

        kraken.perform().openAuthModal();
        Assert.assertTrue(kraken.detect().isAuthModalOpen());

        kraken.perform().closeAuthModal();
        Assert.assertFalse(kraken.detect().isAuthModalOpen());

        kraken.get().retailerPage("metro");

        kraken.perform().openAuthModal();
        Assert.assertTrue(kraken.detect().isAuthModalOpen());

        kraken.perform().closeAuthModal();
        Assert.assertFalse(kraken.detect().isAuthModalOpen());
    }


    @Test(description = "Тест корректности определения авторизованности пользователя", priority = 10003)
    public void detectAuthorisation() throws Exception {

        kraken.perform().dropAuth();

        kraken.get().baseUrl();
        Assert.assertFalse(kraken.detect().isUserAuthorised());

        kraken.get().retailerPage("metro");
        Assert.assertFalse(kraken.detect().isUserAuthorised());

        kraken.perform().doLoginAs("admin");
        Assert.assertTrue(kraken.detect().isUserAuthorised());

        kraken.perform().logout();
        Assert.assertFalse(kraken.detect().isUserAuthorised());

    }


    // TODO public void detectAddressModal() throws Exception { }


    @Test(description = "Тест корректности определения меню Профиль", priority = 10004)
    public void detectAccountMenu() throws Exception {

        kraken.get().baseUrl();
        kraken.perform().doLoginAs("admin");

        kraken.perform().openAccountMenu();
        Assert.assertTrue(kraken.detect().isAccountMenuOpen());

        kraken.perform().closeAccountMenu();
        Assert.assertFalse(kraken.detect().isAccountMenuOpen());
    }


    @Test(description = "Тест корректности определения карточки товара", priority = 10005)
    public void detectItemCard() throws Exception {

        kraken.get().page("metro/salat-premium-gorshochek");
        Assert.assertTrue(kraken.getShoppingHelper().isItemCardOpen());

        kraken.get().page("metro/eliektronika");
        Assert.assertFalse(kraken.getShoppingHelper().isItemCardOpen());
    }


    @Test(description = "Тест корректности определения что находимся на сайте", priority = 10006)
    public void detectIsOnSite() throws Exception {

        kraken.get().page(Pages.Site.Static.faq());
        Assert.assertTrue(kraken.detect().isOnSite());

        kraken.perform().doLoginAs("admin");
        kraken.get().page(Pages.Admin.retailers());
        Assert.assertFalse(kraken.detect().isOnSite());
    }


    @Test(description = "Тест корректности определения что находимся в админке", priority = 10007)
    public void detectIsInAdmin() throws Exception {

        kraken.get().page(Pages.Site.Static.contacts());
        Assert.assertFalse(kraken.detect().isInAdmin());

        kraken.perform().doLoginAs("admin");
        kraken.get().page(Pages.Admin.settings());
        Assert.assertTrue(kraken.detect().isInAdmin());
    }


    @Test(description = "Тест корректности определения 404 ошибки на страниице", priority = 10008)
    public void detect404() throws Exception {

        kraken.get().page("nowhere");
        Assert.assertTrue(kraken.detect().is404());

        kraken.get().page("metro");
        Assert.assertFalse(kraken.detect().is404());
    }


    @Test(description = "Тест корректности определения 500 ошибки на страниице", priority = 10009)
    public void detect500() throws Exception {

        kraken.get().page("stores/21/shipping_methods");
        Assert.assertTrue(kraken.detect().is500());

        kraken.get().page("metro");
        Assert.assertFalse(kraken.detect().is500());
    }


    @Test(description = "Тест корректности определения шторки Каталога", priority = 10010)
    public void detectCatalogDrawer() throws Exception {

        kraken.get().page("metro");

        kraken.getShoppingHelper().openCatalog();
        Assert.assertTrue(kraken.getShoppingHelper().isCatalogDrawerOpen());

        kraken.getShoppingHelper().closeCatalog();
        Assert.assertFalse(kraken.getShoppingHelper().isCatalogDrawerOpen());
    }


    @Test(description = "Тест корректности определения шторки магазинов", priority = 10011)
    public void detectShopsDrawer() throws Exception {

        kraken.get().baseUrl();
        if(kraken.detect().isUserAuthorised()) {
            kraken.perform().logout();
        }

        //landing
        kraken.get().baseUrl();

        kraken.getShoppingHelper().setShippingAddress(Addresses.Moscow.testAddress());
        Assert.assertTrue(kraken.getShoppingHelper().isShopSelectorOpen());

        kraken.getShoppingHelper().closeShopSelector();
        Assert.assertFalse(kraken.getShoppingHelper().isShopSelectorOpen());

        //retailer
        kraken.get().page("metro");

        kraken.getShoppingHelper().openShopSelector();
        Assert.assertTrue(kraken.getShoppingHelper().isShopSelectorOpen());

        kraken.getShoppingHelper().closeShopSelector();
        Assert.assertFalse(kraken.getShoppingHelper().isShopSelectorOpen());
    }


    @Test(description = "Тест корректности определения шторки корзины", priority = 10012)
    public void detectCartDrawer() throws Exception {

        kraken.get().page("metro");

        kraken.getShoppingHelper().openCart();
        Assert.assertTrue(kraken.getShoppingHelper().isCartOpen());

        kraken.getShoppingHelper().closeCart();
        Assert.assertFalse(kraken.getShoppingHelper().isCartOpen());
    }


    @Test(description = "Тест корректности определения модалки Доставка", priority = 10013)
    public void detectDeliveryModal() throws Exception {

        kraken.get().page("metro");

        kraken.perform().click(Elements.Site.Header.deliveryButton());
        Assert.assertTrue(kraken.detect().isDeliveryModalOpen());

        kraken.perform().click(Elements.Site.DeliveryModal.closeButton());
        Assert.assertFalse(kraken.detect().isDeliveryModalOpen());
    }


    @Test(description = "Тест корректности определения модалки Партнеры", priority = 10014)
    public void detectPartnersModal() throws Exception {

        kraken.get().page("metro");

        kraken.perform().click(Elements.Site.Header.partnersButton());
        Assert.assertTrue(kraken.detect().isPartnersModalOpen());

        kraken.perform().click(Elements.Site.PartnersModal.closeButton());
        Assert.assertFalse(kraken.detect().isPartnersModalOpen());
    }

    // TODO detectPaymentModal

    // TODO detectAddressModal

}

