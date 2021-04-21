package ru.instamart.tests.ui.site;

import org.testng.annotations.BeforeClass;
import ru.instamart.api.objects.v2.RetailerV2;
import ru.instamart.core.settings.Config;
import ru.instamart.core.testdata.dataprovider.RestDataProvider;
import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import ru.instamart.ui.common.lib.Pages;
import ru.instamart.ui.modules.User;
import ru.instamart.ui.objectsmap.Elements;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

@Epic("STF UI")
@Feature("Лэндинг")
public class BasicSbermarketTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();

    @BeforeMethod(alwaysRun = true,
            description ="Завершаем сессию, текущего пользователя")
    public void quickLogout() {
        User.Logout.quickly();
    }

    @CaseId(1438)
    @Story("Валидация элементов")
    @Test(
            description = "Тест валидности элементов и ссылок в шапке Сбермаркета",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
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
    @CaseId(733)
    @Story("Навигация")
    @Test(
            description = "Тест перехода из Сбермаркета на как мы работаем",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successTransitionHowWeWork(){
        kraken.get().page(Config.DEFAULT_RETAILER);
        baseChecks.checkTransitionValidation(Elements.Header.howWeWorkInfoButton());
    }
    @CaseId(1810)
    @Story("Навигация")
    @Test(
            description = "Тест перехода из Сбермаркета на информацию о контактах",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successTransitionContactsInfo(){
        kraken.get().page(Config.DEFAULT_RETAILER);
        baseChecks.checkTransitionValidation(Elements.Header.contactsInfoButton());
    }
    @CaseId(1811)
    @Story("Навигация")
    @Test(
            description = "Тест перехода из Сбермаркета на страничку с помощью для клиента",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successTransitionHelpInfo(){
        kraken.get().page(Config.DEFAULT_RETAILER);
        baseChecks.checkTransitionValidation(Elements.Header.helpInfoButton());
    }
    @CaseId(1812)
    @Story("Навигация")
    @Test(
            description = "Тест перехода из Сбермаркета на страничку с информацией о доставке",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successTransitionDeliveryInfo(){
        kraken.get().page(Config.DEFAULT_RETAILER);
        baseChecks.checkTransitionValidation(Elements.Header.deliveryInfoButton());
    }
    @CaseId(1813)
    @Story("Навигация")
    @Test(
            description = "Тест перехода из Сбермаркета на страничку с Logo",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successTransitionLogo(){
        kraken.get().page(Config.DEFAULT_RETAILER);
        baseChecks.checkTransitionValidation(Elements.Header.logo());
    }

    // todo public void successValidateHeaderDeliveryMetro()

    // todo public void successValidateHeaderSbermarket()

    @CaseId(1439)
    @Story("Валидация элементов")
    @Test(
            description = "Тест валидности элементов и ссылок в футере Сбермаркета",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    ) public void successValidateFooterSbermarket() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        baseChecks.checkPageIsAvailable();
        checkFooterElementsPresence();
        validateFooterLinks();
        openFooterModals();
    }

    // todo public void successValidateFooterDeliveryMetro()
    private void checkFooterElementsPresence() {
        kraken.perform().scrollToTheBottom();
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
        baseChecks.checkIsElementPresent(Elements.Footer.huaweiButton());
        baseChecks.checkIsElementPresent(Elements.Footer.appstoreButton());
        baseChecks.checkIsElementPresent(Elements.Footer.googlePlayButton());
        baseChecks.checkIsElementPresent(Elements.Footer.returnsPolicyLink());
        baseChecks.checkIsElementPresent(Elements.Footer.personalDataPolicyLink());
        baseChecks.checkIsElementPresent(Elements.Footer.publicOfferLink());
    }

    private void validateFooterLinks() {
        baseChecks.checkTransitionValidation(Elements.Footer.infoLink("О компании"));
        baseChecks.checkTransitionValidation(Elements.Footer.infoLink("Контакты"));
        baseChecks.checkTransitionValidation(Elements.Footer.returnsPolicyLink());
        baseChecks.checkTransitionValidation(Elements.Footer.publicOfferLink());
        //TODO валидировать и остальные ссылки
    }

    private void openFooterModals() {
        //TODO разбить на отдельные тесты
        kraken.get().baseUrl();
        kraken.perform().scrollToTheBottom(Elements.Footer.deliveryButton());
        kraken.perform().click(Elements.Footer.deliveryButton());
        baseChecks.checkPageIsAvailable();
    }
    @CaseId(1437)
    @Story("Витрины ретейлеров")
    @Test(  dataProvider = "retailersSpree" ,
            dataProviderClass = RestDataProvider.class,
            description = "Тест доступности / недоступности витрин ритейлеров Сбермаркета ",
            groups = {"sbermarket-Ui-smoke","MRAutoCheck","ui-smoke-production"}
    ) public void successCheckSbermarketRetailers(RetailerV2 retailer) {
        if (retailer.getAvailable()) baseChecks.checkRetailerIsAvailable(retailer.getSlug());
        else baseChecks.checkRetailerIsUnavailable(retailer.getSlug());
    }
    @CaseId(1433)
    @Story("Партнерские лендинги")
    @Test(
            description = "Тест доступности партнерских лендингов",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    ) public void successCheckPartnerLandingsAreAvailable() {
        baseChecks.checkPageIsAvailable(Pages.Landings.mnogoru());
        baseChecks.checkPageIsAvailable(Pages.Landings.aeroflot());
    }
    @CaseId(1814)
    @Story("Сервисные страницы")
    @Test(
            description = "Тест доступности сервисных страниц",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successServicePagesAreAvailable() {
        baseChecks.checkPageIsAvailable(Pages.ServicePages.giftCertificates());
        baseChecks.checkPageIsAvailable(Pages.Landings.massHiring());
    }

    @CaseId(1432)
    @Story("Статические страницы")
    @Test(
            description = "Тест доступности статических страниц",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    ) public void successCheckStaticPagesAreAvailabile() {
        //TODO разбить на тесты
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