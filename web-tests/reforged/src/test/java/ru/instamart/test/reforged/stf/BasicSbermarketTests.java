package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.DoNotOpenBrowser;
import ru.instamart.reforged.core.StaticPage;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.instamart.reforged.core.service.Curl;
import ru.instamart.reforged.stf.page.shop.ShopPage;
import ru.instamart.test.reforged.BaseTest;

import static org.testng.Assert.assertTrue;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Лэндинг")
public final class BasicSbermarketTests extends BaseTest {

    @CaseId(1438)
    @Story("Валидация элементов")
    @Test(
            description = "Тест валидности элементов и ссылок в шапке Сбермаркета",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successValidateHeader() {
        shop().goToPage(ShopUrl.METRO);
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
        shop().interactHeader().checkStoreSelectorVisible();
        shop().interactHeader().checkSearchInputVisible();
        shop().interactHeader().checkSearchButtonVisible();
        shop().interactHeader().checkCartVisible();
        shop().interactHeader().checkFavoritesNoAuthVisible();
        shop().interactHeader().checkLoginIsVisible();
    }

    @CaseId(733)
    @Story("Навигация")
    @Test(
            description = "Тест перехода из Сбермаркета на как мы работаем",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successTransitionHowWeWork(){
        shop().goToPage(ShopUrl.METRO);
        shop().interactHeader().clickToHowWeWork();
        howWeWork().checkPageIsAvailable();
    }

    @CaseId(1810)
    @Story("Навигация")
    @Test(
            description = "Тест перехода из Сбермаркета на информацию о контактах",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successTransitionContactsInfo(){
        shop().goToPage(ShopUrl.METRO);
        shop().interactHeader().clickToContacts();
        contacts().checkPageIsAvailable();
    }

    @CaseId(1811)
    @Story("Навигация")
    @Test(
            description = "Тест перехода из Сбермаркета на страничку с помощью для клиента",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successTransitionHelpInfo(){
        shop().goToPage(ShopUrl.METRO);
        shop().interactHeader().clickToContacts();
        faq().checkPageIsAvailable();
    }

    @CaseId(1812)
    @Story("Навигация")
    @Test(
            description = "Тест перехода из Сбермаркета на страничку с информацией о доставке",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successTransitionDeliveryInfo(){
        shop().goToPage(ShopUrl.METRO);
        shop().interactHeader().clickToContacts();
        delivery().checkPageIsAvailable();
    }

    @CaseId(1813)
    @Story("Навигация")
    @Test(
            description = "Тест перехода из Сбермаркета на страничку с Logo",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successTransitionLogo(){
        shop().goToPage(ShopUrl.METRO);
        shop().interactHeader().clickToLogo();
        home().checkPageIsAvailable();
    }

    @CaseId(1439)
    @Story("Валидация элементов")
    @Test(
            description = "Тест валидности элементов в футере Сбермаркета",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successValidateElementInFooterSbermarket() {
        shop().goToPage(ShopUrl.METRO);
        shop().checkPageIsAvailable();
        shop().addCookie(CookieFactory.COOKIE_ALERT);
        shop().refresh();
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

        shop().interactFooter().checkGooglePlayButtonVisible();
        shop().interactFooter().checkAppstoreButtonVisible();
        shop().interactFooter().checkHuaweiButtonVisible();

        shop().interactFooter().checkReturnsPolicyLinkVisible();
        shop().interactFooter().checkPersonalDataPolicyLinkVisible();
        shop().interactFooter().checkPublicOfferLinkVisible();
    }

    @CaseId(1439)
    @Story("Валидация элементов")
    @Test(
            description = "Тест валидности переходов по ссылка в футере Сбермаркета",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void testFooterLink() {
        home().goToPage();
        home().addCookie(CookieFactory.COOKIE_ALERT);
        home().refresh();
        home().scrollDown();

        home().interactFooter().clickToAbout();
        about().checkPageIsAvailable();

        home().goToPage();
        home().scrollDown();
        home().interactFooter().clickToContacts();
        contacts().checkPageIsAvailable();

        home().goToPage();
        home().scrollDown();
        home().interactFooter().clickToReturnPolicy();
        rules().checkPageIsAvailable();

        home().goToPage();
        home().scrollDown();
        home().interactFooter().clickToPublicOffer();
        terms().checkPageIsAvailable();
    }

    @DoNotOpenBrowser
    @CaseId(1437)
    @Story("Витрины ретейлеров")
    @Test(  dataProviderClass = StaticPage.class,
            dataProvider = "filteredAvailableRetailersSpree",
            description = "Тест доступности витрин ритейлеров Сбермаркета ",
            groups = {"sbermarket-Ui-smoke","MRAutoCheck","ui-smoke-production"}
    )
    public void successCheckSbermarketAvailableRetailers(final RetailerV2 retailer) {
        final String fullUrl = EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + retailer.getSlug();
        assertTrue(Curl.pageAvailable(fullUrl), "Страница " + fullUrl + " недоступна");
    }

    @DoNotOpenBrowser
    @CaseId(1437)
    @Story("Витрины ретейлеров")
    @Test(  dataProviderClass = StaticPage.class,
            dataProvider = "filteredUnavailableRetailersSpree",
            description = "Тест недоступности витрин ритейлеров Сбермаркета ",
            groups = {"sbermarket-Ui-smoke","MRAutoCheck","ui-smoke-production"}
    )
    public void successCheckSbermarketUnavailableRetailers(final RetailerV2 retailer) {
        final String fullUrl = EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + retailer.getSlug();
        assertTrue(Curl.pageUnavailable(fullUrl), "Страница " + fullUrl + " доступна");
    }

    @DoNotOpenBrowser
    @CaseId(1433)
    @Story("Партнерские лендинги")
    @Test(
            dataProviderClass = StaticPage.class,
            dataProvider = "landingPage",
            description = "Тест доступности партнерских лендингов",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successCheckPartnerLandingsAreAvailable(final String url) {
        assertTrue(Curl.pageAvailable(url), "Страница " + url + " недоступна");
    }

    @DoNotOpenBrowser
    @CaseId(1814)
    @Story("Сервисные страницы")
    @Test(
            dataProviderClass = StaticPage.class,
            dataProvider = "servicePage",
            description = "Тест доступности сервисных страниц",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )

    public void successServicePagesAreAvailable(final String url) {
        assertTrue(Curl.pageAvailable(url), "Страница " + url + " недоступна");
    }

    @DoNotOpenBrowser
    @CaseId(1432)
    @Story("Статические страницы")
    @Test(
            dataProviderClass = StaticPage.class,
            dataProvider = "faqPage",
            description = "Тест доступности статических страниц",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successCheckStaticPagesAreAvailable(final String url) {
        assertTrue(Curl.pageAvailable(url), "Страница " + url + " недоступна");
    }
}
