package ru.instamart.test.reforged.stf_prod;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.reforged.core.annotation.DoNotOpenBrowser;
import ru.instamart.reforged.core.data_provider.StaticPage;
import ru.instamart.reforged.core.service.curl.Curl;
import ru.instamart.reforged.core.service.curl.CurlService;

import static org.testng.Assert.assertTrue;
import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.core.config.BasicProperties.JOB_LANDING_URL;
import static ru.instamart.reforged.core.config.UiProperties.STF_URL;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Лэндинг")
public final class BasicSbermarketTests {

    @TmsLink("1438")
    @Story("Валидация элементов")
    @Test(description = "Тест валидности элементов и ссылок в шапке Сбермаркета", groups = {STF_PROD_S})
    public void successValidateHeader() {
        shop().goToPageProd();
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

    @TmsLink("733")
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на как мы работаем", groups = {STF_PROD_S})
    public void successTransitionHowWeWork() {
        shop().goToPageProd();
        shop().interactHeader().clickToHowWeWork();
        howWeWork().checkPageIsAvailable();
        howWeWork().checkPageOpened();
    }

    @TmsLink("1810")
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на информацию о контактах", groups = {STF_PROD_S})
    public void successTransitionContactsInfo() {
        shop().goToPageProd();
        shop().interactHeader().clickToContacts();
        contacts().checkPageIsAvailable();
        contacts().checkPageOpened();
    }

    @TmsLink("1811")
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на страничку с помощью для клиента", groups = {STF_PROD_S})
    public void successTransitionHelpInfo() {
        shop().goToPageProd();
        shop().interactHeader().clickToHelp();
        faq().checkPageIsAvailable();
        faq().checkPageOpened();
    }

    @TmsLink("1812")
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на страничку с информацией о доставке", groups = {STF_PROD_S})
    public void successTransitionDeliveryInfo() {
        shop().goToPageProd();
        shop().interactHeader().clickToDeliveryAndPayment();
        delivery().checkPageIsAvailable();
        delivery().checkPageOpened();
    }

    @TmsLink("1813")
    @Story("Навигация")
    @Test(description = "Тест перехода из Сбермаркета на страничку с Logo", groups = {STF_PROD_S})
    public void successTransitionLogo() {
        shop().goToPageProd();
        shop().interactHeader().clickToLogo();
        shop().checkPageIsAvailable();
        shop().checkPageOpened();
    }

    @TmsLink("1439")
    @Story("Валидация элементов")
    @Test(description = "Тест валидности элементов в футере Сбермаркета", groups = {STF_PROD_S})
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

    @TmsLink("3470")
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
        home().waitPageLoad();
        home().checkPageOpened();
        home().checkPageContains(url);
    }

    @Skip //ошибка рендера
    @TmsLink("3504")
    @Story("Статические страницы")
    @Test(description = "Тест валидности переходов по ссылкам в футере Сбермаркета, кейсы с открытием документа и модального окна",
            groups = {STF_PROD_S})
    public void successFooterLinkTransitionOtherCases() {
        home().goToPage();
        home().scrollDown();

        home().interactFooter().clickToFooterElementWithText("Обработка персональных данных");
        home().switchToNextWindow();
        privacyPolicy().checkPageUrl(STF_URL + privacyPolicy().pageUrl());
    }

    @TmsLink("3470")
    @Story("Статические страницы")
    //вынесено в отдельный тест, тк в муне не хочет никак открывать ссылку на https://storage.yandexcloud.net/
    @Test(description = "Тест валидности ссылки про коррупцию в футере Сбермаркета",
            groups = {STF_PROD_S})
    public void successCorruptionFooterLink() {
        home().goToPage();
        home().scrollDown();

        var corruptionLink = "https://storage.yandexcloud.net/sbermarket-prod-compliance/" +
        "%D0%9F%D1%80%D0%B8%D0%BA%D0%B0%D0%B7%20%E2%84%96%20%D0%9F-2022-04-18%20%D0%BE%D1%82%2019.04.2022%20%D0%98%D0%A1_.pdf";

        home().interactFooter().checkCorruptionElementContains("href", corruptionLink);
    }

    @DoNotOpenBrowser
    @TmsLink("1814")
    @Story("Сервисные страницы")
    @Test(description = "Тест доступности сервисных страниц", groups = {STF_PROD_S})
    public void successCheckServicePagesAreAvailable() {
        final String fullUrl = STF_URL + driversHiring().pageUrl();
        final var curl = new Curl.Builder(fullUrl).build();
        assertTrue(CurlService.pageAvailable(curl), "Страница " + fullUrl + " недоступна");
    }

    @DoNotOpenBrowser
    @TmsLink("1814")
    @Story("Сервисные страницы")
    @Test(description = "Тест доступности сервисных страниц", groups = {STF_PROD_S})
    public void successCheckJobLandingAreAvailable() {
        final var curl = new Curl.Builder(JOB_LANDING_URL).withUserAgent("Autotest").build();
        assertTrue(CurlService.pageAvailable(curl), "Страница " + JOB_LANDING_URL + " недоступна");
    }

    @DoNotOpenBrowser
    @TmsLink("1432")
    @Story("Статические страницы")
    @Test(
            dataProviderClass = StaticPage.class,
            dataProvider = "faqPage",
            description = "Тест доступности статических страниц",
            groups = {STF_PROD_S})
    public void successCheckStaticPagesAreAvailable(final String url) {
        final var curl = new Curl.Builder(url).withUserAgent("Autotest").build();
        assertTrue(CurlService.pageAvailable(curl), "Страница " + url + " недоступна");
    }
}
