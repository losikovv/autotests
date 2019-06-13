package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;

public class TestInit extends TestBase {

    @Test(
            description = "Тест валидности элементов и ссылок лендинга",
            groups = {"smoke","acceptance","regression"},
            priority = 10
    )
    public void successValidateLanding() {
        skipOn("metro");
        checkLandingElementsPresence();

        kraken.perform().fillField(Elements.Landing.phoneField(),"9629422123");
        kraken.perform().click(Elements.Landing.getDownloadLinkButton());

        Assert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Landing.successDownloadLinkPlaceholder()));
    }

    private void checkLandingElementsPresence() {
        kraken.check().elementPresence(Elements.Landing.logo());
        kraken.check().elementPresence(Elements.Landing.loginButton());
        kraken.check().elementPresence(Elements.Landing.title());
        kraken.check().elementPresence(Elements.Landing.addressField());
        kraken.check().elementPresence(Elements.Landing.selectStoreButton());
        kraken.check().elementPresence(Elements.Landing.goToCatalogButton());
        kraken.check().elementPresence(Elements.Landing.phoneField());
        kraken.check().elementPresence(Elements.Landing.getDownloadLinkButton());
    }

    @Test(
            description = "Тест валидности элементов и ссылок в шапке сайта",
            groups = {"smoke","acceptance","regression"},
            priority = 11
    )
    public void successValidateHeader() {
        skipOn("metro"); // TODO сделать тест для тенанта
        kraken.get().page("metro");

        checkHeaderElementsPresence();
        validateHeaderLinks();
    }

    private void checkHeaderElementsPresence() {
        kraken.check().elementPresence(Elements.Header.container());

        kraken.check().elementPresence(Elements.Header.shipAddressPlaceholder());
        kraken.check().elementPresence(Elements.Header.shipAddressButton());
        kraken.check().elementPresence(Elements.Header.hotlinePhoneNumber());
        kraken.check().elementPresence(Elements.Header.hotlineWorkhoursText());

        kraken.check().elementPresence(Elements.Header.logo());
        kraken.check().elementPresence(Elements.Header.aboutCompanyButton());
        kraken.check().elementPresence(Elements.Header.contactsButton());
        kraken.check().elementPresence(Elements.Header.helpButton());
        kraken.check().elementPresence(Elements.Header.deliveryButton());
        kraken.check().elementPresence(Elements.Header.corporativeCustomersButton());
        kraken.check().elementPresence(Elements.Header.mnogoruButton());

        kraken.check().elementPresence(Elements.Header.catalogButton());
        kraken.check().elementPresence(Elements.Header.storeButton());
        kraken.check().elementPresence(Elements.Header.Search.inputField());
        kraken.check().elementPresence(Elements.Header.Search.sendButton());
        kraken.check().elementPresence(Elements.Header.favoritesButton());
        kraken.check().elementPresence(Elements.Header.loginButton());
        kraken.check().elementPresence(Elements.Header.cartButton());
    }

    private void validateHeaderLinks() {
        validateTransition(Elements.Header.aboutCompanyButton());
        validateTransition(Elements.Header.contactsButton());
        validateTransition(Elements.Header.helpButton());
        validateTransition(Elements.Header.deliveryButton());
        //TODO доделать обработку и проверку открытия новых вкладок
        //validateTransition(Elements.Site.Header.corporativeCustomersButton());
        //validateTransition(Elements.Site.Header.mnogoruButton());
        validateTransition(Elements.Header.logo());
    }

    @Test(
            description = "Тест валидности элементов и ссылок в футере сайта",
            groups = {"smoke","acceptance","regression"},
            priority = 12
    )
    public void successValidateFooter() {
        skipOn("metro"); // TODO сделать тест для тенанта
        kraken.get().page("metro");

        checkFooterElementsPresence();
        validateFooterLinks();
        openFooterModals();
    }

    private void checkFooterElementsPresence() {
        kraken.check().elementPresence(Elements.Footer.info());
        kraken.check().elementPresence(Elements.Footer.container());

        kraken.check().elementPresence(Elements.Footer.instamartLogo());

        kraken.check().elementPresence(Elements.Footer.instamartTitle());
        kraken.check().elementPresence(Elements.Footer.aboutCompanyLink());
        kraken.check().elementPresence(Elements.Footer.vacanciesLink());
        kraken.check().elementPresence(Elements.Footer.partnersButton());
        kraken.check().elementPresence(Elements.Footer.contactsLink());

        kraken.check().elementPresence(Elements.Footer.customerHelpTitle());
        kraken.check().elementPresence(Elements.Footer.deliveryButton());
        kraken.check().elementPresence(Elements.Footer.paymentButton());
        kraken.check().elementPresence(Elements.Footer.faqButton());

        kraken.check().elementPresence(Elements.Footer.hotlinePhoneNumber());
        kraken.check().elementPresence(Elements.Footer.hotlineWorkhoursText());

        kraken.check().elementPresence(Elements.Footer.facebookButton());
        kraken.check().elementPresence(Elements.Footer.vkontakteButton());
        kraken.check().elementPresence(Elements.Footer.instagramButton());

        kraken.check().elementPresence(Elements.Footer.appstoreButton());
        kraken.check().elementPresence(Elements.Footer.googlePlayButton());

        kraken.check().elementPresence(Elements.Footer.returnsPolicyLink());
        kraken.check().elementPresence(Elements.Footer.personalDataPolicyLink());
        kraken.check().elementPresence(Elements.Footer.publicOfferLink());
    }

    private void validateFooterLinks() {
        validateTransition(Elements.Footer.aboutCompanyLink());
        validateTransition(Elements.Footer.contactsLink());
        // validateTransition(Elements.Site.Footer.faqButton()); // заслоняет живосайт
        validateTransition(Elements.Footer.returnsPolicyLink());
        validateTransition(Elements.Footer.publicOfferLink());
        //TODO валидировать и остальные ссылки
    }

    private void openFooterModals() {
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
    public void successCheckRetailerPages() throws AssertionError {
        skipOn("metro"); // TODO сделать тест для тенанта

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
    public void successCheckPartnersLandings() throws AssertionError {
        assertPageIsAvailable(Pages.Site.Landings.mnogoru());
        assertPageIsAvailable(Pages.Site.Landings.aeroflot());
    }

    @Test(
            description = "Тест доступности статических страниц",
            groups = {"smoke","acceptance","regression"},
            priority = 15
    )
    public void successCheckStaticPages() throws AssertionError {
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