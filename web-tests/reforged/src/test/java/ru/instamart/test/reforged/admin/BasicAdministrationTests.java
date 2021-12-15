package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import io.qameta.allure.Story;
import ru.sbermarket.qase.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Базовый функционал и навигация в админке")
public final class BasicAdministrationTests extends BaseTest {

    @CaseId(419)
    @Story("Тест доступности корневых разделов админки")
    @Test(description = "Тест доступности корневых разделов админки", groups = {"acceptance", "regression", "smoke"})
    public void successCheckAdminSectionsAvailability() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shipments().goToPage();
        shipments().checkPageIsAvailable();
        retailers().goToPage();
        retailers().checkPageIsAvailable();
        products().goToPage();
        products().checkPageIsAvailable();
        imports().goToPage();
        imports().checkPageIsAvailable();
        settings().goToPage();
        settings().checkPageIsAvailable();
        marketing().goToPage();
        marketing().checkPageIsAvailable();
        staff().goToPage();
        staff().checkPageIsAvailable();
        users().goToPage();
        users().checkPageIsAvailable();
        pages().goToPage();
        pages().checkPageIsAvailable();
    }

    @CaseId(420)
    @Story("Тест доступности вьюхи oktell")
    @Test(description = "Тест доступности вьюхи oktell", groups = {"acceptance", "regression"})
    public void successCheckOktellViewAvailability() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        oktell().openSitePage(oktell().pageUrl());
        oktell().checkPageIsAvailable();
    }

    @CaseId(416)
    @Story("Проверка наличия элементов в шапке админки")
    @Test(description = "Проверка наличия элементов в шапке админки", groups = {"acceptance", "regression"})
    public void successValidateHeader() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        main().interactAuthoredHeader().checkAdminNavigationTitle();
        main().interactAuthoredHeader().checkAdminAuth();
        main().interactAuthoredHeader().checkAdminAvatar();
        main().interactAuthoredHeader().checkLogoutDropdown();
    }

    @Flaky
    @CaseId(4)
    @Story("Тест валидности ссылок навигационного меню в шапке админки")
    @Test(description = "Тест валидности ссылок навигационного меню в шапке админки", groups = {"acceptance", "regression", "smoke"})
    public void successValidateNavigationMenu() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        main().interactSideMenu().storesDropdownClick();
        main().interactSideMenu().regionsClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().retailersClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().shipmentAreaClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().storesDropdownClick();

        main().interactSideMenu().ordersDropdownClick();
        main().interactSideMenu().logisticControlClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().rootsParametersClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().multipleOrderClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().ordersDropdownClick();

        main().interactSideMenu().contentDropdownClick();
        main().interactSideMenu().productsClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().goodsOptionsClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().propertiesClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().brandsClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().manufacturersClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().manufacturingCountriesClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().categoriesClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().contentImportClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().contentDropdownClick();

        main().interactSideMenu().marketingDropdownClick();
        main().interactSideMenu().promoCardsClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().promoActionsClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().welcomeBannersClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().adsClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().cartsClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().bonusCardsClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().retailerProgramsClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().referralProgramClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().newCitiesClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().inAppBannersClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().bonusCountClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().redirectsClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().samplingClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().marketingCategoriesClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().marketingDropdownClick();

        main().interactSideMenu().staffDropdownClick();
        main().interactSideMenu().shiftsClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().tariffsClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().shoppersClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().partnersImportClick();
        main().checkPageIsAvailable();
        main().interactSideMenu().staffDropdownClick();

        main().interactSideMenu().settingsClick();
        main().checkPageIsAvailable();

        main().interactSideMenu().usersClick();
        main().checkPageIsAvailable();

        main().interactSideMenu().pagesClick();
        main().checkPageIsAvailable();

        main().interactSideMenu().companiesClick();
        main().checkPageIsAvailable();
    }
}
