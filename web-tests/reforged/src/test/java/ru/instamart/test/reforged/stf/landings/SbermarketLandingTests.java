package ru.instamart.test.reforged.stf.landings;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.stf.page.StfRouter.home;

@Epic("STF UI")
@Feature("Проверка лендингов")
public class SbermarketLandingTests extends BaseTest {

    //АБ по главной отключили, нужно переписать ATST-872
    @CaseId(1687)
    @Test(enabled = false, description = "Тест валидности и наличия элементов лендинга Сбермаркета", groups = "regression")
    public void successValidateSbermarketLanding() {
        home().goToPage();
        home().addCookie(CookieFactory.COOKIE_ALERT);
        home().refresh();
        home().checkHeaderContainerIsVisible();
        home().checkHeaderLogoIsVisible();
        home().checkLoginButtonIsVisible();

        home().checkMainBlockContainerIsVisible();
        home().checkMainBlockAddressButtonIsVisible();
        home().checkMainBlockTextIsVisible();

        home().checkStoresListIsVisible();
        home().checkStoresButtonIsVisible();
        home().checkShowAllRetailerslIsVisible();
        home().clickToShowAllRetailers();
        home().checkStoresButtonAuchanIsVisible();
        home().checkStoresButtonMetroIsVisible();
        home().checkStoresButtonLentaIsVisible();

        home().checkAdvantagesBlockContainerIsVisible();
        home().checkDeliveryAdvIsVisible();
        home().checkHeavyToDoorAdvIsVisible();
        home().checkGoodQualityAdvIsVisible();
        home().checkSaleAdvIsVisible();

        home().checkZonesBlockContainerIsVisible();
        home().checkZonesBlockShowAllButtonIsVisible();

        home().checkOrderBlockContainerIsVisible();
        home().checkOrderBlockStepFirstIsVisible();
        home().checkOrderBlockStepSecondIsVisible();
        home().checkOrderBlockStepThirdIsVisible();

        home().checkAppsBlockContainerIsVisible();
        home().checkAppStoreIsVisible();
        home().checkGooglePlayIsVisible();
        home().checkAppGalleryContainerIsVisible();

        home().checkFooterContainerIsVisible();
    }

    @CaseId(2042)
    @Test(description = "Кнопка СберБизнес ID при выбранном чекбоксе 'Хочу заказывать для бизнеса'", groups = "regression")
    public void enabledSberBusinessIdButton() {
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().checkModalIsVisible();
        home().interactAuthModal().checkForBusiness();
        home().interactAuthModal().checkSberBusinessIdIsVisible();
        home().interactAuthModal().uncheckForBusiness();
        home().interactAuthModal().checkSberBusinessIdIsNotVisible();
        home().interactAuthModal().checkSberIdIsVisible();
        home().interactAuthModal().checkVkIsVisible();
        home().interactAuthModal().checkFacebookIsVisible();
        home().interactAuthModal().checkMailRuIsVisible();
    }
}