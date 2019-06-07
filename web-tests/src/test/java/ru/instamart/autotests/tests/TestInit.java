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
        kraken.check().elementPresence(Elements.Site.Header.container());

        kraken.check().elementPresence(Elements.Site.Header.shipAddressPlaceholder());
        kraken.check().elementPresence(Elements.Site.Header.shipAddressButton());
        kraken.check().elementPresence(Elements.Site.Header.hotlinePhoneNumber());
        kraken.check().elementPresence(Elements.Site.Header.hotlineWorkhoursText());

        kraken.check().elementPresence(Elements.Site.Header.logo());
        kraken.check().elementPresence(Elements.Site.Header.aboutCompanyButton());
        kraken.check().elementPresence(Elements.Site.Header.contactsButton());
        kraken.check().elementPresence(Elements.Site.Header.helpButton());
        kraken.check().elementPresence(Elements.Site.Header.deliveryButton());
        kraken.check().elementPresence(Elements.Site.Header.corporativeCustomersButton());
        kraken.check().elementPresence(Elements.Site.Header.mnogoruButton());

        kraken.check().elementPresence(Elements.Site.Header.catalogButton());
        kraken.check().elementPresence(Elements.Site.Header.storeButton());
        kraken.check().elementPresence(Elements.Site.Header.Search.inputField());
        kraken.check().elementPresence(Elements.Site.Header.Search.sendButton());
        kraken.check().elementPresence(Elements.Site.Header.favoritesButton());
        kraken.check().elementPresence(Elements.Site.Header.loginButton());
        kraken.check().elementPresence(Elements.Site.Header.cartButton());
    }

    private void validateHeaderLinks() {
        validateTransition(Elements.Site.Header.aboutCompanyButton());
        validateTransition(Elements.Site.Header.contactsButton());
        validateTransition(Elements.Site.Header.helpButton());
        validateTransition(Elements.Site.Header.deliveryButton());
        //TODO доделать обработку и проверку открытия новых вкладок
        //validateTransition(Elements.Site.Header.corporativeCustomersButton());
        //validateTransition(Elements.Site.Header.mnogoruButton());
        validateTransition(Elements.Site.Header.logo());
    }

    @Test(
            description = "Тест доступности витрин активных рителйеров",
            groups = {"smoke","acceptance","regression"},
            priority = 12
    )
    public void successCheckActiveRetailerPages() throws AssertionError {
        skipOn("metro"); // TODO сделать тест для тенанта
        assertPageIsAvailable(Pages.Site.Retailers.metro());
        assertPageIsAvailable(Pages.Site.Retailers.vkusvill());
        assertPageIsAvailable(Pages.Site.Retailers.lenta());
        assertPageIsAvailable(Pages.Site.Retailers.auchan());
    }

    @Test(
            description = "Тест недоступности витрин неактивных рителйеров",
            groups = {"smoke","acceptance","regression"},
            priority = 13
    )
    public void successCheckInactiveRetailerPages() throws AssertionError {
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

    @Test(
            description = "Тест валидности элементов и ссылок в футере сайта",
            groups = {"smoke","acceptance","regression"},
            priority = 16
    )
    public void successValidateFooter() {
        skipOn("metro"); // TODO сделать тест для тенанта
        kraken.get().page("metro");

        checkFooterElementsPresence();
        validateFooterLinks();
        openFooterModals();
    }

    private void checkFooterElementsPresence() {
        kraken.check().elementPresence(Elements.Site.Footer.info());
        kraken.check().elementPresence(Elements.Site.Footer.container());

        kraken.check().elementPresence(Elements.Site.Footer.instamartLogo());

        kraken.check().elementPresence(Elements.Site.Footer.instamartTitle());
        kraken.check().elementPresence(Elements.Site.Footer.aboutCompanyLink());
        kraken.check().elementPresence(Elements.Site.Footer.vacanciesLink());
        kraken.check().elementPresence(Elements.Site.Footer.partnersButton());
        kraken.check().elementPresence(Elements.Site.Footer.contactsLink());

        kraken.check().elementPresence(Elements.Site.Footer.customerHelpTitle());
        kraken.check().elementPresence(Elements.Site.Footer.deliveryButton());
        kraken.check().elementPresence(Elements.Site.Footer.paymentButton());
        kraken.check().elementPresence(Elements.Site.Footer.faqButton());

        kraken.check().elementPresence(Elements.Site.Footer.hotlinePhoneNumber());
        kraken.check().elementPresence(Elements.Site.Footer.hotlineWorkhoursText());

        kraken.check().elementPresence(Elements.Site.Footer.facebookButton());
        kraken.check().elementPresence(Elements.Site.Footer.vkontakteButton());
        kraken.check().elementPresence(Elements.Site.Footer.instagramButton());

        kraken.check().elementPresence(Elements.Site.Footer.appstoreButton());
        kraken.check().elementPresence(Elements.Site.Footer.googlePlayButton());

        kraken.check().elementPresence(Elements.Site.Footer.returnsPolicyLink());
        kraken.check().elementPresence(Elements.Site.Footer.personalDataPolicyLink());
        kraken.check().elementPresence(Elements.Site.Footer.publicOfferLink());
    }

    private void validateFooterLinks() {
        validateTransition(Elements.Site.Footer.aboutCompanyLink());
        validateTransition(Elements.Site.Footer.contactsLink());
        // validateTransition(Elements.Site.Footer.faqButton()); // заслоняет живосайт
        validateTransition(Elements.Site.Footer.returnsPolicyLink());
        validateTransition(Elements.Site.Footer.publicOfferLink());
        //TODO валидировать и остальные ссылки
    }

    private void openFooterModals() {
        kraken.get().baseUrl();

        kraken.perform().click(Elements.Site.Footer.deliveryButton());
        Assert.assertTrue(kraken.detect().isDeliveryModalOpen(),
                "Не открывается модалка 'Доставка' из футера\n");
        kraken.perform().click(Elements.Site.DeliveryModal.closeButton());
        assertPageIsAvailable();

        kraken.get().page(Pages.Site.Retailers.metro());
        kraken.perform().click(Elements.Site.Footer.partnersButton());
        Assert.assertTrue(kraken.detect().isPartnersModalOpen(),
                "Не открывается модалка 'Партнеры' из футера\n");
        kraken.perform().click(Elements.Site.PartnersModal.closeButton());
        assertPageIsAvailable();

        kraken.get().page(Pages.Site.Retailers.metro());
        kraken.perform().click(Elements.Site.Footer.paymentButton());
        Assert.assertTrue(kraken.detect().isPaymentModalOpen(),
                "Не открывается модалка 'Оплата' из футера\n");
        kraken.perform().click(Elements.Site.PaymentModal.closeButton());
        assertPageIsAvailable();
    }
}