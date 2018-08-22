package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;



// Тесты самопроверки кракена



public class SelfCheck extends TestBase {


    @Test(description = "Тест корректности определения 500 ошибки на страниице")
    public void detect500() throws Exception {

        app.getNavigationHelper().getPage("// TODO стабильно пятисотящая страница ");
        Assert.assertTrue(app.getHelper().is500());

        app.getNavigationHelper().getPage("metro");
        Assert.assertFalse(app.getHelper().is500());
    }


    @Test(description = "Тест корректности определения 404 ошибки на страниице")
    public void detect404() throws Exception {

        app.getNavigationHelper().getPage("nowhere");
        Assert.assertTrue(app.getHelper().is404());

        app.getNavigationHelper().getPage("metro");
        Assert.assertFalse(app.getHelper().is404());
    }


    // TODO public void detectAuthModal() throws Exception { }

    // TODO public void detectAddressModal() throws Exception { }

    // TODO public void detectAccountMenu() throws Exception { }


    @Test(description = "Тест корректности определения карточки товара")
    public void detectItemCard() throws Exception {

        app.getNavigationHelper().getPage("/metro/soda-pishchievaia");
        Assert.assertTrue(app.getShoppingHelper().isItemCardOpen());

        app.getNavigationHelper().getPage("/metro/eliektronika");
        Assert.assertFalse(app.getShoppingHelper().isItemCardOpen());
    }

    // TODO public void detectDeliveryPopup() throws Exception { }

    // TODO public void detectPartnersPopup() throws Exception { }

    // TODO public void detectShopsDrawer() throws Exception { }

    // TODO public void detectCatalogDrawer() throws Exception { }

    // TODO public void detectCartDrawer() throws Exception { }


}
