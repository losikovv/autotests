package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.configuration.Pages;
import ru.instamart.autotests.testdata.Addresses;



// Тесты самопроверки кракена



public class SelfCheck extends TestBase {


    @Test(description = "Тест базового URL", priority = 10000)
    public void initialCheck() throws Exception {
        app.getHelper().getBaseUrl();
        Assert.assertTrue(app.getHelper().currentURL().equals(app.getHelper().baseUrl()));
    }


    @Test(description = "Тест корректности работы методов навигации", priority = 10001)
    public void checkNavigation() throws Exception {

        app.getNavigationHelper().get("metro");
        Assert.assertTrue(app.getHelper().currentURL().equals(app.getHelper().baseUrl() + "metro"));

        app.getNavigationHelper().get(Pages.Site.Static.faq());
        Assert.assertTrue(app.getHelper().currentURL().equals(app.getHelper().baseUrl() + Pages.getPagePath()));

        // TODO проверка GO методов
    }


    @Test(description = "Тест корректности определения модалки авторизации/регистрации", priority = 10002)
    public void detectAuthModal() throws Exception {

        app.getHelper().getBaseUrl();

        app.getSessionHelper().openAuthModal();
        Assert.assertTrue(app.getSessionHelper().isAuthModalOpen());

        app.getSessionHelper().closeAuthModal();
        Assert.assertFalse(app.getSessionHelper().isAuthModalOpen());

        app.getNavigationHelper().getRetailerPage("metro");

        app.getSessionHelper().openAuthModal();
        Assert.assertTrue(app.getSessionHelper().isAuthModalOpen());

        app.getSessionHelper().closeAuthModal();
        Assert.assertFalse(app.getSessionHelper().isAuthModalOpen());
    }


    @Test(description = "Тест корректности определения авторизованности пользователя", priority = 10003)
    public void detectAuthorisation() throws Exception {

        app.getSessionHelper().dropAuth();

        app.getNavigationHelper().getBaseUrl();
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised());

        app.getNavigationHelper().getRetailerPage("metro");
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised());

        app.getSessionHelper().doLoginAs("admin");
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised());

        app.getSessionHelper().doLogout();
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised());
    }


    // TODO public void detectAddressModal() throws Exception { }


    // TODO public void detectCartDrawer() throws Exception { }

    // TODO public void detectDeliveryPopup() throws Exception { }

    // TODO public void detectPartnersPopup() throws Exception { }


    @Test(description = "Тест корректности определения меню Профиль", priority = 10004)
    public void detectAccountMenu() throws Exception {

        app.getHelper().getBaseUrl();

        app.getSessionHelper().doLoginAs("admin");
        app.getSessionHelper().openAccountMenu();
        Assert.assertTrue(app.getSessionHelper().isAccountMenuOpen());

        app.getSessionHelper().closeAccountMenu();
        Assert.assertFalse(app.getSessionHelper().isAccountMenuOpen());
    }


    @Test(description = "Тест корректности определения карточки товара", priority = 10005)
    public void detectItemCard() throws Exception {

        app.getNavigationHelper().get("metro/soda-pishchievaia");
        Assert.assertTrue(app.getShoppingHelper().isItemCardOpen());

        app.getNavigationHelper().get("metro/eliektronika");
        Assert.assertFalse(app.getShoppingHelper().isItemCardOpen());
    }


    @Test(description = "Тест корректности определения что находимся на сайте", priority = 10006)
    public void detectIsOnSite() throws Exception {

        app.getNavigationHelper().get(Pages.Site.Static.faq());
        Assert.assertTrue(app.getHelper().isOnSite());

        app.getSessionHelper().doLoginAs("admin");
        app.getNavigationHelper().get(Pages.Admin.retailers());
        Assert.assertFalse(app.getHelper().isOnSite());
    }


    @Test(description = "Тест корректности определения что находимся в админке", priority = 10007)
    public void detectIsInAdmin() throws Exception {

        app.getNavigationHelper().get(Pages.Site.Static.contacts());
        Assert.assertFalse(app.getHelper().isInAdmin());

        app.getSessionHelper().doLoginAs("admin");
        app.getNavigationHelper().get(Pages.Admin.settings());
        Assert.assertTrue(app.getHelper().isInAdmin());
    }


    @Test(description = "Тест корректности определения 404 ошибки на страниице", priority = 10008)
    public void detect404() throws Exception {

        app.getNavigationHelper().get("nowhere");
        Assert.assertTrue(app.getHelper().is404());

        app.getNavigationHelper().get("metro");
        Assert.assertFalse(app.getHelper().is404());
    }


    @Test(description = "Тест корректности определения 500 ошибки на страниице", priority = 10009)
    public void detect500() throws Exception {

        app.getNavigationHelper().get("// TODO стабильно пятисотящая страница ");
        Assert.assertTrue(app.getHelper().is500());

        app.getNavigationHelper().get("metro");
        Assert.assertFalse(app.getHelper().is500());
    }


    @Test(description = "Тест корректности определения шторки Каталога", priority = 10010)
    public void detectCatalogDrawer() throws Exception {

        app.getNavigationHelper().get("metro");
        app.getShoppingHelper().openCatalog();
        Assert.assertTrue(app.getShoppingHelper().isCatalogDrawerOpen());

        app.getShoppingHelper().closeCatalog();
        Assert.assertFalse(app.getShoppingHelper().isCatalogDrawerOpen());

    }

    @Test(description = "Тест корректности определения шторки магазинов", priority = 10011)
    public void detectShopsDrawer() throws Exception {

        //landing
        app.getNavigationHelper().getBaseUrl();
        app.getShoppingHelper().setShippingAddress(Addresses.Moscow.testAddress());
        Assert.assertTrue(app.getShoppingHelper().isShopSelectorOpen());

        app.getShoppingHelper().closeShopSelector();
        Assert.assertFalse(app.getShoppingHelper().isShopSelectorOpen());


        //retailer
        app.getNavigationHelper().get("metro");
        app.getShoppingHelper().openShopSelector();
        Assert.assertTrue(app.getShoppingHelper().isShopSelectorOpen());

        app.getShoppingHelper().closeShopSelector();
        Assert.assertFalse(app.getShoppingHelper().isShopSelectorOpen());
    }

    @Test(description = "Тест корректности определения шторки корзины", priority = 10012)
    public void detectCartDrawer() throws Exception {

        app.getNavigationHelper().get("metro");
        app.getShoppingHelper().openCart();
        Assert.assertTrue(app.getShoppingHelper().isCartOpen());

        app.getShoppingHelper().closeCart();
        Assert.assertFalse(app.getShoppingHelper().isCartOpen());

    }

    @Test(description = "Тест корректности определения попапа доставки", priority = 10013)
    public void detectDeliveryPopup() throws Exception {

        app.getNavigationHelper().get("metro");
        app.getHelper().openDeliveryPopup();
        Assert.assertTrue(app.getHelper().isDeliveryPopupOpened());

        app.getHelper().closeDeliveryPopup();
        Assert.assertFalse(app.getHelper().isDeliveryPopupOpened());


    }

    @Test(description = "Тест корректности определения попапа партнерских программ", priority = 10014)
    public void detectPartnersPopup() throws Exception {

        app.getNavigationHelper().get("metro");
        app.getHelper().openPartnersPopup();
        Assert.assertTrue(app.getHelper().isPartnersPopupOpened());

        app.getHelper().closePartnersPopup();
        Assert.assertFalse(app.getHelper().isPartnersPopupOpened());


    }


}

