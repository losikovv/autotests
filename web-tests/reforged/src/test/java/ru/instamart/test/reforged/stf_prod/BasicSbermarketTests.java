package ru.instamart.test.reforged.stf_prod;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.reforged.core.DoNotOpenBrowser;
import ru.instamart.reforged.core.config.BasicProperties;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.data_provider.StaticPage;
import ru.instamart.reforged.core.service.Curl;
import ru.instamart.reforged.core.service.CurlService;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertTrue;
import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Лэндинг")
public final class BasicSbermarketTests {

    @CaseId(1438)
    @Story("Валидация элементов")
    @Test(description = "Тест валидности элементов и ссылок в шапке Сбермаркета", groups = {STF_PROD_S})
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
    @Test(description = "Тест перехода из Сбермаркета на как мы работаем", groups = {STF_PROD_S})
    public void successTransitionHowWeWork() {
        shop().goToPage();
        shop().interactHeader().clickToHowWeWork();
        howWeWork().checkPageIsAvailable();
        howWeWork().checkPageOpened();
    }

    @CaseId(1810)
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на информацию о контактах", groups = {STF_PROD_S})
    public void successTransitionContactsInfo() {
        shop().goToPage();
        shop().interactHeader().clickToContacts();
        contacts().checkPageIsAvailable();
        contacts().checkPageOpened();
    }

    @CaseId(1811)
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на страничку с помощью для клиента", groups = {STF_PROD_S})
    public void successTransitionHelpInfo() {
        shop().goToPage();
        shop().interactHeader().clickToHelp();
        faq().checkPageIsAvailable();
        faq().checkPageOpened();
    }

    @CaseId(1812)
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на страничку с информацией о доставке", groups = {STF_PROD_S})
    public void successTransitionDeliveryInfo() {
        shop().goToPage();
        shop().interactHeader().clickToDeliveryAndPayment();
        delivery().checkPageIsAvailable();
        delivery().checkPageOpened();
    }

    @CaseId(1813)
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на страничку с Logo", groups = {STF_PROD_S})
    public void successTransitionLogo() {
        shop().goToPage();
        shop().interactHeader().clickToLogo();
        shop().checkPageIsAvailable();
        shop().checkPageOpened();
    }

    @CaseId(1439)
    @Story("Валидация элементов")
    @Test(description = "Тест валидности элементов в футере Сбермаркета", groups = {STF_PROD_S})
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

        shop().interactFooter().checkCustomerHelpTitleVisible();
        shop().interactFooter().checkHowWeWorkVisible();
        shop().interactFooter().checkDeliveryZoneVisible();
        shop().interactFooter().checkDeliveryAndPaymentVisible();
        shop().interactFooter().checkHelpVisible();

        shop().interactFooter().checkHotlinePhoneNumberVisible();
        shop().interactFooter().checkHotlineWorkHoursTextVisible();

        shop().interactFooter().checkVkontakteButtonVisible();
        shop().interactFooter().checkGooglePlayButtonVisible();
        shop().interactFooter().checkAppstoreButtonVisible();
        shop().interactFooter().checkHuaweiButtonVisible();

        shop().interactFooter().checkReturnsPolicyLinkVisible();
        shop().interactFooter().checkPersonalDataPolicyLinkVisible();
        shop().interactFooter().checkPublicOfferLinkVisible();
        shop().interactFooter().assertAll();
    }

    @CaseId(3470)
    @Story("Статические страницы")
    @Test(
            dataProviderClass = StaticPage.class,
            dataProvider = "footerLinkPage",
            description = "Тест валидности переходов по ссылкам в футере Сбермаркета",
            groups = {STF_PROD_S})
    public void successFooterLinkTransition(final String text, final String url) {
        home().goToPage();
        home().scrollDown();

        home().interactFooter().clickToFooterElementWithText(text);
        home().checkPageContains(url);
    }

    @Skip //ошибка рендера
    @CaseId(3504)
    @Story("Статические страницы")
    @Test(description = "Тест валидности переходов по ссылкам в футере Сбермаркета, кейсы с открытием документа и модального окна",
            groups = {STF_PROD_S})
    public void successFooterLinkTransitionOtherCases() {
        home().goToPage();
        home().scrollDown();

        home().interactFooter().clickToFooterElementWithText("Обработка персональных данных");
        home().switchToNextWindow();
        privacyPolicy().checkPageUrl(UiProperties.STF_URL + privacyPolicy().pageUrl());
    }

    @DoNotOpenBrowser
    @CaseId(1814)
    @Story("Сервисные страницы")
    @Test(description = "Тест доступности сервисных страниц", groups = {STF_PROD_S})
    public void successCheckServicePagesAreAvailable() {
        final String fullUrl = UiProperties.STF_URL + driversHiring().pageUrl();
        final var curl = new Curl.Builder(fullUrl).build();
        assertTrue(CurlService.pageAvailable(curl), "Страница " + fullUrl + " недоступна");
    }

    @DoNotOpenBrowser
    @CaseId(1814)
    @Story("Сервисные страницы")
    @Test(description = "Тест доступности сервисных страниц", groups = {STF_PROD_S})
    public void successCheckJobLandingAreAvailable() {
        final var curl = new Curl.Builder(BasicProperties.JOB_LANDING_URL)
                .withUserAgent("Autotest")
                .build();
        assertTrue(CurlService.pageAvailable(curl), "Страница " + BasicProperties.JOB_LANDING_URL + " недоступна");
    }

    @DoNotOpenBrowser
    @CaseId(1432)
    @Story("Статические страницы")
    @Test(
            dataProviderClass = StaticPage.class,
            dataProvider = "faqPage",
            description = "Тест доступности статических страниц",
            groups = {STF_PROD_S})
    public void successCheckStaticPagesAreAvailable(final String url) {
        final var curl = new Curl.Builder(url).build();
        assertTrue(CurlService.pageAvailable(curl), "Страница " + url + " недоступна");
    }
}
