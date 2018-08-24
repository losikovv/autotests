package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.configuration.Pages;



// Тесты самопроверки кракена



public class SelfCheck extends TestBase {

    @Test(description = "Тест базового URL")
    public void initialCheck() throws Exception {
        app.getHelper().getBaseUrl();
        Assert.assertTrue(app.getHelper().currentURL().equals(app.getHelper().baseUrl()));
    }


    @Test(description = "Тест корректности работы методов навигации")
    public void checkNavigation() throws Exception {

        app.getNavigationHelper().get("metro");
        Assert.assertTrue(app.getHelper().currentURL().equals(app.getHelper().baseUrl() + "metro"));

        app.getNavigationHelper().get(Pages.Site.Static.faq());
        Assert.assertTrue(app.getHelper().currentURL().equals(app.getHelper().baseUrl() + Pages.getPagePath()));

        // TODO проверка GO методов
    }


    // TODO public void detectIsOnSite

    // TODO public void detectIsInAdmin

    @Test(description = "Тест корректности определения 500 ошибки на страниице")
    public void detect500() throws Exception {

        app.getNavigationHelper().get("// TODO стабильно пятисотящая страница ");
        Assert.assertTrue(app.getHelper().is500());

        app.getNavigationHelper().get("metro");
        Assert.assertFalse(app.getHelper().is500());
    }


    @Test(description = "Тест корректности определения 404 ошибки на страниице")
    public void detect404() throws Exception {

        app.getNavigationHelper().get("nowhere");
        Assert.assertTrue(app.getHelper().is404());

        app.getNavigationHelper().get("metro");
        Assert.assertFalse(app.getHelper().is404());
    }


    @Test(description = "Тест корректности определения авторизованности пользователя")
    public void detectAuthorisation() throws Exception {

        app.getNavigationHelper().getBaseUrl();
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised());

        app.getNavigationHelper().getRetailerPage("metro");
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised());

        app.getSessionHelper().doLoginAs("admin");
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised());

        app.getSessionHelper().doLogout();
        Assert.assertFalse(app.getSessionHelper().isUserAuthorised());
    }


    @Test(description = "Тест корректности определения модалки авторизации/регистрации")
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

    // TODO public void detectAddressModal() throws Exception { }

    // TODO public void detectAccountMenu() throws Exception { }


    @Test(description = "Тест корректности определения карточки товара")
    public void detectItemCard() throws Exception {

        app.getNavigationHelper().get("metro/soda-pishchievaia");
        Assert.assertTrue(app.getShoppingHelper().isItemCardOpen());

        app.getNavigationHelper().get("metro/eliektronika");
        Assert.assertFalse(app.getShoppingHelper().isItemCardOpen());
    }

    // TODO public void detectShopsDrawer() throws Exception { landing + retailer }

    // TODO public void detectCatalogDrawer() throws Exception { }

    // TODO public void detectCartDrawer() throws Exception { }



    // TODO public void detectDeliveryPopup() throws Exception { }

    // TODO public void detectPartnersPopup() throws Exception { }

}
