package ru.instamart.test.reforged.site;

import org.testng.annotations.Test;
import ru.instamart.kraken.testdata.lib.Pages;
import ru.instamart.reforged.stf.page.StfPage;
import ru.instamart.test.reforged.BaseTest;
import static ru.instamart.reforged.stf.page.StfRouter.metro;

public class BasicMetroTests extends BaseTest {

    @Test(
            description = "Тест валидности элементов и ссылок в шапке METRO Delivery",
            groups = {"metro-smoke","metro-acceptance","metro-regression"}
    ) public void successValidateMetroTenantHeader() {
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

    @Test(
            description = "Тест валидности элементов и ссылок в подвале METRO Delivery",
            groups = {"metro-smoke","metro-acceptance","metro-regression"}
    ) public void successValidateMetroTenantFooter() {
        metro().goToPage();
        metro().checkPageIsAvailable();
        metro().addCookie(StfPage.cookieAlert);
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

    @Test(
            description = "Тест доступности / недоступности витрин ритейлеров Metro Delivery-CC",
            groups = {"metro-smoke","metro-acceptance","metro-regression"}
    ) public void successCheckMetroRetailers() {
        metro().openSitePage(Pages.Retailers.auchan().getPath());
        metro().checkPageIsUnavailable();
        metro().openSitePage(Pages.Retailers.azbuka().getPath());
        metro().checkPageIsUnavailable();
        metro().openSitePage(Pages.Retailers.vkusvill().getPath());
        metro().checkPageIsUnavailable();
        metro().openSitePage(Pages.Retailers.lenta().getPath());
        metro().checkPageIsUnavailable();
        metro().openSitePage(Pages.Retailers.karusel().getPath());
        metro().checkPageIsUnavailable();
        metro().openSitePage(Pages.Retailers.selgros().getPath());
        metro().checkPageIsUnavailable();
        metro().openSitePage(Pages.Retailers.flora().getPath());
        metro().checkPageIsUnavailable();
        metro().openSitePage(Pages.Retailers.foodcity().getPath());
        metro().checkPageIsUnavailable();
        metro().openSitePage(Pages.Retailers.magnit().getPath());
        metro().checkPageIsUnavailable();
        metro().openSitePage(Pages.Retailers.testretailer().getPath());
        metro().checkPageIsUnavailable();
        metro().openSitePage(Pages.Retailers.metro().getPath());
        metro().checkPageIsAvailable();
    }

    @Test(
            description = "Тест доступности статических страниц на METRO Delivery",
            groups = {"metro-smoke","metro-acceptance","metro-regression"}
    ) public void successCheckMetroTenantStaticPages() {
        metro().openSitePage(Pages.Metro.about().getPath());
        metro().checkPageIsAvailable();
        metro().openSitePage(Pages.Metro.delivery().getPath());
        metro().checkPageIsAvailable();
        metro().openSitePage(Pages.Metro.rules().getPath());
        metro().checkPageIsAvailable();
        metro().openSitePage(Pages.Metro.returnPolicy().getPath());
        metro().checkPageIsAvailable();
        metro().openSitePage(Pages.Metro.faq().getPath());
        metro().checkPageIsAvailable();
        metro().openSitePage(Pages.Metro.terms().getPath());
        metro().checkPageIsAvailable();
        metro().openSitePage(Pages.Metro.contacts().getPath());
        metro().checkPageIsAvailable();
    }
}
