package ru.instamart.test.reforged.selgros;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.reforged.core.CookieProvider;
import ru.instamart.reforged.core.DoNotOpenBrowser;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.data_provider.StaticPage;
import ru.instamart.reforged.core.service.Curl;
import ru.instamart.reforged.core.service.CurlService;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertTrue;
import static ru.instamart.reforged.selgros.page.SelgrosRouter.selgros;

@Epic("SELGROS UI")
@Feature("Базовые тесты тенанта селгрос")
public final class BasicSelgrosTests {

    @CaseId(2781)
    @Story("Валидация элементов")
    @CookieProvider(cookies = {"FORWARD_FEATURE_SELGROS", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Тест валидности элементов и ссылок в шапке Selgros", groups = "regression")
    public void successValidateSelgrosTenantHeader() {
        selgros().goToPage();
        selgros().checkPageIsAvailable();

        selgros().interactHeader().checkHeaderVisible();
        selgros().interactHeader().checkDeliveryButtonVisible();
        selgros().interactHeader().checkPickupButtonVisible();
        selgros().interactHeader().checkSelectAddressButtonVisible();
        selgros().interactHeader().checkSelectAddressTextButtonVisible();
        selgros().interactHeader().checkHotlineWorkHoursVisible();
        selgros().interactHeader().checkHotlinePhoneVisible();
        selgros().interactHeader().checkShopLogoButtonVisible();
        selgros().interactHeader().checkHelpVisible();
        selgros().interactHeader().checkCategoryMenuVisible();
        selgros().interactHeader().checkSearchInputVisible();
        selgros().interactHeader().checkSearchButtonVisible();
        selgros().interactHeader().checkCartVisible();
        selgros().interactHeader().checkPartnershipLabelVisible();
        selgros().interactHeader().checkLoginIsVisible();
        selgros().interactHeader().checkNearestDeliveryLabelVisible();
    }

    @CaseId(2782)
    @Story("Валидация элементов")
    @CookieProvider(cookies = {"FORWARD_FEATURE_SELGROS", "COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_GUEST"})
    @Test(description = "Тест валидности элементов и ссылок в подвале Selgros", groups = "regression")
    public void successValidateSelgrosTenantFooter() {
        selgros().goToPage();
        selgros().checkPageIsAvailable();
        selgros().scrollDown();

        selgros().interactFooter().checkFooterVisible();
        selgros().interactFooter().checkLogoVisible();

        selgros().interactFooter().checkCopyrightTextVisible();
        selgros().interactFooter().checkPartnershipLogoVisible();
        selgros().interactFooter().checkCopyrightShopNameVisible();

        selgros().interactFooter().checkHotlineTextVisible();
        selgros().interactFooter().checkHotlinePhoneNumberVisible();
        selgros().interactFooter().checkHotlineWorkHoursVisible();

        selgros().interactFooter().checkDisclaimerVisible();

        selgros().interactFooter().checkCustomerHelpVisible();
        selgros().interactFooter().checkReturnsPolicyLinkVisible();
        selgros().interactFooter().checkPublicOfferLinkVisible();
        selgros().interactFooter().checkPersonalDataPolicyLinkVisible();

        selgros().interactFooter().checkAboutCompanyVisible();
        selgros().interactFooter().checkDeliveryZoneVisible();
        selgros().interactFooter().checkDeliveryAndPaymentVisible();
        selgros().interactFooter().checkPaymentInfoVisible();
    }

    @DoNotOpenBrowser
    @Issue("FEP-3229")
    @CaseId(2783)
    @Story("Витрины ретейлеров")
    @Test(enabled = false,
            dataProviderClass = StaticPage.class,
            dataProvider = "selgrosUnavailableRetailersSpree",
            description = "Тест недоступности витрин ретейлеров Selgros",
            groups = "regression")
    public void successCheckSelgrosUnavailableRetailers(final RetailerV2 retailer) {
        final String fullUrl = UiProperties.SELGROS_URL + retailer.getSlug();
        final var curl = new Curl.Builder(fullUrl)
                .withHeader(UiProperties.HEADER_SELGROS_FORWARD_TO)
                .withBasicAuth(CoreProperties.BASIC_AUTH_USERNAME, CoreProperties.BASIC_AUTH_PASSWORD)
                .build();
        assertTrue(CurlService.pageUnavailable(curl), "Страница " + fullUrl + " доступна");
    }

    @DoNotOpenBrowser
    @CaseId(2783)
    @Story("Витрины ретейлеров")
    @Test(description = "Тест доступности витрин ретейлеров Selgros", groups = "regression")
    public void successCheckSelgrosAvailableRetailers() {
        final var fullUrl = UiProperties.SELGROS_URL + selgros().pageUrl();
        final var curl = new Curl.Builder(fullUrl)
                .withHeader(UiProperties.HEADER_SELGROS_FORWARD_TO)
                .withBasicAuth(CoreProperties.BASIC_AUTH_USERNAME, CoreProperties.BASIC_AUTH_PASSWORD)
                .build();
        assertTrue(CurlService.pageAvailable(curl), "Страница " + fullUrl + " доступна");
    }

    @DoNotOpenBrowser
    @CaseId(2784)
    @Story("Статические страницы")
    @Test(
            dataProviderClass = StaticPage.class,
            dataProvider = "selgrosFaqPage",
            description = "Тест доступности статических страниц на Selgros",
            groups = "regression")
    public void successCheckSelgrosStaticPagesAreAvailable(final String url) {
        final var curl = new Curl.Builder(url)
                .withHeader(UiProperties.HEADER_SELGROS_FORWARD_TO)
                .withBasicAuth(CoreProperties.BASIC_AUTH_USERNAME, CoreProperties.BASIC_AUTH_PASSWORD)
                .build();
        assertTrue(CurlService.pageAvailable(curl), "Страница " + url + " недоступна");
    }
}
