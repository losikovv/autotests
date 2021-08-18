package ru.instamart.test.reforged.site;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.core.DoNotOpenBrowser;
import ru.instamart.reforged.core.StaticPage;
import ru.instamart.reforged.core.service.Curl;
import ru.instamart.reforged.stf.site.metro.MetroPage;
import ru.instamart.test.reforged.BaseTest;

import static org.testng.Assert.assertTrue;
import static ru.instamart.reforged.stf.page.StfRouter.metro;

@Epic("METRO UI")
@Feature("Базовые тесты тенанта метро")
public class BasicMetroTests extends BaseTest {

    @CaseId(1440)
    @Story("Валидация элементов")
    @Test(
            description = "Тест валидности элементов и ссылок в шапке METRO Delivery",
            groups = {"metro-smoke", "metro-acceptance", "metro-regression"}
    )
    public void successValidateMetroTenantHeader() {
        metro().goToPage();
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
    @Test(
            description = "Тест валидности элементов и ссылок в подвале METRO Delivery",
            groups = {"metro-smoke", "metro-acceptance", "metro-regression"}
    )
    public void successValidateMetroTenantFooter() {
        metro().goToPage();
        metro().checkPageIsAvailable();
        metro().addCookie(MetroPage.cookieAlert);
        metro().refresh();
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
            groups = {"metro-smoke", "metro-acceptance", "metro-regression"}
    )
    public void successCheckMetroUnavailableRetailers(final RetailerV2 retailer) {
        final String fullUrl = EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + retailer.getSlug();
        assertTrue(Curl.pageUnavailable(fullUrl), "Страница " + fullUrl + " доступна");
    }

    @DoNotOpenBrowser
    @CaseId(1442)
    @Story("Витрины ретейлеров")
    @Test(  dataProviderClass = StaticPage.class,
            dataProvider = "metroAvailableRetailerPage",
            description = "Тест доступности витрин ретейлеров Metro Delivery-CC",
            groups = {"metro-smoke", "metro-acceptance", "metro-regression"}
    )
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
            groups = {"metro-smoke", "metro-acceptance", "metro-regression"}
    )
    public void successCheckStaticPagesAreAvailable(final String url) {
        assertTrue(Curl.pageAvailable(url), "Страница " + url + " недоступна");
    }
}
