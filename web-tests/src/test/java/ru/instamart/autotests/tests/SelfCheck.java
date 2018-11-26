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
        kraken.perform().getBaseUrl();
        Assert.assertEquals(kraken.perform().currentURL() , kraken.perform().fullBaseUrl);
    }


    @Test(description = "Тест корректности работы методов навигации", priority = 10001)
    public void checkNavigation() throws Exception {

        kraken.getNavigationHelper().get("metro");
        Assert.assertEquals(kraken.perform().currentURL() , kraken.perform().fullBaseUrl + "metro");


        kraken.getNavigationHelper().get(Pages.Site.Static.faq());
        Assert.assertEquals(kraken.perform().currentURL() , kraken.perform().fullBaseUrl + Pages.getPagePath());
        // TODO проверка GO методов
    }


    @Test(description = "Тест корректности определения модалки авторизации/регистрации", priority = 10002)
    public void detectAuthModal() throws Exception {

        kraken.perform().getBaseUrl();

        kraken.getSessionHelper().openAuthModal();
        Assert.assertTrue(kraken.getSessionHelper().isAuthModalOpen());

        kraken.getSessionHelper().closeAuthModal();
        Assert.assertFalse(kraken.getSessionHelper().isAuthModalOpen());

        kraken.getNavigationHelper().getRetailerPage("metro");

        kraken.getSessionHelper().openAuthModal();
        Assert.assertTrue(kraken.getSessionHelper().isAuthModalOpen());

        kraken.getSessionHelper().closeAuthModal();
        Assert.assertFalse(kraken.getSessionHelper().isAuthModalOpen());
    }


    @Test(description = "Тест корректности определения авторизованности пользователя", priority = 10003)
    public void detectAuthorisation() throws Exception {

        kraken.getSessionHelper().dropAuth();

        kraken.getNavigationHelper().getBaseUrl();
        Assert.assertFalse(kraken.getSessionHelper().isUserAuthorised());

        kraken.getNavigationHelper().getRetailerPage("metro");
        Assert.assertFalse(kraken.getSessionHelper().isUserAuthorised());

        kraken.getSessionHelper().doLoginAs("admin");
        Assert.assertTrue(kraken.getSessionHelper().isUserAuthorised());

        kraken.getSessionHelper().doLogout();
        Assert.assertFalse(kraken.getSessionHelper().isUserAuthorised());

    }


    // TODO public void detectAddressModal() throws Exception { }


    @Test(description = "Тест корректности определения меню Профиль", priority = 10004)
    public void detectAccountMenu() throws Exception {

        kraken.perform().getBaseUrl();
        kraken.getSessionHelper().doLoginAs("admin");

        kraken.perform().openAccountMenu();
        Assert.assertTrue(kraken.detect().isAccountMenuOpen());

        kraken.perform().closeAccountMenu();
        Assert.assertFalse(kraken.detect().isAccountMenuOpen());
    }


    @Test(description = "Тест корректности определения карточки товара", priority = 10005)
    public void detectItemCard() throws Exception {

        kraken.getNavigationHelper().get("metro/salat-premium-gorshochek");
        Assert.assertTrue(kraken.getShoppingHelper().isItemCardOpen());

        kraken.getNavigationHelper().get("metro/eliektronika");
        Assert.assertFalse(kraken.getShoppingHelper().isItemCardOpen());
    }


    @Test(description = "Тест корректности определения что находимся на сайте", priority = 10006)
    public void detectIsOnSite() throws Exception {

        kraken.getNavigationHelper().get(Pages.Site.Static.faq());
        Assert.assertTrue(kraken.detect().isOnSite());

        kraken.getSessionHelper().doLoginAs("admin");
        kraken.getNavigationHelper().get(Pages.Admin.retailers());
        Assert.assertFalse(kraken.detect().isOnSite());
    }


    @Test(description = "Тест корректности определения что находимся в админке", priority = 10007)
    public void detectIsInAdmin() throws Exception {

        kraken.getNavigationHelper().get(Pages.Site.Static.contacts());
        Assert.assertFalse(kraken.detect().isInAdmin());

        kraken.getSessionHelper().doLoginAs("admin");
        kraken.getNavigationHelper().get(Pages.Admin.settings());
        Assert.assertTrue(kraken.detect().isInAdmin());
    }


    @Test(description = "Тест корректности определения 404 ошибки на страниице", priority = 10008)
    public void detect404() throws Exception {

        kraken.getNavigationHelper().get("nowhere");
        Assert.assertTrue(kraken.detect().is404());

        kraken.getNavigationHelper().get("metro");
        Assert.assertFalse(kraken.detect().is404());
    }


    @Test(description = "Тест корректности определения 500 ошибки на страниице", priority = 10009)
    public void detect500() throws Exception {

        kraken.getNavigationHelper().get("stores/21/shipping_methods");
        Assert.assertTrue(kraken.detect().is500());

        kraken.getNavigationHelper().get("metro");
        Assert.assertFalse(kraken.detect().is500());
    }


    @Test(description = "Тест корректности определения шторки Каталога", priority = 10010)
    public void detectCatalogDrawer() throws Exception {

        kraken.getNavigationHelper().get("metro");

        kraken.getShoppingHelper().openCatalog();
        Assert.assertTrue(kraken.getShoppingHelper().isCatalogDrawerOpen());

        kraken.getShoppingHelper().closeCatalog();
        Assert.assertFalse(kraken.getShoppingHelper().isCatalogDrawerOpen());
    }


    @Test(description = "Тест корректности определения шторки магазинов", priority = 10011)
    public void detectShopsDrawer() throws Exception {

        kraken.getNavigationHelper().getBaseUrl();
        if(kraken.getSessionHelper().isUserAuthorised()) {
            kraken.getSessionHelper().doLogout();
        }

        //landing
        kraken.getNavigationHelper().getBaseUrl();

        kraken.getShoppingHelper().setShippingAddress(Addresses.Moscow.testAddress());
        Assert.assertTrue(kraken.getShoppingHelper().isShopSelectorOpen());

        kraken.getShoppingHelper().closeShopSelector();
        Assert.assertFalse(kraken.getShoppingHelper().isShopSelectorOpen());

        //retailer
        kraken.getNavigationHelper().get("metro");

        kraken.getShoppingHelper().openShopSelector();
        Assert.assertTrue(kraken.getShoppingHelper().isShopSelectorOpen());

        kraken.getShoppingHelper().closeShopSelector();
        Assert.assertFalse(kraken.getShoppingHelper().isShopSelectorOpen());
    }


    @Test(description = "Тест корректности определения шторки корзины", priority = 10012)
    public void detectCartDrawer() throws Exception {

        kraken.getNavigationHelper().get("metro");

        kraken.getShoppingHelper().openCart();
        Assert.assertTrue(kraken.getShoppingHelper().isCartOpen());

        kraken.getShoppingHelper().closeCart();
        Assert.assertFalse(kraken.getShoppingHelper().isCartOpen());
    }


    @Test(description = "Тест корректности определения модалки Доставка", priority = 10013)
    public void detectDeliveryModal() throws Exception {

        kraken.getNavigationHelper().get("metro");

        kraken.perform().click(Elements.Site.Header.deliveryButton());
        Assert.assertTrue(kraken.detect().isDeliveryModalOpen());

        kraken.perform().click(Elements.Site.DeliveryModal.closeButton());
        Assert.assertFalse(kraken.detect().isDeliveryModalOpen());
    }


    @Test(description = "Тест корректности определения модалки Партнеры", priority = 10014)
    public void detectPartnersModal() throws Exception {

        kraken.getNavigationHelper().get("metro");

        kraken.perform().click(Elements.Site.Header.partnersButton());
        Assert.assertTrue(kraken.detect().isPartnersModalOpen());

        kraken.perform().click(Elements.Site.PartnersModal.closeButton());
        Assert.assertFalse(kraken.detect().isPartnersModalOpen());
    }

    // TODO detectPaymentModal

    // TODO detectAddressModal

}

