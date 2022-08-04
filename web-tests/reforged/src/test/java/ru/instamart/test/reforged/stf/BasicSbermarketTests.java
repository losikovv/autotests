package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.reforged.core.DoNotOpenBrowser;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.data_provider.StaticPage;
import ru.instamart.reforged.core.service.Curl;
import ru.instamart.reforged.core.service.CurlService;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertTrue;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Лэндинг")
public final class BasicSbermarketTests {

    @CaseId(1438)
    @Story("Валидация элементов")
    @Test(description = "Тест валидности элементов и ссылок в шапке Сбермаркета", groups = {"regression", "MRAutoCheck"})
    public void successValidateHeader() {
        shop().goToPage();
        shop().checkPageIsAvailable();

        shop().interactHeader().checkHeaderVisible();
        shop().interactHeader().checkSelectAddressButtonVisible();
        shop().interactHeader().checkHotlineWorkHoursVisible();
        shop().interactHeader().checkForB2bVisible();
        shop().interactHeader().checkForBrandsVisible();
        shop().interactHeader().checkHowWeWorkVisible();
        shop().interactHeader().checkContactsVisible();
        shop().interactHeader().checkHelpVisible();
        shop().interactHeader().checkDeliveryAndPaymentVisible();
        shop().interactHeader().checkCategoryMenuVisible();
        shop().interactHeader().checkSearchInputVisible();
        shop().interactHeader().checkSearchButtonVisible();
        shop().interactHeader().checkCartVisible();
        shop().interactHeader().checkFavoritesNoAuthVisible();
        shop().interactHeader().checkLoginIsVisible();
    }

    @CaseId(733)
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на как мы работаем", groups = "regression")
    public void successTransitionHowWeWork() {
        shop().goToPage();
        shop().interactHeader().clickToHowWeWork();
        howWeWork().checkPageIsAvailable();
        howWeWork().checkPageOpened();
    }

    @CaseId(1810)
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на информацию о контактах", groups = "regression")
    public void successTransitionContactsInfo() {
        shop().goToPage();
        shop().interactHeader().clickToContacts();
        contacts().checkPageIsAvailable();
        contacts().checkPageOpened();
    }

    @CaseId(1811)
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на страничку с помощью для клиента", groups = "regression")
    public void successTransitionHelpInfo() {
        shop().goToPage();
        shop().interactHeader().clickToHelp();
        faq().checkPageIsAvailable();
        faq().checkPageOpened();
    }

    @CaseId(1812)
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на страничку с информацией о доставке", groups = "regression")
    public void successTransitionDeliveryInfo() {
        shop().goToPage();
        shop().interactHeader().clickToDeliveryAndPayment();
        delivery().checkPageIsAvailable();
        delivery().checkPageOpened();
    }

    @CaseId(1813)
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на страничку с Logo", groups = "regression")
    public void successTransitionLogo() {
        shop().goToPage();
        shop().interactHeader().clickToLogo();
        shop().checkPageIsAvailable();
        shop().checkPageOpened();
    }

    @CaseId(1439)
    @Story("Валидация элементов")
    @Test(description = "Тест валидности элементов в футере Сбермаркета", groups = {"regression", "MRAutoCheck"})
    public void successValidateElementInFooterSbermarket() {
        shop().goToPage();
        shop().checkPageIsAvailable();
        shop().scrollDown();

        shop().interactFooter().checkFooterVisible();
        shop().interactFooter().checkLogoVisible();

        shop().interactFooter().checkSbermarketTitleVisible();
        shop().interactFooter().checkAboutCompanyLinkVisible();
        shop().interactFooter().checkContactsLinkVisible();
        shop().interactFooter().checkVacanciesLinkVisible();
        shop().interactFooter().checkDocumentsLinkVisible();
        shop().interactFooter().checkPartnersLinkVisible();

        shop().interactFooter().checkCustomerHelpTitleVisible();
        shop().interactFooter().checkHowWeWorkVisible();
        shop().interactFooter().checkDeliveryZoneVisible();
        shop().interactFooter().checkDeliveryAndPaymentVisible();
        shop().interactFooter().checkHelpVisible();

        shop().interactFooter().checkHotlinePhoneNumberVisible();
        shop().interactFooter().checkHotlineWorkHoursTextVisible();

        shop().interactFooter().checkFacebookButtonVisible();
        shop().interactFooter().checkVkontakteButtonVisible();
        shop().interactFooter().checkInstagramButtonVisible();
        shop().interactFooter().checkTwitterButtonVisible();

        shop().interactFooter().checkGooglePlayButtonVisible();
        shop().interactFooter().checkAppstoreButtonVisible();
        shop().interactFooter().checkHuaweiButtonVisible();

        shop().interactFooter().checkReturnsPolicyLinkVisible();
        shop().interactFooter().checkPersonalDataPolicyLinkVisible();
        shop().interactFooter().checkPublicOfferLinkVisible();
    }

