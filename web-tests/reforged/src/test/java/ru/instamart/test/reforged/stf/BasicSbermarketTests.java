package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.reforged.core.annotation.DoNotOpenBrowser;
import ru.instamart.reforged.core.data_provider.StaticPage;
import ru.instamart.reforged.core.service.curl.Curl;
import ru.instamart.reforged.core.service.curl.CurlService;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertTrue;
import static ru.instamart.kraken.config.CoreProperties.BASIC_AUTH_PASSWORD;
import static ru.instamart.kraken.config.CoreProperties.BASIC_AUTH_USERNAME;
import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.core.config.BasicProperties.JOB_LANDING_URL;
import static ru.instamart.reforged.core.config.UiProperties.Env.STF_FORWARD_TO;
import static ru.instamart.reforged.core.config.UiProperties.Env.STF_FRONT_URL;
import static ru.instamart.reforged.core.service.curl.Header.FORWARD_KEY;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Лэндинг")
public final class BasicSbermarketTests {

    @CaseId(1438)
    @Story("Валидация элементов")
    @Test(description = "Тест валидности элементов и ссылок в шапке Сбермаркета", groups = {REGRESSION_STF, "MRAutoCheck"})
    public void successValidateHeader() {
        shop().goToPage();
        shop().checkPageIsAvailable();

        shop().interactHeader().checkHeaderVisible();
        shop().interactHeader().checkSelectAddressButtonVisible();
        shop().interactHeader().checkHotlineWorkHoursVisible();
        shop().interactHeader().checkHotlinePhoneNumberVisible();
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
        shop().interactHeader().checkLoginIsVisibleSoft();
        shop().interactHeader().assertAll();
    }

    @CaseId(733)
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на как мы работаем", groups = REGRESSION_STF)
    public void successTransitionHowWeWork() {
        shop().goToPage();
        shop().interactHeader().clickToHowWeWork();
        howWeWork().checkPageIsAvailable();
        howWeWork().checkPageOpened();
    }

    @CaseId(1810)
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на информацию о контактах", groups = REGRESSION_STF)
    public void successTransitionContactsInfo() {
        shop().goToPage();
        shop().interactHeader().clickToContacts();
        contacts().checkPageIsAvailable();
        contacts().checkPageOpened();
    }

    @CaseId(1811)
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на страничку с помощью для клиента", groups = REGRESSION_STF)
    public void successTransitionHelpInfo() {
        shop().goToPage();
        shop().interactHeader().clickToHelp();
        faq().checkPageIsAvailable();
        faq().checkPageOpened();
    }

    @CaseId(1812)
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на страничку с информацией о доставке", groups = REGRESSION_STF)
    public void successTransitionDeliveryInfo() {
        shop().goToPage();
        shop().interactHeader().clickToDeliveryAndPayment();
        delivery().checkPageIsAvailable();
        delivery().checkPageOpened();
    }

    @CaseId(1813)
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на страничку с Logo", groups = REGRESSION_STF)
    public void successTransitionLogo() {
        shop().goToPage();
        shop().interactHeader().clickToLogo();
        shop().checkPageIsAvailable();
        shop().checkPageOpened();
    }

    @CaseId(1439)
    @Story("Валидация элементов")
    @Test(description = "Тест валидности элементов в футере Сбермаркета", groups = {REGRESSION_STF, "MRAutoCheck"})
    public void successValidateElementInFooterSbermarket() {
        home().goToPage();
        home().checkPageIsAvailable();
        home().scrollDown();

        home().interactFooter().checkFooterVisible();
        home().interactFooter().checkLogoVisible();

        home().interactFooter().checkSbermarketTitleVisible();
        home().interactFooter().checkAboutCompanyLinkVisible();
        home().interactFooter().checkContactsLinkVisible();
        home().interactFooter().checkVacanciesLinkVisible();
        home().interactFooter().checkDocumentsLinkVisible();

        home().interactFooter().checkCustomerHelpTitleVisible();
        home().interactFooter().checkHowWeWorkVisible();
        home().interactFooter().checkDeliveryZoneVisible();
        home().interactFooter().checkDeliveryAndPaymentVisible();
        home().interactFooter().checkHelpVisible();

        home().interactFooter().checkHotlinePhoneNumberVisible();
        home().interactFooter().checkHotlineWorkHoursTextVisible();

        home().interactFooter().checkVkontakteButtonVisible();

        home().interactFooter().checkGooglePlayButtonVisible();
        home().interactFooter().checkAppstoreButtonVisible();
        home().interactFooter().checkHuaweiButtonVisible();
        home().interactFooter().checkApkButtonVisible();

        home().interactFooter().checkReturnsPolicyLinkVisible();
        home().interactFooter().checkPersonalDataPolicyLinkVisible();
        home().interactFooter().checkPublicOfferLinkVisible();
        home().interactFooter().assertAll();
    }

