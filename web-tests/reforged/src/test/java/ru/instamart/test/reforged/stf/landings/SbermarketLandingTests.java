package ru.instamart.test.reforged.stf.landings;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.Group.STARTING_X;
import static ru.instamart.reforged.stf.page.StfRouter.home;

@Epic("STF UI")
@Feature("Проверка лендингов")
public final class SbermarketLandingTests {

    @CaseId(1687)
    @Test(description = "Тест валидности и наличия элементов лендинга Сбермаркета", groups = {STARTING_X, REGRESSION_STF})
    public void successValidateSbermarketLanding() {
        home().goToPage();
        home().checkHeaderContainerIsVisible();
        home().checkHeaderLogoIsVisible();
        home().checkLoginButtonIsVisible();

        home().checkAddressBlockContainerIsVisible();
        home().checkAddressBlockTextIsVisible();
        home().checkAddressBlockAddressButtonIsVisible();

        home().checkDeliveryRetailersContainerVisible();
        home().checkDeliveryTitleVisible();
        home().checkDeliverySubTitleVisible();

        home().checkAdvantagesContainerVisible();
        home().checkInfoContainerVisible();
        home().checkCitiesContainerVisible();

        home().checkB2bButtonContainerVisible();
        home().checkB2bButtonContainerVisible();
        home().checkStepsContainerVisible();

        home().checkAppContainerVisible();
        home().checkGooglePlayButtonVisible();
        home().checkAppStoreButtonVisible();
        home().checkHuaweiPlayButtonVisible();
    }

    @CaseId(2042)
    @Test(description = "Кнопка СберБизнес ID при выбранном чекбоксе 'Хочу заказывать для бизнеса'", groups = {STARTING_X, REGRESSION_STF})
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
        home().interactAuthModal().checkMailRuIsVisible();
    }
}