    @CaseId(3470)
    @Story("Статические страницы")
    @Test(
            dataProviderClass = StaticPage.class,
            dataProvider = "footerLinkPage",
            description = "Тест валидности переходов по ссылкам в футере Сбермаркета",
            groups = "regression")
    public void successFooterLinkTransition(final String text, final String url) {
        home().goToPage();
        home().scrollDown();

        home().interactFooter().clickToFooterElementWithText(text);
        home().checkPageUrl(url);
    }

    @CaseId(3504)
    @Story("Статические страницы")
    @Test(description = "Тест валидности переходов по ссылкам в футере Сбермаркета, кейсы с открытием документа и модального окна",
            groups = "regression")
    public void successFooterLinkTransitionOtherCases() {
        home().goToPage();
        home().interactFooter().clickToFooterElementWithText("Обработка персональных данных");
        home().switchToNextWindow();
        privacyPolicy().checkPageUrl(UiProperties.STF_URL + privacyPolicy().pageUrl());
    }

    @DoNotOpenBrowser
    @CaseId(1437)
    @Story("Витрины ретейлеров")
    @Test(dataProviderClass = StaticPage.class,
            dataProvider = "filteredAvailableRetailersSpree",
            description = "Тест доступности витрин ритейлеров Сбермаркета ",
            groups = "regression")
    public void successCheckSbermarketAvailableRetailers(final RetailerV2 retailer) {
        final String fullUrl = UiProperties.STF_URL + retailer.getSlug();
        assertTrue(CurlService.pageAvailable(fullUrl, UiProperties.HEADER_STF_FORWARD_TO), "Страница " + fullUrl + " недоступна");
    }

    @DoNotOpenBrowser
    @CaseId(1437)
    @Story("Витрины ретейлеров")
    @Test(dataProviderClass = StaticPage.class,
            dataProvider = "filteredUnavailableRetailersSpree",
            description = "Тест недоступности витрин ритейлеров Сбермаркета ",
            groups = "regression")
    public void successCheckSbermarketUnavailableRetailers(final RetailerV2 retailer) {
        final var fullUrl = UiProperties.STF_URL + retailer.getSlug();
        assertTrue(CurlService.pageUnavailable(fullUrl, UiProperties.HEADER_STF_FORWARD_TO), "Страница " + fullUrl + " доступна");
    }

    @DoNotOpenBrowser
    @CaseId(1433)
    @Story("Партнерские лендинги")
    @Test(description = "Тест доступности партнерских лендингов", groups = "regression")
    public void successCheckPartnerLandingsAreAvailable() {
        final var fullUrl = UiProperties.STF_URL + aeroflot().pageUrl();
        assertTrue(CurlService.pageAvailable(fullUrl, UiProperties.HEADER_STF_FORWARD_TO), "Страница " + fullUrl + " недоступна");
    }

    @DoNotOpenBrowser
    @CaseId(1814)
    @Story("Сервисные страницы")
    @Test(description = "Тест доступности сервисных страниц", groups = "regression")
    public void successCheckServicePagesAreAvailable() {
        final String fullUrl = UiProperties.STF_URL + driversHiring().pageUrl();
        assertTrue(CurlService.pageAvailable(fullUrl, UiProperties.HEADER_STF_FORWARD_TO), "Страница " + fullUrl + " недоступна");
    }

    @DoNotOpenBrowser
    @CaseId(1814)
    @Story("Сервисные страницы")
    @Test(description = "Тест доступности сервисных страниц", groups = "regression")
    public void successCheckJobLandingAreAvailable() {
        final var curl = new Curl.Builder(UiProperties.JOB_LANDING_URL).withUserAgent("Autotest").build();
        assertTrue(CurlService.pageAvailable(curl), "Страница " + UiProperties.JOB_LANDING_URL + " недоступна");
    }

    @DoNotOpenBrowser
    @CaseId(1432)
    @Story("Статические страницы")
    @Test(
            dataProviderClass = StaticPage.class,
            dataProvider = "faqPage",
            description = "Тест доступности статических страниц",
            groups = "regression")
    public void successCheckStaticPagesAreAvailable(final String url) {
        assertTrue(CurlService.pageAvailable(url, UiProperties.HEADER_STF_FORWARD_TO), "Страница " + url + " недоступна");
    }
}
