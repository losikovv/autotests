package ru.instamart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.application.lib.Tenants;
import ru.instamart.application.Elements;
import ru.instamart.application.lib.Pages;

import static ru.instamart.application.lib.Tenants.metro;

public class StartupTests extends TestBase {

    @Test(
            description = "Тест валидности элементов и ссылок в шапке сайта",
            groups = {"smoke","acceptance","regression"},
            priority = 11
    )
    public void successValidateInstamartHeader() {
        runTestOnlyOn(Tenants.instamart());

        kraken.get().page("metro");

        assertPageIsAvailable();

        assertElementPresence(Elements.Header.container());

        assertElementPresence(Elements.Header.shipAddressPlaceholder());
        assertElementPresence(Elements.Header.shipAddressButton());
        assertElementPresence(Elements.Header.hotlinePhoneNumber());
        assertElementPresence(Elements.Header.hotlineWorkhoursText());

        assertElementPresence(Elements.Header.howWeWorkInfoButton());
        assertElementPresence(Elements.Header.contactsInfoButton());
        assertElementPresence(Elements.Header.helpInfoButton());
        assertElementPresence(Elements.Header.deliveryInfoButton());
        assertElementPresence(Elements.Header.corporativeCustomersInfoButton());
        assertElementPresence(Elements.Header.mnogoruButton());

        assertElementPresence(Elements.Header.catalogButton());
        if(!kraken.detect().tenant(metro())) assertElementPresence(Elements.Header.storeButton());
        assertElementPresence(Elements.Header.Search.inputField());
        assertElementPresence(Elements.Header.Search.sendButton());
        assertElementPresence(Elements.Header.favoritesButton());
        assertElementPresence(Elements.Header.loginButton());
        assertElementPresence(Elements.Header.cartButton());

        // todo вынести валидации отдельными тестами
        validateTransition(Elements.Header.howWeWorkInfoButton());
        validateTransition(Elements.Header.contactsInfoButton());
        validateTransition(Elements.Header.helpInfoButton());
        validateTransition(Elements.Header.deliveryInfoButton());
        //TODO доделать обработку и проверку открытия новых вкладок
        //validateNewTabOpening(Elements.Site.Header.corporativeCustomersInfoButton());
        //validateNewTabOpening(Elements.Site.Header.mnogoruButton());
        validateTransition(Elements.Header.logo());
    }

    @Test(
            description = "Тест валидности элементов и ссылок в футере сайта",
            groups = {"smoke","acceptance","regression"},
            priority = 12
    )
    public void successValidateInstamartFooter() {
        runTestOnlyOn(Tenants.instamart());

        kraken.get().page("metro");

        assertPageIsAvailable();

        checkFooterElementsPresence();
        validateFooterLinks();
        openFooterModals();
    }

    private void checkFooterElementsPresence() {
        assertElementPresence(Elements.Footer.info());
        assertElementPresence(Elements.Footer.container());

        assertElementPresence(Elements.Footer.instamartLogo());

        assertElementPresence(Elements.Footer.instamartTitle());
            assertElementPresence(Elements.Footer.infoLink("О компании"));
            assertElementPresence(Elements.Footer.infoLink("Вакансии"));
            assertElementPresence(Elements.Footer.infoLink("Партнеры"));
            assertElementPresence(Elements.Footer.infoLink("Контакты"));

        assertElementPresence(Elements.Footer.customerHelpTitle());
            assertElementPresence(Elements.Footer.infoLink("Доставка"));
            assertElementPresence(Elements.Footer.infoLink("Оплата"));
            assertElementPresence(Elements.Footer.infoLink("FAQ"));

        assertElementPresence(Elements.Footer.hotlinePhoneNumber());
        assertElementPresence(Elements.Footer.hotlineWorkhoursText());

        assertElementPresence(Elements.Footer.facebookButton());
        assertElementPresence(Elements.Footer.vkontakteButton());
        assertElementPresence(Elements.Footer.instagramButton());

        assertElementPresence(Elements.Footer.appstoreButton());
        assertElementPresence(Elements.Footer.googlePlayButton());

        assertElementPresence(Elements.Footer.returnsPolicyLink());
        assertElementPresence(Elements.Footer.personalDataPolicyLink());
        assertElementPresence(Elements.Footer.publicOfferLink());
    }

    private void validateFooterLinks() {
        validateTransition(Elements.Footer.infoLink("О компании"));
        validateTransition(Elements.Footer.infoLink("Контакты"));
        //validateTransition(Elements.Footer.infoLink("FAQ")); // заслоняет живосайт

        validateTransition(Elements.Footer.returnsPolicyLink());
        validateTransition(Elements.Footer.publicOfferLink());
        //TODO валидировать и остальные ссылки
    }

    private void openFooterModals() {
        //TODO разбить на отдельные тесты

        kraken.get().baseUrl();

        kraken.perform().click(Elements.Footer.deliveryButton());
        Assert.assertTrue(kraken.detect().isDeliveryModalOpen(),
                "Не открывается модалка 'Доставка' из футера\n");
        kraken.perform().click(Elements.Modals.DeliveryModal.closeButton());
        assertPageIsAvailable();

        kraken.get().page(Pages.Site.Retailers.metro());
        kraken.perform().click(Elements.Footer.partnersButton());
        Assert.assertTrue(kraken.detect().isPartnersModalOpen(),
                "Не открывается модалка 'Партнеры' из футера\n");
        kraken.perform().click(Elements.Modals.PartnersModal.closeButton());
        assertPageIsAvailable();

        kraken.get().page(Pages.Site.Retailers.metro());
        kraken.perform().click(Elements.Footer.paymentButton());
        Assert.assertTrue(kraken.detect().isPaymentModalOpen(),
                "Не открывается модалка 'Оплата' из футера\n");
        kraken.perform().click(Elements.Modals.PaymentModal.closeButton());
        assertPageIsAvailable();
    }

    @Test(
            description = "Тест доступности / недоступности витрин ритейлеров",
            groups = {"smoke","acceptance","regression"},
            priority = 13
    )
    public void successCheckRetailerPagesAreAvailable() {
        skipTestOn(metro()); // TODO сделать тест для тенанта

        // Проверяем что доступны витрины активных ритейлеров
        assertPageIsAvailable(Pages.Site.Retailers.metro());
        assertPageIsAvailable(Pages.Site.Retailers.auchan());

        // Проверяем что недоступны витрины неактивных ритейлеров
        assertPageIs404(Pages.Site.Retailers.vkusvill());
        assertPageIs404(Pages.Site.Retailers.lenta());
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
            priority = 14
    )
    public void successCheckPartnerLandingsAreAvailable() {
        assertPageIsAvailable(Pages.Site.Landings.mnogoru());
        assertPageIsAvailable(Pages.Site.Landings.aeroflot());
    }

    @Test(
            description = "Тест доступности статических страниц",
            groups = {"smoke","acceptance","regression"},
            priority = 15
    )
    public void successCheckStaticPagesAreAvailabile() {
        assertPageIsAvailable(Pages.Site.Static.about());
        assertPageIsAvailable(Pages.Site.Static.delivery());
        assertPageIsAvailable(Pages.Site.Static.rules());
        assertPageIsAvailable(Pages.Site.Static.payment());
        assertPageIsAvailable(Pages.Site.Static.returnPolicy());
        assertPageIsAvailable(Pages.Site.Static.faq());
        assertPageIsAvailable(Pages.Site.Static.terms());
        assertPageIsAvailable(Pages.Site.Static.contacts());
    }
}