package ru.instamart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.application.Tenants;
import ru.instamart.application.Elements;
import ru.instamart.application.lib.Pages;

import static ru.instamart.application.Tenants.metro;

public class StartupTests extends TestBase {

    //todo сделать отдельные тесты под тенанты

    @Test(
            description = "Тест валидности элементов и ссылок в шапке Instamart",
            priority = 101,
            groups = {"smoke","acceptance","regression"}
    ) public void successValidateHeaderInstamart() {
        runTestOnlyOn(Tenants.instamart());
        kraken.get().page("metro");

        assertPageIsAvailable();

        assertPresence(Elements.Header.container());

        assertPresence(Elements.Header.shipAddressPlaceholder());
        assertPresence(Elements.Header.shipAddressButton());
        assertPresence(Elements.Header.hotlinePhoneNumber());
        assertPresence(Elements.Header.hotlineWorkhoursText());

        assertPresence(Elements.Header.howWeWorkInfoButton());
        assertPresence(Elements.Header.contactsInfoButton());
        assertPresence(Elements.Header.helpInfoButton());
        assertPresence(Elements.Header.deliveryInfoButton());
        assertPresence(Elements.Header.corporativeCustomersInfoButton());
        assertPresence(Elements.Header.mnogoruButton());

        assertPresence(Elements.Header.catalogButton());
        if(!kraken.detect().tenant(metro())) assertPresence(Elements.Header.storeButton());
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
        //TODO доделать обработку и проверку открытия новых вкладок
        //validateNewTabOpening(Elements.Site.Header.corporativeCustomersInfoButton());
        //validateNewTabOpening(Elements.Site.Header.mnogoruButton());
        validateTransition(Elements.Header.logo());
    }

    // todo public void successValidateHeaderDeliveryMetro()

    // todo public void successValidateHeaderSbermarket()

    @Test(
            description = "Тест валидности элементов и ссылок в футере сайта",
            priority = 102,
            groups = {"smoke","acceptance","regression"}
    ) public void successValidateFooterInstamart() {
        runTestOnlyOn(Tenants.instamart());

        kraken.get().page("metro");

        assertPageIsAvailable();

        checkFooterElementsPresence();
        validateFooterLinks();
        openFooterModals();
    }

    // todo public void successValidateFooterDeliveryMetro()

    // todo public void successValidateFooterSbermarket()

    private void checkFooterElementsPresence() {
        assertPresence(Elements.Footer.info());
        assertPresence(Elements.Footer.container());

        assertPresence(Elements.Footer.instamartLogo());

        assertPresence(Elements.Footer.instamartTitle());
            assertPresence(Elements.Footer.infoLink("О компании"));
            assertPresence(Elements.Footer.infoLink("Контакты"));
            assertPresence(Elements.Footer.infoLink("Вакансии"));
            assertPresence(Elements.Footer.infoLink("Документы"));

        assertPresence(Elements.Footer.customerHelpTitle());
            assertPresence(Elements.Footer.infoLink("Как мы работаем"));
            assertPresence(Elements.Footer.infoLink("Доставка"));
            assertPresence(Elements.Footer.infoLink("Помощь"));

        assertPresence(Elements.Footer.hotlinePhoneNumber());
        assertPresence(Elements.Footer.hotlineWorkhoursText());

        assertPresence(Elements.Footer.facebookButton());
        assertPresence(Elements.Footer.vkontakteButton());
        assertPresence(Elements.Footer.instagramButton());

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
    }

    @Test(
            description = "Тест доступности / недоступности витрин ритейлеров",
            priority = 103,
            groups = {"smoke","acceptance","regression"}
    ) public void successCheckRetailerPagesAreAvailable() {
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
            priority = 104,
            groups = {"smoke","acceptance","regression"}
    ) public void successCheckPartnerLandingsAreAvailable() {
        assertPageIsAvailable(Pages.Site.Landings.mnogoru());
        assertPageIsAvailable(Pages.Site.Landings.aeroflot());
    }

    @Test(
            description = "Тест доступности статических страниц",
            priority = 105,
            groups = {
                    "smoke","acceptance","regression",
                    "metro-smoke","metro-acceptance","metro-regression",
                    "sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successCheckStaticPagesAreAvailabile() {
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