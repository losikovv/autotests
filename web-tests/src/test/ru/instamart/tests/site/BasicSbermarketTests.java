package ru.instamart.tests.site;

import instamart.core.settings.Config;
import instamart.core.testdata.dataprovider.RestDataProvider;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.common.lib.Pages;
import instamart.ui.objectsmap.Elements;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

public class BasicSbermarketTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();

    //todo сделать отдельные тесты под тенанты

    @Test(
            description = "Тест валидности элементов и ссылок в шапке Сбермаркета",
            priority = 101,
            groups = {"testing","sbermarket-Ui-smoke"}
    ) public void successValidateHeaderSbermarket() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        baseChecks.checkPageIsAvailable();
        baseChecks.checkIsElementPresent(Elements.Header.container());
        baseChecks.checkIsElementPresent(Elements.Header.shipAddressPlaceholder());
        baseChecks.checkIsElementPresent(Elements.Header.shipAddressButton());
        //baseChecks.checkIsElementPresent(Elements.Header.hotlinePhoneNumber());
        baseChecks.checkIsElementPresent(Elements.Header.hotlineWorkhoursText());
        baseChecks.checkIsElementPresent(Elements.Header.howWeWorkInfoButton());
        baseChecks.checkIsElementPresent(Elements.Header.contactsInfoButton());
        baseChecks.checkIsElementPresent(Elements.Header.helpInfoButton());
        baseChecks.checkIsElementPresent(Elements.Header.deliveryInfoButton());
        baseChecks.checkIsElementPresent(Elements.Header.forBusinessButton());
        baseChecks.checkIsElementPresent(Elements.Header.catalogButton());
        if(!kraken.detect().tenant("metro")) baseChecks.checkIsElementPresent(Elements.Header.storeButton());
        baseChecks.checkIsElementPresent(Elements.Header.Search.inputField());
        baseChecks.checkIsElementPresent(Elements.Header.Search.sendButton());
        baseChecks.checkIsElementPresent(Elements.Header.favoritesButton());
        baseChecks.checkIsElementPresent(Elements.Header.loginButton());
        baseChecks.checkIsElementPresent(Elements.Header.cartButton());
    }
    @Test(
            description = "Тест перехода из Сбермаркета на как мы работаем",
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successTransitionHowWeWork(){
        kraken.get().page(Config.DEFAULT_RETAILER);
        baseChecks.checkTransitionValidation(Elements.Header.howWeWorkInfoButton());
    }
    @Test(
            description = "Тест перехода из Сбермаркета на информацию о контактах",
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successTransitionContactsInfo(){
        kraken.get().page(Config.DEFAULT_RETAILER);
        baseChecks.checkTransitionValidation(Elements.Header.contactsInfoButton());
    }
    @Test(
            description = "Тест перехода из Сбермаркета на страничку с помощью для клиента",
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successTransitionHelpInfo(){
        kraken.get().page(Config.DEFAULT_RETAILER);
        baseChecks.checkTransitionValidation(Elements.Header.helpInfoButton());
    }
    @Test(
            description = "Тест перехода из Сбермаркета на страничку с информацией о доставке",
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successTransitionDeliveryInfo(){
        kraken.get().page(Config.DEFAULT_RETAILER);
        baseChecks.checkTransitionValidation(Elements.Header.deliveryInfoButton());
    }
    @Test(
            description = "Тест перехода из Сбермаркета на страничку с Logo",
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successTransitionLogo(){
        kraken.get().page(Config.DEFAULT_RETAILER);
        baseChecks.checkTransitionValidation(Elements.Header.logo());
    }

    // todo public void successValidateHeaderDeliveryMetro()

    // todo public void successValidateHeaderSbermarket()

    @CaseId(6)
    @Test(
            description = "Тест валидности элементов и ссылок в футере Сбермаркета",
            priority = 102,
            groups = {"testing","sbermarket-Ui-smoke"}
    ) public void successValidateFooterSbermarket() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        baseChecks.checkPageIsAvailable();
        checkFooterElementsPresence();
        validateFooterLinks();
        openFooterModals();
    }

    // todo public void successValidateFooterDeliveryMetro()


    private void checkFooterElementsPresence() {
        baseChecks.checkIsElementPresent(Elements.Footer.container());
        baseChecks.checkIsElementPresent(Elements.Footer.instamartLogo());
        baseChecks.checkIsElementPresent(Elements.Footer.sbermarketTitle());
            baseChecks.checkIsElementPresent(Elements.Footer.infoLink("О компании"));
            baseChecks.checkIsElementPresent(Elements.Footer.infoLink("Контакты"));
            baseChecks.checkIsElementPresent(Elements.Footer.infoLink("Вакансии"));
            baseChecks.checkIsElementPresent(Elements.Footer.infoLink("Документы"));
        baseChecks.checkIsElementPresent(Elements.Footer.customerHelpTitle());
            baseChecks.checkIsElementPresent(Elements.Footer.infoLink("Как мы работаем"));
            baseChecks.checkIsElementPresent(Elements.Footer.infoLink("Зоны доставки"));
            baseChecks.checkIsElementPresent(Elements.Footer.infoLink("Доставка и оплата"));
            baseChecks.checkIsElementPresent(Elements.Footer.infoLink("Помощь"));
        //baseChecks.checkIsElementPresent(Elements.Footer.hotlinePhoneNumber());
        baseChecks.checkIsElementPresent(Elements.Footer.hotlineWorkhoursText());
        baseChecks.checkIsElementPresent(Elements.Footer.facebookButton());
        baseChecks.checkIsElementPresent(Elements.Footer.vkontakteButton());
        baseChecks.checkIsElementPresent(Elements.Footer.instagramButton());
        //baseChecks.checkIsElementPresent(Elements.Footer.twitterButton());
        baseChecks.checkIsElementPresent(Elements.Footer.appstoreButton());
        baseChecks.checkIsElementPresent(Elements.Footer.googlePlayButton());
        baseChecks.checkIsElementPresent(Elements.Footer.returnsPolicyLink());
        baseChecks.checkIsElementPresent(Elements.Footer.personalDataPolicyLink());
        baseChecks.checkIsElementPresent(Elements.Footer.publicOfferLink());
    }

    private void validateFooterLinks() {
        baseChecks.checkTransitionValidation(Elements.Footer.infoLink("О компании"));
        baseChecks.checkTransitionValidation(Elements.Footer.infoLink("Контакты"));
        //validateTransition(Elements.Footer.infoLink("FAQ")); // заслоняет живосайт

        baseChecks.checkTransitionValidation(Elements.Footer.returnsPolicyLink());
        baseChecks.checkTransitionValidation(Elements.Footer.publicOfferLink());
        //TODO валидировать и остальные ссылки
    }

    private void openFooterModals() {
        //TODO разбить на отдельные тесты
        kraken.get().baseUrl();
        kraken.perform().click(Elements.Footer.deliveryButton());
        baseChecks.checkPageIsAvailable();
    }

    @Test(  dataProvider = "retailersSpree" ,
            dataProviderClass = RestDataProvider.class,
            description = "Тест доступности / недоступности витрин ритейлеров Сбермаркета ",
            priority = 103,
            groups = {"testing","sbermarket-Ui-smoke"}
    ) public void successCheckSbermarketRetailers(String slug, boolean available) {
        if (available) baseChecks.checkRetailerIsAvailable(slug);
        else baseChecks.checkRetailerIsUnavailable(slug);
    }

    @Test(
            description = "Тест доступности партнерских лендингов",
            priority = 104,
            groups = {"testing","sbermarket-Ui-smoke"}
    ) public void successCheckPartnerLandingsAreAvailable() {
        baseChecks.checkPageIsAvailable(Pages.Landings.mnogoru());
        baseChecks.checkPageIsAvailable(Pages.Landings.aeroflot());
    }

    @Test(
            description = "Тест доступности сервисных страниц",
            priority = 105,
            groups = {"testing","sbermarket-Ui-smoke"}
    ) public void successServicePagesAreAvailable() {
        baseChecks.checkPageIsAvailable(Pages.ServicePages.giftCertificates());
        baseChecks.checkPageIsAvailable(Pages.Landings.massHiring());
    }

    @CaseId(9)
    @Test(
            description = "Тест доступности статических страниц",
            priority = 106,
            groups = {
                    "testing","sbermarket-Ui-smoke"
            }
    ) public void successCheckStaticPagesAreAvailabile() {
        baseChecks.checkPageIsAvailable(Pages.Sbermarket.about());
        baseChecks.checkPageIsAvailable(Pages.Sbermarket.delivery());
        baseChecks.checkPageIsAvailable(Pages.Sbermarket.rules());
        baseChecks.checkPageIsAvailable(Pages.Sbermarket.payment());
        baseChecks.checkPageIsAvailable(Pages.Sbermarket.returnPolicy());
        baseChecks.checkPageIsAvailable(Pages.Sbermarket.faq());
        baseChecks.checkPageIsAvailable(Pages.Sbermarket.terms());
        baseChecks.checkPageIsAvailable(Pages.Sbermarket.contacts());
    }
}