package ru.instamart.tests.site;

import org.testng.annotations.Test;
import ru.instamart.application.Elements;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.rest.RestDataProvider;
import ru.instamart.tests.TestBase;

public class BasicSbermarketTests extends TestBase {

    //todo сделать отдельные тесты под тенанты

    @Test(
            description = "Тест валидности элементов и ссылок в шапке Сбермарткета",
            priority = 101,
            groups = {"sbermarket-smoke","testing","sbermarket-acceptance","sbermarket-regression"}
    ) public void successValidateHeaderSbermarket() {
        kraken.get().page("metro");

        assertPageIsAvailable();

        assertPresence(Elements.Header.container());

        assertPresence(Elements.Header.shipAddressPlaceholder());
        assertPresence(Elements.Header.shipAddressButton());
        //assertPresence(Elements.Header.hotlinePhoneNumber());
        assertPresence(Elements.Header.hotlineWorkhoursText());

        assertPresence(Elements.Header.howWeWorkInfoButton());
        assertPresence(Elements.Header.contactsInfoButton());
        assertPresence(Elements.Header.helpInfoButton());
        assertPresence(Elements.Header.deliveryInfoButton());
        assertPresence(Elements.Header.forBusinessButton());

        assertPresence(Elements.Header.catalogButton());
        if(!kraken.detect().tenant("metro")) assertPresence(Elements.Header.storeButton());
        assertPresence(Elements.Header.Search.inputField());
        assertPresence(Elements.Header.Search.sendButton());
        assertPresence(Elements.Header.favoritesButton());
        assertPresence(Elements.Header.loginButton());
        assertPresence(Elements.Header.cartButton());

        // todo вынести валидации отдельными тестами
        validateTransition(Elements.Header.howWeWorkInfoButton());
        validateTransition(Elements.Header.contactsInfoButton());
        validateTransition(Elements.Header.helpInfoButton());
        validateTransition(Elements.Header.deliveryInfoButton());
        validateTransition(Elements.Header.logo());
    }

    // todo public void successValidateHeaderDeliveryMetro()

    // todo public void successValidateHeaderSbermarket()

    @Test(
            description = "Тест валидности элементов и ссылок в футере Сбермаркета",
            priority = 102,
            groups = {"sbermarket-smoke","testing","sbermarket-acceptance","sbermarket-regression"}
    ) public void successValidateFooterSbermarket() {
        kraken.get().page("metro");

        assertPageIsAvailable();

        checkFooterElementsPresence();
        validateFooterLinks();
        openFooterModals();
    }

    // todo public void successValidateFooterDeliveryMetro()


    private void checkFooterElementsPresence() {
        assertPresence(Elements.Footer.container());

        assertPresence(Elements.Footer.instamartLogo());

        assertPresence(Elements.Footer.sbermarketTitle());
            assertPresence(Elements.Footer.infoLink("О компании"));
            assertPresence(Elements.Footer.infoLink("Контакты"));
            assertPresence(Elements.Footer.infoLink("Вакансии"));
            assertPresence(Elements.Footer.infoLink("Документы"));

        assertPresence(Elements.Footer.customerHelpTitle());
            assertPresence(Elements.Footer.infoLink("Как мы работаем"));
            assertPresence(Elements.Footer.infoLink("Зоны доставки"));
            assertPresence(Elements.Footer.infoLink("Доставка и оплата"));
            assertPresence(Elements.Footer.infoLink("Помощь"));

        //assertPresence(Elements.Footer.hotlinePhoneNumber());
        assertPresence(Elements.Footer.hotlineWorkhoursText());

        assertPresence(Elements.Footer.facebookButton());
        assertPresence(Elements.Footer.vkontakteButton());
        assertPresence(Elements.Footer.instagramButton());
        //assertPresence(Elements.Footer.twitterButton());

        assertPresence(Elements.Footer.appstoreButton());
        assertPresence(Elements.Footer.googlePlayButton());

        assertPresence(Elements.Footer.returnsPolicyLink());
        assertPresence(Elements.Footer.personalDataPolicyLink());
        assertPresence(Elements.Footer.publicOfferLink());
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

        // todo Почему один и тот же метод 2 раза?
        assertPageIsAvailable();
        assertPageIsAvailable();
    }

    @Test(  dataProvider = "retailersV1" ,
            dataProviderClass = RestDataProvider.class,
            description = "Тест доступности / недоступности витрин ритейлеров Сбермаркета ",
            priority = 103,
            groups = {"sbermarket-smoke","testing","sbermarket-acceptance","sbermarket-regression"}
    ) public void successCheckSbermarketRetailers(String slug, boolean available) {

        if (available) assertRetailerIsAvailable(slug);
        else assertRetailerIsUnavailable(slug);
    }

    @Test(
            description = "Тест доступности партнерских лендингов",
            priority = 104,
            groups = {"sbermarket-smoke","testing","sbermarket-acceptance","sbermarket-regression"}
    ) public void successCheckPartnerLandingsAreAvailable() {
        assertPageIsAvailable(Pages.Landings.mnogoru());
        assertPageIsAvailable(Pages.Landings.aeroflot());
    }

    @Test(
            description = "Тест доступности сервисных страниц",
            priority = 105,
            groups = {"sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"}
    ) public void successServicePagesAreAvailable() {
        assertPageIsAvailable(Pages.ServicePages.giftCertificates());
        assertPageIsAvailable(Pages.Landings.massHiring());
    }

    @Test(
            description = "Тест доступности статических страниц",
            priority = 106,
            groups = {
                    "sbermarket-smoke","testing","sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successCheckStaticPagesAreAvailabile() {
        assertPageIsAvailable(Pages.Sbermarket.about());
        assertPageIsAvailable(Pages.Sbermarket.delivery());
        assertPageIsAvailable(Pages.Sbermarket.rules());
        assertPageIsAvailable(Pages.Sbermarket.payment());
        assertPageIsAvailable(Pages.Sbermarket.returnPolicy());
        assertPageIsAvailable(Pages.Sbermarket.faq());
        assertPageIsAvailable(Pages.Sbermarket.terms());
        assertPageIsAvailable(Pages.Sbermarket.contacts());
    }
}