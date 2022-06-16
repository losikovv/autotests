package ru.instamart.test.reforged.metro;

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
import static ru.instamart.reforged.metro.page.MetroRouter.metro;

@Epic("METRO UI")
@Feature("Базовые тесты тенанта метро")
public final class BasicMetroTests {

    @CaseId(1440)
    @Story("Валидация элементов")
    @Test(description = "Тест валидности элементов и ссылок в шапке METRO Delivery", groups = "regression")
    public void successValidateMetroTenantHeader() {
        metro().goToPage(true);
        metro().checkPageIsAvailable();

        metro().interactHeader().checkHeaderVisible();
        metro().interactHeader().checkSelectAddressButtonVisible();
        metro().interactHeader().checkSelectAddressTextButtonVisible();
        metro().interactHeader().checkHotlineWorkHoursVisible();
        metro().interactHeader().checkHotlinePhoneVisible();
        metro().interactHeader().checkDeliveryButtonVisible();
        metro().interactHeader().checkPickupButtonVisible();
        metro().interactHeader().checkShopLogoButtonVisible();
        metro().interactHeader().checkForB2bVisible();
        metro().interactHeader().checkHelpVisible();
        metro().interactHeader().checkCategoryMenuVisible();
        metro().interactHeader().checkSearchInputVisible();
        metro().interactHeader().checkSearchButtonVisible();
        metro().interactHeader().checkCartVisible();
        metro().interactHeader().checkPartnershipLabelVisible();
        metro().interactHeader().checkLoginIsVisible();
        metro().interactHeader().checkNearestDeliveryLabelVisible();
    }

    @CaseId(1441)
    @Story("Валидация элементов")
    @Test(description = "Тест валидности элементов и ссылок в подвале METRO Delivery", groups = "regression")
    @CookieProvider(cookieFactory = CookieFactory.class, cookies = "COOKIE_ALERT")
    public void successValidateMetroTenantFooter() {
        metro().goToPage();
        metro().checkPageIsAvailable();
        metro().scrollDown();

        metro().interactFooter().checkFooterVisible();
        metro().interactFooter().checkLogoVisible();

        metro().interactFooter().checkCopyrightTextVisible();
        metro().interactFooter().checkPartnershipLogoVisible();
        metro().interactFooter().checkCopyrightShopNameVisible();

        metro().interactFooter().checkHotlineTextVisible();
        metro().interactFooter().checkHotlinePhoneNumberVisible();
        metro().interactFooter().checkHotlineWorkHoursVisible();
        
        metro().interactFooter().checkDisclaimerVisible();

        metro().interactFooter().checkCustomerHelpVisible();
        metro().interactFooter().checkReturnsPolicyLinkVisible();
        metro().interactFooter().checkPublicOfferLinkVisible();
        metro().interactFooter().checkPersonalDataPolicyLinkVisible();

        metro().interactFooter().checkAboutCompanyVisible();
        metro().interactFooter().checkDeliveryZoneVisible();
        metro().interactFooter().checkDeliveryAndPaymentVisible();
        metro().interactFooter().checkPaymentInfoVisible();
    }

    @DoNotOpenBrowser
    @CaseId(1442)
    @Story("Витрины ретейлеров")
    @Test(  dataProviderClass = StaticPage.class,
            dataProvider = "filteredUnavailableRetailersSpree",
            description = "Тест недоступности витрин ретейлеров Metro Delivery-CC",
            groups = "regression")
    public void successCheckMetroUnavailableRetailers(final RetailerV2 retailer) {
        final String fullUrl = EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + retailer.getSlug();
        assertTrue(Curl.pageUnavailable(fullUrl), "Страница " + fullUrl + " доступна");
    }

    @DoNotOpenBrowser
    @CaseId(1442)
    @Story("Витрины ретейлеров")
    @Test(  dataProviderClass = StaticPage.class,
            dataProvider = "metroAvailableRetailerPage",
            description = "Тест доступности витрин ретейлеров Metro Delivery-CC",
            groups = "regression")
    public void successCheckMetroAvailableRetailers(final String url) {
        assertTrue(Curl.pageAvailable(url), "Страница " + url + " доступна");
    }

    @DoNotOpenBrowser
    @CaseId(1443)
    @Story("Статические страницы")
    @Test(
            dataProviderClass = StaticPage.class,
            dataProvider = "metroFaqPage",
            description = "Тест доступности статических страниц на METRO Delivery",
            groups = "regression")
    public void successCheckStaticPagesAreAvailable(final String url) {
        assertTrue(Curl.pageAvailable(url), "Страница " + url + " недоступна");
    }
}
