package ru.instamart.test.reforged.selgros;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.DoNotOpenBrowser;
import ru.instamart.reforged.core.data_provider.StaticPage;
import ru.instamart.reforged.core.service.Curl;
import ru.instamart.test.reforged.BaseTest;

import static org.testng.Assert.assertTrue;
import static ru.instamart.reforged.selgros.page.SelgrosRouter.selgros;

@Epic("SELGROS UI")
@Feature("Базовые тесты тенанта селгрос")
public final class BasicSelgrosTests extends BaseTest {

    @CaseId(2781)
    @Story("Валидация элементов")
    @Test(
            description = "Тест валидности элементов и ссылок в шапке Selgros",
            groups = {"selgros-smoke", "selgros-acceptance", "selgros-regression"}
    )
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
    @Test(
            description = "Тест валидности элементов и ссылок в подвале Selgros",
            groups = {"selgros-smoke", "selgros-acceptance", "selgros-regression"}
    )
    public void successValidateSelgrosTenantFooter() {
        selgros().goToPage();
        selgros().checkPageIsAvailable();
        selgros().addCookie(CookieFactory.COOKIE_ALERT);
        selgros().refresh();
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
    @CaseId(2783)
    @Story("Витрины ретейлеров")
    @Test(  dataProviderClass = StaticPage.class,
            dataProvider = "filteredUnavailableRetailersSpree",
            description = "Тест недоступности витрин ретейлеров Selgros",
            groups = {"selgros-smoke", "selgros-acceptance", "selgros-regression"}
    )
    public void successCheckSelgrosUnavailableRetailers(final RetailerV2 retailer) {
        final String fullUrl = EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + retailer.getSlug();
        assertTrue(Curl.pageUnavailable(fullUrl), "Страница " + fullUrl + " доступна");
    }

    @DoNotOpenBrowser
    @CaseId(2783)
    @Story("Витрины ретейлеров")
    @Test(  dataProviderClass = StaticPage.class,
            dataProvider = "selgrosAvailableRetailerPage",
            description = "Тест доступности витрин ретейлеров Selgros",
            groups = {"selgros-smoke", "selgros-acceptance", "selgros-regression"}
    )
    public void successCheckSelgrosAvailableRetailers(final String url) {
        assertTrue(Curl.pageAvailable(url), "Страница " + url + " доступна");
    }

    @DoNotOpenBrowser
    @CaseId(2784)
    @Story("Статические страницы")
    @Test(
            dataProviderClass = StaticPage.class,
            dataProvider = "selgrosFaqPage",
            description = "Тест доступности статических страниц на Selgros",
            groups = {"selgros-smoke", "selgros-acceptance", "selgros-regression"}
    )
    public void successCheckSelgrosStaticPagesAreAvailable(final String url) {
        assertTrue(Curl.pageAvailable(url), "Страница " + url + " недоступна");
    }
}
