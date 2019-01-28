package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;


// Проверка доступности страниц


public class CheckPages extends TestBase {


    @Test(
            description = "Тест доступности витрин активных рителйеров",
            groups = {"smoke","acceptance","regression"},
            priority = 1401
    )
    // TODO забирать список ритейлеров из БД или из админки с признаком активности
    public void successCheckActiveRetailerPages() throws Exception, AssertionError {
        // TODO переделать на assertPagesAvailable(Pages.Site.Retailers.*)
        assertPageIsAvailable(Pages.Site.Retailers.metro());
        assertPageIsAvailable(Pages.Site.Retailers.vkusvill());
        assertPageIsAvailable(Pages.Site.Retailers.lenta());
        assertPageIsAvailable(Pages.Site.Retailers.auchan());
    }

    @Test(
            description = "Тест недоступности витрин неактивных рителйеров",
            groups = {"smoke","acceptance","regression"},
            priority = 1402
    )
    // TODO забирать список ритейлеров из БД или из админки с признаком активности
    public void successCheckInactiveRetailerPages() throws Exception, AssertionError {
        // TODO переделать на assertPagesUnavailable(Pages.Site.InactiveRetailers.*)
        assertPageIs404(Pages.Site.Retailers.karusel());
        assertPageIs404(Pages.Site.Retailers.selgros());
        assertPageIs404(Pages.Site.Retailers.flora());
        assertPageIs404(Pages.Site.Retailers.foodcity());
        assertPageIs404(Pages.Site.Retailers.magnit());
        assertPageIs404(Pages.Site.Retailers.testretailer());
    }


    @Test(
            description = "Тест доступности партнерских лендингов",
            groups = {"smoke","acceptance","regression"},
            priority = 1403
    )
    public void successCheckPartnersLandings() throws Exception, AssertionError {
        kraken.perform().dropAuth();

        // TODO переделать на assertPagesAvailable(Pages.Site.Landings.*)
        assertPageIsAvailable(Pages.Site.Landings.mnogoru());
        assertPageIsAvailable(Pages.Site.Landings.aeroflot());

        //assertPageIsAvailable(Pages.Site.Landings.sovest());      старый лендос
        //assertPageIsAvailable(Pages.Site.Landings.halva());       старый лендос
        //assertPageIsAvailable(Pages.Site.Landings.kazan());      лендос отключен
        //assertPageIsAvailable(Pages.Site.Landings.feedback());    этого лендоса нет на стейдже
        //assertPageIsAvailable(Pages.Site.Landings.mobile());      этого лендоса нет на стейдже
    }


    @Test(
            description = "Тест доступности статических страниц",
            groups = {"smoke","acceptance","regression"},
            priority = 1404
    )
    public void successCheckStaticPages() throws Exception, AssertionError {
        // TODO переделать на assertPagesAvailable(Pages.Site.Static.*)
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
            priority = 1405
    )
    public void successCheckProfilePages() throws Exception, AssertionError {
        kraken.get().baseUrl();
        kraken.perform().loginAs("admin");

        // TODO переделать на assertPagesAvailable(Pages.Site.Profile.*)
        assertPageIsAvailable(Pages.Site.Profile.edit());
        assertPageIsAvailable(Pages.Site.Profile.favorites());
        assertPageIsAvailable(Pages.Site.Profile.orders());
        assertPageIsAvailable(Pages.Site.Profile.addresses());
    }


    @Test(
            description = "Тест доступности корневых разделов админки",
            groups = {"smoke","acceptance","regression"},
            priority = 1406
    )
    public void successCheckAdminPages() throws Exception, AssertionError {
        kraken.perform().reachAdmin();

        // TODO переделать на assertPagesAvailable(Pages.Admin.*)
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


    // TOdO перенести в отдельный тест checkLinks
    // TOdO добавить тест ссылок хедера на главной

    @Test(
            description = "Тест работоспособности ссылок футера",
            groups = {"smoke","acceptance","regression"},
            priority = 1407
    )
    public void successCheckFooterLinks() throws Exception {
        kraken.get().baseUrl();

        assertTransition(Elements.Site.Footer.aboutCompanyButton());
        assertPageIsAvailable();

        assertTransition(Elements.Site.Footer.contactsButton());
        assertPageIsAvailable();

        assertTransition(Elements.Site.Footer.faqButton());
        assertPageIsAvailable();

        assertTransition(Elements.Site.Footer.feedbackFormButton());
        assertPageIsAvailable();

        assertTransition(Elements.Site.Footer.returnPolicyButton());
        assertPageIsAvailable();

        assertTransition(Elements.Site.Footer.publicOfferButton());
        assertPageIsAvailable();

        kraken.perform().click(Elements.Site.Footer.deliveryButton());
        Assert.assertTrue(kraken.detect().isDeliveryModalOpen(),
                "Модалка 'Доставка' не открыта\n");
        kraken.perform().click(Elements.Site.DeliveryModal.closeButton());
        assertPageIsAvailable();

        kraken.perform().click(Elements.Site.Footer.partnersButton());
        Assert.assertTrue(kraken.detect().isPartnersModalOpen(),
                "Модалка 'Партнёры' не открыта\n");
        kraken.perform().click(Elements.Site.PartnersModal.closeButton());
        assertPageIsAvailable();

        // TODO тест валится из-за кнопки gotop
        /*
        kraken.perform().click(Elements.Site.Footer.paymentButton());
        Assert.assertTrue(kraken.detect().isPaymentModalOpen(),
                "Cant assert Payment modal is open, check manually\n");
        kraken.perform().click(Elements.Site.PaymentModal.closeButton());
        assertPageIsAvailable();
        */
    }

}
