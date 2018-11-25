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
        assertPageIsAvailable(Pages.Site.Landings.aeroflot());

        //assertPageIsAvailable(Pages.Site.Landings.sovest());      старый лендос
        //assertPageIsAvailable(Pages.Site.Landings.halva());       старый лендос

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
        app.perform().getBaseUrl();
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


// TOdO перенести в отдельный тест checkLinks (на место cleanup - его перенести в stop())
    // TOdO добаавить тест ссылок хедера на главной

    @Test(
            description = "Тест работоспособности ссылок футера",
            groups = {"smoke","acceptance","regression"},
            priority = 807
    )
    public void checkFooterLinks() throws Exception {
        app.perform().getBaseUrl();

        // TOdO нужен метод, проверяющий переход по ссылке и включающий проверку что начальная и конечная страницы не одинаковые
        app.perform().click(Elements.Site.Footer.aboutCompanyButton());
        assertPageIsAvailable();

        app.perform().click(Elements.Site.Footer.contactsButton());
        assertPageIsAvailable();

        app.perform().click(Elements.Site.Footer.faqButton());
        assertPageIsAvailable();

        app.perform().click(Elements.Site.Footer.feedbackFormButton());
        assertPageIsAvailable();

        app.perform().click(Elements.Site.Footer.returnPolicyButton());
        assertPageIsAvailable();

        app.perform().click(Elements.Site.Footer.publicOfferButton());
        assertPageIsAvailable();

        app.perform().click(Elements.Site.Footer.deliveryButton());
        Assert.assertTrue(app.perform().isDeliveryPopupOpen(),
                "Cant assert 'Delivery' pop-up open, check manually\n");
        app.perform().click(Elements.Site.DeliveryPopup.closeButton());
        assertPageIsAvailable();

        app.perform().click(Elements.Site.Footer.partnersButton());
        Assert.assertTrue(app.perform().isPartnersPopupOpen(),
                "Cant assert 'Partners' pop-up open, check manually\n");
        app.perform().click(Elements.Site.PartnersPopup.closeButton());
        assertPageIsAvailable();

        app.perform().click(Elements.Site.Footer.paymentButton());
        Assert.assertTrue(app.perform().isPaymentPopupOpen(),
                "Cant assert 'Payment' pop-up open, check manually\n");
        app.perform().click(Elements.Site.PaymentPopup.closeButton());
        assertPageIsAvailable();

    }

}
