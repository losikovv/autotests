package ru.instamart.test.reforged.okey;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.CookieProvider;
import ru.instamart.reforged.core.DoNotOpenBrowser;
import ru.instamart.reforged.core.data_provider.StaticPage;
import ru.instamart.reforged.core.service.Curl;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertTrue;
import static ru.instamart.reforged.okey.page.OkeyRouter.okey;

@Epic("OKEY UI")
@Feature("Базовые тесты тенанта окей")
public final class BasicOkeyTests {

    @CaseId(2777)
    @Story("Валидация элементов")
    @Test(description = "Тест валидности элементов и ссылок в шапке Okey", groups = "regression")
    public void successValidateOkeyTenantHeader() {
        okey().goToPage();
        okey().checkPageIsAvailable();

        okey().interactHeader().checkHeaderVisible();
        okey().interactHeader().checkSelectAddressButtonVisible();
        okey().interactHeader().checkSelectAddressTextButtonVisible();
        okey().interactHeader().checkHotlineWorkHoursVisible();
        okey().interactHeader().checkHotlinePhoneVisible();
        okey().interactHeader().checkShopLogoButtonVisible();
        okey().interactHeader().checkHelpVisible();
        okey().interactHeader().checkCategoryMenuVisible();
        okey().interactHeader().checkSearchInputVisible();
        okey().interactHeader().checkSearchButtonVisible();
        okey().interactHeader().checkCartVisible();
        okey().interactHeader().checkPartnershipLabelVisible();
        okey().interactHeader().checkLoginIsVisible();
        okey().interactHeader().checkNearestDeliveryLabelVisible();
    }

    @CaseId(2778)
    @Story("Валидация элементов")
    @Test(description = "Тест валидности элементов и ссылок в подвале Okey", groups = "regression")
    @CookieProvider(cookieFactory = CookieFactory.class, cookies = "COOKIE_ALERT")
    public void successValidateOkeyTenantFooter() {
        okey().goToPage();
        okey().checkPageIsAvailable();
        okey().scrollDown();

        okey().interactFooter().checkFooterVisible();
        okey().interactFooter().checkLogoVisible();

        okey().interactFooter().checkCopyrightTextVisible();
        okey().interactFooter().checkPartnershipLogoVisible();
        okey().interactFooter().checkCopyrightShopNameVisible();

        okey().interactFooter().checkHotlineTextVisible();
        okey().interactFooter().checkHotlinePhoneNumberVisible();
        okey().interactFooter().checkHotlineWorkHoursVisible();
        
        okey().interactFooter().checkDisclaimerVisible();

        okey().interactFooter().checkCustomerHelpVisible();
        okey().interactFooter().checkReturnsPolicyLinkVisible();
        okey().interactFooter().checkPublicOfferLinkVisible();
        okey().interactFooter().checkPersonalDataPolicyLinkVisible();

        okey().interactFooter().checkAboutCompanyVisible();
        okey().interactFooter().checkDeliveryZoneVisible();
        okey().interactFooter().checkDeliveryAndPaymentVisible();
        okey().interactFooter().checkPaymentInfoVisible();
    }

    @DoNotOpenBrowser
    @CaseId(2779)
    @Story("Витрины ретейлеров")
    @Test(  dataProviderClass = StaticPage.class,
            dataProvider = "filteredUnavailableRetailersSpree",
            description = "Тест недоступности витрин ретейлеров Okey",
            groups = "regression")
    public void successCheckOkeyUnavailableRetailers(final RetailerV2 retailer) {
        final String fullUrl = EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + retailer.getSlug();
        assertTrue(Curl.pageUnavailable(fullUrl), "Страница " + fullUrl + " доступна");
    }

    @DoNotOpenBrowser
    @CaseId(2779)
    @Story("Витрины ретейлеров")
    @Test(  dataProviderClass = StaticPage.class,
            dataProvider = "okeyAvailableRetailerPage",
            description = "Тест доступности витрин ретейлеров Okey",
            groups = "regression")
    public void successCheckOkeyAvailableRetailers(final String url) {
        assertTrue(Curl.pageAvailable(url), "Страница " + url + " доступна");
    }

    @DoNotOpenBrowser
    @CaseId(2780)
    @Story("Статические страницы")
    @Test(
            dataProviderClass = StaticPage.class,
            dataProvider = "okeyFaqPage",
            description = "Тест доступности статических страниц на Okey",
            groups = "regression")
    public void successCheckOkeyStaticPagesAreAvailable(final String url) {
        assertTrue(Curl.pageAvailable(url), "Страница " + url + " недоступна");
    }
}
