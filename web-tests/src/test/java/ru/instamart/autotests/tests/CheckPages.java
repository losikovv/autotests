package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Pages;



    // Проверка доступности страниц



public class CheckPages extends TestBase {


    @Test(
            description = "Тест доступности витрин активных рителйеров",
            groups = {"smoke","acceptance","regression"},
            priority = 801
    )
    // TODO переделать чек страниц по списку ретейлеров
    // TODO забирать список ритейлеров из БД или из админки с признаком активности
    public void checkActiveRetailerPages() throws Exception, AssertionError {
        assertPageIsAvailable(Pages.Site.Retailers.metro());
        assertPageIsAvailable(Pages.Site.Retailers.vkusvill());
        assertPageIsAvailable(Pages.Site.Retailers.lenta());
        assertPageIsAvailable(Pages.Site.Retailers.karusel());
        assertPageIsAvailable(Pages.Site.Retailers.auchan());
    }

    @Test(
            description = "Тест недоступности витрин неактивных рителйеров",
            groups = {"smoke","acceptance","regression"},
            priority = 802
    )
    // TODO переделать чек страниц по списку ретейлеров
    // TODO забирать список ритейлеров из БД или из админки с признаком активности
    public void checkInactiveRetailerPages() throws Exception, AssertionError {
        assertPageIs404(Pages.Site.Retailers.selgros());
        assertPageIs404(Pages.Site.Retailers.flora());
        assertPageIs404(Pages.Site.Retailers.foodcity());
        assertPageIs404(Pages.Site.Retailers.magnit());
        assertPageIs404(Pages.Site.Retailers.testretailer());
    }


    @Test(
            description = "Тест доступности партнерских лендингов",
            groups = {"smoke","acceptance","regression"},
            priority = 803
    )
    // TODO переделать чек лендингов циклом по списку
    public void checkLandings() throws Exception, AssertionError {
        assertPageIsAvailable(Pages.Site.Landings.instamart());
        assertPageIsAvailable(Pages.Site.Landings.mnogoru());
        assertPageIsAvailable(Pages.Site.Landings.sovest());
        assertPageIsAvailable(Pages.Site.Landings.halva());

        // assertPageIsAvailable(Pages.Site.Landings.kazan());      лендос отключен
        //assertPageIsAvailable(Pages.Site.Landings.feedback());    этого лендоса нет на стейдже
        //assertPageIsAvailable(Pages.Site.Landings.mobile());      этого лендоса нет на стейдже
    }


    @Test(
            description = "Тест доступности статических страниц",
            groups = {"smoke","acceptance","regression"},
            priority = 804
    )
    // TODO переделать чек страниц циклом по списку
    public void checkStaticPages() throws Exception, AssertionError {
        assertPageIsAvailable(Pages.Site.Static.about());
        assertPageIsAvailable(Pages.Site.Static.delivery());
        assertPageIsAvailable(Pages.Site.Static.rules());
        assertPageIsAvailable(Pages.Site.Static.payment());
        assertPageIsAvailable(Pages.Site.Static.returnPolicy());
        assertPageIsAvailable(Pages.Site.Static.faq());
        assertPageIsAvailable(Pages.Site.Static.terms());
        assertPageIsAvailable(Pages.Site.Static.contacts());
    }


    @Test(
            description = "Тест доступности страниц профиля пользователя",
            groups = {"smoke","acceptance","regression"},
            priority = 805
    )
    public void checkProfilePages() throws Exception, AssertionError {
        app.getHelper().getBaseUrl();
        app.getSessionHelper().doLoginAs("admin");

        assertPageIsAvailable(Pages.Site.Profile.edit());
        assertPageIsAvailable(Pages.Site.Profile.orders());
        assertPageIsAvailable(Pages.Site.Profile.addresses());
    }




    @Test(
            description = "Тест доступности корневых разделов админки",
            groups = {"smoke","acceptance","regression"},
            priority = 806
    )
    public void checkAdminPages() throws Exception, AssertionError {
        app.getSessionHelper().reachAdmin(Pages.Admin.shipments());

        assertPageIsAvailable(Pages.Admin.shipments());
        assertPageIsAvailable(Pages.Admin.retailers());
        assertPageIsAvailable(Pages.Admin.products());
        assertPageIsAvailable(Pages.Admin.imports());
        assertPageIsAvailable(Pages.Admin.reports());
        assertPageIsAvailable(Pages.Admin.settings());
        assertPageIsAvailable(Pages.Admin.marketing());
        assertPageIsAvailable(Pages.Admin.users());
        assertPageIsAvailable(Pages.Admin.pages());
    }


    @Test(
            description = "Тест доступности страниц футера",
            groups = {"smoke","acceptance","regression"},
            priority = 807
    )
    public void checkFooterPages() throws Exception {
        app.getHelper().getBaseUrl();

        app.getNavigationHelper().goFooterAboutCompany();
        assertPageIsAvailable();

        app.getNavigationHelper().goFooterContacts();
        assertPageIsAvailable();

        app.getNavigationHelper().goFooterFaq();
        assertPageIsAvailable();

        app.getNavigationHelper().goFooterFeedbackForm();
        assertPageIsAvailable();

        app.getNavigationHelper().goFooterReturnPolicy();
        assertPageIsAvailable();

        app.getNavigationHelper().goFooterPublicOffer();
        assertPageIsAvailable();

        app.getNavigationHelper().goFooterDelivery();
        Assert.assertTrue(app.getHelper().isDeliveryPopupOpen(),
                "Cant assert 'Delivery' pop-up open, check manually\n");
        app.getHelper().click(Elements.Site.DeliveryPopup.closeButton());
        assertPageIsAvailable();

        app.getNavigationHelper().goFooterPartners();
        Assert.assertTrue(app.getHelper().isPartnersPopupOpen(),
                "Cant assert 'Partners' pop-up open, check manually\n");
        app.getHelper().click(Elements.Site.PartnersPopup.closeButton());
        assertPageIsAvailable();

        /* Тест временно отключен - валится потому что кнопка goTop перекрывает ссылку
        app.getNavigationHelper().goFooterPayment();
        Assert.assertTrue(app.getHelper().isPaymentPopupOpen(),
                "Cant assert 'Payment' pop-up open, check manually\n");
        app.getHelper().click(Elements.Site.PaymentPopup.closeButton());
        assertPageIsAvailable();
        */
    }

}