    @CaseId(3470)
    @Story("Статические страницы")
    @Test(
            dataProviderClass = StaticPage.class,
            dataProvider = "footerLinkPage",
            description = "Тест валидности переходов по ссылкам в футере Сбермаркета",
            groups = REGRESSION_STF)
    public void successFooterLinkTransition(final String text, final String url) {
        home().goToPage();
        home().scrollDown();

        home().interactFooter().clickToFooterElementWithText(text);
        home().checkPageContains(url);
    }

    @Skip
    @CaseId(3504)
    @Story("Статические страницы")
    @Test(description = "Тест валидности переходов по ссылкам в футере Сбермаркета, кейсы с открытием документа и модального окна",
            groups = REGRESSION_STF)
    public void successFooterLinkTransitionOtherCases() {
        home().goToPage();
        home().interactFooter().clickToFooterElementWithText("Обработка персональных данных");
        home().switchToNextWindow();
        privacyPolicy().checkPageContains(privacyPolicy().pageUrl());
    }

    @DoNotOpenBrowser
    @CaseId(1437)
    @Story("Витрины ретейлеров")
    @Test(dataProviderClass = StaticPage.class,
            dataProvider = "filteredAvailableRetailersSpree",
            description = "Тест доступности витрин ритейлеров Сбермаркета ",
            groups = REGRESSION_STF)
    public void successCheckSbermarketAvailableRetailers(final RetailerV2 retailer) {
        final String fullUrl = STF_FRONT_URL + retailer.getSlug();
        final var curl = new Curl.Builder(fullUrl)
                .withHeader(FORWARD_KEY, STF_FORWARD_TO)
                .withBasicAuth(BASIC_AUTH_USERNAME, BASIC_AUTH_PASSWORD)
                .build();
        assertTrue(CurlService.pageAvailable(curl), "Страница " + fullUrl + " недоступна");
    }

    @DoNotOpenBrowser
    @CaseId(1437)
    @Story("Витрины ретейлеров")
    @Test(dataProviderClass = StaticPage.class,
            dataProvider = "filteredUnavailableRetailersSpree",
            description = "Тест недоступности витрин ритейлеров Сбермаркета ",
            groups = REGRESSION_STF)
    public void successCheckSbermarketUnavailableRetailers(final RetailerV2 retailer) {
        final var fullUrl = STF_FRONT_URL + retailer.getSlug();
        final var curl = new Curl.Builder(fullUrl)
                .withHeader(FORWARD_KEY, STF_FORWARD_TO)
                .withBasicAuth(BASIC_AUTH_USERNAME, BASIC_AUTH_PASSWORD)
                .build();
        assertTrue(CurlService.pageUnavailable(curl), "Страница " + fullUrl + " доступна");
    }

    @DoNotOpenBrowser
    @CaseId(1433)
    @Story("Партнерские лендинги")
    @Test(description = "Тест доступности партнерских лендингов", groups = REGRESSION_STF)
    public void successCheckPartnerLandingsAreAvailable() {
        final var fullUrl = STF_FRONT_URL + aeroflot().pageUrl();
        final var curl = new Curl.Builder(fullUrl)
                .withHeader(FORWARD_KEY, STF_FORWARD_TO)
                .withBasicAuth(BASIC_AUTH_USERNAME, BASIC_AUTH_PASSWORD)
                .build();
        assertTrue(CurlService.pageAvailable(curl), "Страница " + fullUrl + " недоступна");
    }

    @DoNotOpenBrowser
    @CaseId(1814)
    @Story("Сервисные страницы")
    @Test(description = "Тест доступности сервисных страниц", groups = REGRESSION_STF)
    public void successCheckServicePagesAreAvailable() {
        final String fullUrl = STF_FRONT_URL + driversHiring().pageUrl();
        final var curl = new Curl.Builder(fullUrl)
                .withHeader(FORWARD_KEY, STF_FORWARD_TO)
                .withBasicAuth(BASIC_AUTH_USERNAME, BASIC_AUTH_PASSWORD)
                .build();
        assertTrue(CurlService.pageAvailable(curl), "Страница " + fullUrl + " недоступна");
    }

    @DoNotOpenBrowser
    @CaseId(1814)
    @Story("Сервисные страницы")
    @Test(description = "Тест доступности сервисных страниц", groups = REGRESSION_STF)
    public void successCheckJobLandingAreAvailable() {
        final var curl = new Curl.Builder(JOB_LANDING_URL).withUserAgent("Autotest").build();
        assertTrue(CurlService.pageAvailable(curl), "Страница " + JOB_LANDING_URL + " недоступна");
    }

    @DoNotOpenBrowser
    @CaseId(1432)
    @Story("Статические страницы")
    @Test(
            dataProviderClass = StaticPage.class,
            dataProvider = "faqPage",
            description = "Тест доступности статических страниц",
            groups = REGRESSION_STF)
    public void successCheckStaticPagesAreAvailable(final String url) {
        final var curl = new Curl.Builder(url)
                .withHeader(FORWARD_KEY, STF_FORWARD_TO)
                .withBasicAuth(BASIC_AUTH_USERNAME, BASIC_AUTH_PASSWORD)
                .build();
        assertTrue(CurlService.pageAvailable(curl), "Страница " + url + " недоступна");
    }
}
